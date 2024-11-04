/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.models.mappers;

import cn.edu.buaa.qvog.bot.models.entities.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResultMapper {
    @Insert("""
            INSERT INTO `results` (
                       `id`, `success`, `message`, `bug_count`, `query_count`,
                       `data`, `created_at`
            ) VALUES (
                       #{id}, #{success}, #{message}, #{bugCount}, #{queryCount},
                       #{data}, #{createdAt}
            )
            """)
    void insert(Result result);
}
