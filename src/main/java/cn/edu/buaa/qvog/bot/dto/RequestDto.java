/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto;

import lombok.Data;

@Data
public class RequestDto {
    private String id;
    private String cloneUrl;
    private String branch;
    private String commitId;
    private String commitMessage;
    private String author;
    private String authorEmail;
    private String repoName;
}
