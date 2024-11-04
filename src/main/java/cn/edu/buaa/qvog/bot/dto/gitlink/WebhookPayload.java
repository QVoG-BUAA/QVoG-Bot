/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto.gitlink;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ref",
        "before",
        "after",
        "compare_url",
        "commits",
        "total_commits",
        "head_commit",
        "repository",
        "pusher",
        "sender"
})
@Data
public class WebhookPayload {

    @JsonProperty("ref")
    private String ref;
    @JsonProperty("before")
    private String before;
    @JsonProperty("after")
    private String after;
    @JsonProperty("compare_url")
    private String compareUrl;
    @JsonProperty("commits")
    @Valid
    private List<Commit> commits;
    @JsonProperty("total_commits")
    private int totalCommits;
    @JsonProperty("head_commit")
    @Valid
    private HeadCommit headCommit;
    @JsonProperty("repository")
    @Valid
    private Repository repository;
    @JsonProperty("pusher")
    @Valid
    private Pusher pusher;
    @JsonProperty("sender")
    @Valid
    private Sender sender;

}
