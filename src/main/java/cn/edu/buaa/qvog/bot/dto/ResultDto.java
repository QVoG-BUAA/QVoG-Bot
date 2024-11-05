/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto;

import lombok.Data;

@Data
public class ResultDto {
    private String id;
    private boolean success;
    private String message;
    private String language;
}
