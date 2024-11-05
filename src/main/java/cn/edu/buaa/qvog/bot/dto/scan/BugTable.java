/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto.scan;

import lombok.Data;

import java.util.List;

@Data
public class BugTable {
    private List<String> headers;
    private List<List<String>> rows;

    public int getColumnCount() {
        return headers.size();
    }

    public int getBugCount() {
        return rows.size();
    }
}
