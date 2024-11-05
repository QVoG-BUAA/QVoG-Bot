/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.models.mappers;

import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WebhookRequestMapper {
    @Insert("""
            INSERT INTO `requests` (
                        `id`, `clone_url`, `branch`, `commit_id`, `commit_message`,
                        `author`, `author_email`, `repo_name`, `raw`, `created_at`
            ) VALUES (
                        #{id}, #{cloneUrl}, #{branch}, #{commitId}, #{commitMessage},
                        #{author}, #{authorEmail}, #{repoName}, #{raw}, #{createdAt}
            )
            """)
    void insert(WebhookRequest request);

    @Select("SELECT * FROM `requests` WHERE `id` = #{id}")
    WebhookRequest find(String id);
}
