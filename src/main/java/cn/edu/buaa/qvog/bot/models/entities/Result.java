/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.models.entities;

import cn.edu.buaa.qvog.bot.common.models.HasCreated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Result extends HasCreated {
    private String id;
    private boolean success;
    private String message;
    private int bugCount;
    private int queryCount;
    private String data;
}
