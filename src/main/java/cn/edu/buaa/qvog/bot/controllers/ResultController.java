/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.controllers;

import cn.edu.buaa.qvog.bot.common.dto.DataResponse;
import cn.edu.buaa.qvog.bot.dto.CompleteResultDto;
import cn.edu.buaa.qvog.bot.services.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Result", description = "Result API")
public class ResultController {
    private final ResultService resultService;

    @GetMapping("result/{id}")
    @Operation(summary = "Get scan result by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid ID"),
            @ApiResponse(responseCode = "404", description = "Result not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public DataResponse<CompleteResultDto> getResult(
            @PathVariable String id
    ) {
        log.info("Requesting for result: {}", id);
        CompleteResultDto result = resultService.getResult(id);
        return DataResponse.ok(result);
    }
}
