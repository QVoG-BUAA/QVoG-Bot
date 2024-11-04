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
        "verified",
        "reason",
        "signature",
        "signer",
        "payload"
})
@Data
public class Verification {

    @JsonProperty("verified")
    private boolean verified;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("signer")
    private Object signer;
    @JsonProperty("payload")
    private String payload;

}
