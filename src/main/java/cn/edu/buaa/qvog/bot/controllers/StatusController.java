/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.controllers;

import cn.edu.buaa.qvog.bot.common.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Status", description = "Internal testing API")
public class StatusController {
    @GetMapping("ping")
    @Operation(summary = "Ping", description = "Ping the server")
    public MessageResponse ping() {
        return MessageResponse.ok("pong");
    }
}
