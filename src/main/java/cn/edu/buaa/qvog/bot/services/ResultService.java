/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.services;

import cn.edu.buaa.qvog.bot.dto.CompleteResultDto;
import cn.edu.buaa.qvog.bot.dto.RequestDto;
import cn.edu.buaa.qvog.bot.dto.ResultDto;
import cn.edu.buaa.qvog.bot.dto.scan.ScanResultDto;
import cn.edu.buaa.qvog.bot.exceptions.InternalServerErrorException;
import cn.edu.buaa.qvog.bot.exceptions.NotFoundException;
import cn.edu.buaa.qvog.bot.extensions.Mappers;
import cn.edu.buaa.qvog.bot.models.entities.Result;
import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;
import cn.edu.buaa.qvog.bot.models.mappers.ResultMapper;
import cn.edu.buaa.qvog.bot.models.mappers.WebhookRequestMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultMapper resultMapper;
    private final WebhookRequestMapper webhookRequestMapper;
    private final Mappers mappers;

    public CompleteResultDto getResult(String id) {
        var request = findRequest(id);
        if (request == null) {
            log.error("Request not found: {}", id);
            throw new NotFoundException("Result not found");
        }
        var result = findResult(id);
        try {
            return new CompleteResultDto(
                    mappers.map(request, RequestDto.class),
                    result == null ? null : mappers.map(result, ResultDto.class),
                    result == null ? null : mappers.fromJson(result.getData(), ScanResultDto.class));
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Failed to parse result data");
        }
    }

    private WebhookRequest findRequest(String id) {
        return webhookRequestMapper.find(id);
    }

    private Result findResult(String id) {
        return resultMapper.find(id);
    }
}
