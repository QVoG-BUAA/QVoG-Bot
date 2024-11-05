/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.services;

import cn.edu.buaa.qvog.bot.config.EmailOptions;
import cn.edu.buaa.qvog.bot.config.Options;
import cn.edu.buaa.qvog.bot.extensions.EmailClient;
import cn.edu.buaa.qvog.bot.extensions.Mappers;
import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;
import cn.edu.buaa.qvog.bot.models.mappers.ResultMapper;
import cn.edu.buaa.qvog.bot.models.mappers.WebhookRequestMapper;
import cn.edu.buaa.qvog.bot.services.impl.Daemon;
import cn.edu.buaa.qvog.bot.services.impl.TaskQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookService {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
    private static final TaskQueue queue = new TaskQueue();
    private final WebhookRequestMapper webhookRequestMapper;
    private final Options options;
    private final Mappers mappers;
    private final ResultMapper resultMapper;
    private final EmailClient emailClient;
    private final EmailOptions emailOptions;


    private boolean started;

    public void submit(WebhookRequest request) {
        startIfNotStarted();
        webhookRequestMapper.insert(request);
        queue.offer(request);
        log.info("Request sent");
    }

    private synchronized void startIfNotStarted() {
        if (!started) {
            EXECUTOR_SERVICE.submit(new Daemon(queue, options, mappers, resultMapper, emailClient, emailOptions));
            started = true;
        }
    }
}
