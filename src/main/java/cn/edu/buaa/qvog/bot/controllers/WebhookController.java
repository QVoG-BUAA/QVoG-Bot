/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.controllers;

import cn.edu.buaa.qvog.bot.common.dto.MessageResponse;
import cn.edu.buaa.qvog.bot.dto.gitlink.WebhookPayload;
import cn.edu.buaa.qvog.bot.exceptions.BadRequestException;
import cn.edu.buaa.qvog.bot.extensions.Mappers;
import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;
import cn.edu.buaa.qvog.bot.services.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Webhook", description = "Webhook API")
public class WebhookController {
    private final Mappers mappers;
    private final WebhookService webhookService;

    @PostMapping("webhook")
    @Operation(summary = "Receive GitLink webhook payload")
    public MessageResponse webhook(@RequestBody @Valid WebhookPayload payload) {
        log.info("Received webhook payload: {}", payload);
        WebhookRequest request = convertPayload(payload);
        webhookService.submit(request);
        return MessageResponse.ok("Received");
    }

    private WebhookRequest convertPayload(WebhookPayload payload) throws BadRequestException {
        WebhookRequest request = new WebhookRequest();

        request.setId(RandomStringUtils.randomAlphanumeric(16));
        request.setCloneUrl(payload.getRepository().getCloneUrl());
        request.setBranch(payload.getRef().substring(payload.getRef().lastIndexOf('/') + 1));
        request.setCommitId(payload.getHeadCommit().getId());
        request.setCommitMessage(payload.getHeadCommit().getMessage());
        request.setAuthor(payload.getHeadCommit().getAuthor().getName());
        request.setAuthorEmail(payload.getHeadCommit().getAuthor().getEmail());
        request.setRepoName(payload.getRepository().getFullName());
        try {
            request.setRaw(mappers.toJson(payload));
        } catch (JsonProcessingException e) {
            throw new BadRequestException(String.format("%s: %s", "Failed to serialize payload", e));
        }

        return request;
    }
}
