/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.services.impl;

import cn.edu.buaa.qvog.bot.common.utils.Medias;
import cn.edu.buaa.qvog.bot.common.utils.Strings;
import cn.edu.buaa.qvog.bot.common.utils.Utils;
import cn.edu.buaa.qvog.bot.common.utils.process.IProcessDescriptor;
import cn.edu.buaa.qvog.bot.common.utils.process.ProcessDescriptor;
import cn.edu.buaa.qvog.bot.config.Options;
import cn.edu.buaa.qvog.bot.dto.ResultEntry;
import cn.edu.buaa.qvog.bot.extensions.Mappers;
import cn.edu.buaa.qvog.bot.models.entities.Result;
import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;
import cn.edu.buaa.qvog.bot.models.mappers.ResultMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class Daemon implements Runnable {
    private static final Map<SupportedLanguages, String> SCRIPTS = Map.of(
            SupportedLanguages.PYTHON, "python-scan.sh",
            SupportedLanguages.C, "c-scan.sh"
    );

    private final TaskQueue queue;
    private final Options options;
    private final Mappers mappers;
    private final ResultMapper resultMapper;
    private Context context;

    @Override
    public void run() {
        while (true) {
            try {
                queue.waitTillNotEmpty();
                var task = queue.poll();
                scan(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Daemon interrupted");
            } catch (Exception e) {
                log.error("Unexpected exception in daemon", e);
            }
        }
    }

    private void scan(WebhookRequest request) {
        log.info("Scanning request: {}", request);
        if (!options.isEnabled()) {
            log.warn("Webhook service is disabled, scan request ignored.");
            return;
        }

        Result result;
        try {
            result = scanImpl(request);
        } catch (ScanException e) {
            result = new Result();
            result.setId(request.getId());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("Scan failed: {}", e.getMessage(), e);
        } catch (Exception e) {
            result = new Result();
            result.setId(request.getId());
            result.setSuccess(false);
            result.setMessage("Internal error");
            log.error("Scan failed: {}", e.getMessage(), e);
        }

        resultMapper.insert(result);
        log.info("Result added");

        log.info("Sending email");
    }

    private Result scanImpl(WebhookRequest request) throws ScanException {
        initializeContext(request);
        prepareWorkingDirectory();
        cloneRepository();
        checkout();
        checkLanguage();
        performScan();
        return analyseResults();
    }

    private void initializeContext(WebhookRequest request) {
        context = new Context();
        context.setRequest(request);
        context.setRepoName(request.getRepoName().substring(request.getRepoName().lastIndexOf("/") + 1));
        context.setRepoPath(Path.of(options.getWorkingDirectory(), context.getRepoName()).toString());
        context.setOutputPath(Path.of(options.getWorkingDirectory(), "result.json").toString());
    }

    private void prepareWorkingDirectory() throws ScanException {
        try {
            Medias.ensureEmptyPath(options.getWorkingDirectory());
        } catch (IOException e) {
            throw new ScanException("Failed to prepare working directory", e);
        }
    }

    private void cloneRepository() throws ScanException {
        log.info("Cloning repository: {}", context.getRequest().getCloneUrl());

        int exitValue;
        IProcessDescriptor.ProcessError error = new IProcessDescriptor.ProcessError();
        try {
            exitValue = ProcessDescriptor.create()
                    .setWorkingDirectory(options.getWorkingDirectory())
                    .exec("git", "clone", context.getRequest().getCloneUrl())
                    .redirectError(error)
                    .waitFor();
        } catch (IOException e) {
            throw new ScanException("Failed to clone repository", e);
        } catch (InterruptedException e) {
            throw new ScanException("Cloning interrupted", e);
        }

        if (exitValue != 0) {
            throw new ScanException("Failed to clone repository: " + Strings.toString(error.getStdErr()));
        }

        if (!Medias.exists(context.getRepoPath())) {
            throw new ScanException("Repository not found: " + context.getRepoPath());
        }
    }

    private void checkout() throws ScanException {
        log.info("Checking out to branch: {}", context.getRequest().getBranch());

        int exitValue;
        IProcessDescriptor.ProcessError error = new IProcessDescriptor.ProcessError();
        try {
            exitValue = ProcessDescriptor.create()
                    .setWorkingDirectory(context.getRepoPath())
                    .exec("git", "checkout", context.getRequest().getBranch())
                    .redirectError(error)
                    .waitFor();
        } catch (IOException e) {
            throw new ScanException("Failed to checkout repository", e);
        } catch (InterruptedException e) {
            throw new ScanException("Checkout interrupted", e);
        }

        if (exitValue != 0) {
            throw new ScanException("Failed to checkout repository: " + Strings.toString(error.getStdErr()));
        }

        // WARNING: remove .git directory, which is no longer required
        Medias.removeSilently(Path.of(context.getRepoPath(), ".git"));
    }

    private void checkLanguage() throws ScanException {
        log.info("Checking language of repository: {}", context.getRepoPath());

        SupportedLanguages language = SupportedLanguages.UNKNOWN;
        int maxCount = 0;
        Map<String, SupportedLanguages> languages = Map.of(".c", SupportedLanguages.C, ".py", SupportedLanguages.PYTHON);

        try {
            for (var entry : languages.entrySet()) {
                int count = Utils.countFiles(context.getRepoPath(), entry.getKey());
                if (count > maxCount) {
                    language = entry.getValue();
                    maxCount = count;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (language == SupportedLanguages.UNKNOWN) {
            throw new ScanException("Unsupported language");
        }

        context.setLanguage(language);

        log.info("Detected language: {}", language);
    }

    private void performScan() throws ScanException {
        log.info("Scan started");

        int exitValue;
        IProcessDescriptor.ProcessError error = new IProcessDescriptor.ProcessError();
        try {
            String script = SCRIPTS.get(context.getLanguage());
            if (script == null) {
                throw new ScanException("Unsupported language: " + context.getLanguage());
            }
            exitValue = ProcessDescriptor.create()
                    .exec("bash",
                            Path.of(options.getScriptDirectory(), script).toString(),
                            context.getRepoPath(),
                            context.getOutputPath())
                    .redirectError(error)
                    .waitFor();
        } catch (IOException e) {
            throw new ScanException("Failed to perform scan", e);
        } catch (InterruptedException e) {
            throw new ScanException("Scan interrupted", e);
        }

        if (exitValue != 0) {
            throw new ScanException("Failed to perform scan: " + Strings.toString(error.getStdErr()));
        }

        log.info("Scan completed");
    }

    private Result analyseResults() throws ScanException {
        log.info("Analysing results: {}", context.getOutputPath());
        if (!Medias.exists(context.getOutputPath())) {
            log.error("Result not found: {}", context.getOutputPath());
            throw new ScanException("Result not found: " + context.getOutputPath());
        }

        ScanResult scanResult = new ScanResult();
        try {
            var data = Files.readAllLines(Path.of(context.getOutputPath()));
            for (var line : data) {
                if (line.startsWith("{")) {
                    var entry = mappers.fromJson(line, ResultEntry.class);
                    scanResult.results.add(entry);
                    scanResult.bugCount += entry.getRows().size();
                    scanResult.queryCount++;
                }
            }
        } catch (IOException e) {
            throw new ScanException("Failed to read result", e);
        }

        Result result = new Result();
        result.setId(context.getRequest().getId());
        result.setSuccess(true);
        result.setMessage("OK");
        result.setBugCount(scanResult.getBugCount());
        result.setQueryCount(scanResult.getQueryCount());
        try {
            result.setData(mappers.toJson(scanResult));
        } catch (JsonProcessingException e) {
            throw new ScanException("Failed to serialize scan result", e);
        }

        return result;
    }

    private enum SupportedLanguages {
        UNKNOWN, PYTHON, C
    }

    @Data
    private static class Context {
        private WebhookRequest request;
        private String repoName;    // only the name of the repository
        private String repoPath;    // the full path of the repository
        private SupportedLanguages language;
        private String outputPath;
    }

    @Data
    private static class ScanResult {
        private List<ResultEntry> results = new ArrayList<>();
        private int bugCount;
        private int queryCount;
    }
}
