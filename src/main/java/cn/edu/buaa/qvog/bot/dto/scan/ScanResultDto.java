/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto.scan;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScanResultDto {
    private List<QueryResultDto> results = new ArrayList<>();
    private int totalMilliseconds;
    private int bugCount;
    private int queryCount;

    public void addResult(QueryResultDto result) {
        results.add(result);
        totalMilliseconds += result.getMilliseconds();
        bugCount += result.getResult().getBugCount();
        queryCount++;
    }
}
