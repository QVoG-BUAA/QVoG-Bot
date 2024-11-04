/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Used to return a list of items with 1-based pagination information.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageListDto<TData> {
    private final List<TData> items;
    private final int total;
    private final int page;
    private final int pageSize;

    public static <TData> PageListDto<TData> of(List<TData> items, int total, int page, int pageSize) {
        return new PageListDto<>(items, total, page, pageSize);
    }

    public static <TData> PageListDto<TData> empty(int page, int pageSize) {
        return new PageListDto<>(List.of(), 0, page, pageSize);
    }

    public boolean isLast() {
        return page * pageSize >= total;
    }
}
