/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.common.models;

import cn.edu.buaa.qvog.bot.common.Globals;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A model that has a created time.
 */
@Data
public class HasCreated implements Serializable {
    @JsonFormat(pattern = Globals.DATE_FORMAT)
    protected LocalDateTime createdAt;

    public HasCreated() {
        this.createdAt = LocalDateTime.now();
    }
}
