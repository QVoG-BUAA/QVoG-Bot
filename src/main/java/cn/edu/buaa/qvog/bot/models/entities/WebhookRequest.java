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
public class WebhookRequest extends HasCreated {
    /**
     * Unique ID.
     */
    private String id;

    /**
     * Repository clone URL.
     */
    private String cloneUrl;

    /**
     * Triggered by which branch.
     */
    private String branch;

    /**
     * Complete commit ID.
     */
    private String commitId;

    /**
     * Commit message.
     */
    private String commitMessage;

    /**
     * Commit author.
     */
    private String author;

    /**
     * Commit author email.
     */
    private String authorEmail;

    /**
     * Repository full name (owner/repo).
     */
    private String repoName;

    /**
     * Raw content of the original webhook payload, which contains everything.
     */
    private String raw;
}
