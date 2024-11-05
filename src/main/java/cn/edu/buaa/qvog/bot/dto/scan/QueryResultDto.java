/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto.scan;

import lombok.Data;

@Data
public class QueryResultDto {
    private String name;
    private BugTable result;
    private int milliseconds;
}
