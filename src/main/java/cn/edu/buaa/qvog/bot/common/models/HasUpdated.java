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

@Data
public class HasUpdated implements Serializable {
    @JsonFormat(pattern = Globals.DATE_FORMAT)
    protected LocalDateTime updatedAt;

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }
}
