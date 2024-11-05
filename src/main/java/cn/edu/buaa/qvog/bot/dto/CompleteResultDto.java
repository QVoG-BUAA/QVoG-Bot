/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto;

import cn.edu.buaa.qvog.bot.dto.scan.ScanResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteResultDto {
    private RequestDto request;
    private ResultDto result;
    private ScanResultDto report;
}
