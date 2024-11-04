/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.dto.gitlink;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "admin",
        "push",
        "pull"
})
@Data
public class Permissions {

    @JsonProperty("admin")
    private boolean admin;
    @JsonProperty("push")
    private boolean push;
    @JsonProperty("pull")
    private boolean pull;

}
