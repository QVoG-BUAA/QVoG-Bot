/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.models.mappers;

import cn.edu.buaa.qvog.bot.models.entities.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResultMapper {
    @Insert("""
            INSERT INTO `results` (
                       `id`, `success`, `message`, `bug_count`, `query_count`,
                       `language`, `data`, `created_at`
            ) VALUES (
                       #{id}, #{success}, #{message}, #{bugCount}, #{queryCount},
                       #{language}, #{data}, #{createdAt}
            )
            """)
    void insert(Result result);

    @Select("SELECT * FROM `results` WHERE `id` = #{id}")
    Result find(String id);
}
