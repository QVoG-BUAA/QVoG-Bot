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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "message",
        "url",
        "author",
        "committer",
        "verification",
        "timestamp",
        "added",
        "removed",
        "modified"
})
@Data
public class Commit {

    @JsonProperty("id")
    private String id;
    @JsonProperty("message")
    private String message;
    @JsonProperty("url")
    private String url;
    @JsonProperty("author")
    @Valid
    private Author author;
    @JsonProperty("committer")
    @Valid
    private Committer committer;
    @JsonProperty("verification")
    @Valid
    private Verification verification;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("added")
    private Object added;
    @JsonProperty("removed")
    private Object removed;
    @JsonProperty("modified")
    private Object modified;

}
