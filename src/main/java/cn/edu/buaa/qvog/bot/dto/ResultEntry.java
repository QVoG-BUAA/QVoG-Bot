/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "headers",
        "rows"
})
@Data
public class ResultEntry {

    @JsonProperty("headers")
    private List<String> headers;
    @JsonProperty("rows")
    private List<List<String>> rows;

}