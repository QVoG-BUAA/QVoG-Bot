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
        "enable_time_tracker",
        "allow_only_contributors_to_track_time",
        "enable_issue_dependencies"
})
@Data
public class InternalTracker {

    @JsonProperty("enable_time_tracker")
    private boolean enableTimeTracker;
    @JsonProperty("allow_only_contributors_to_track_time")
    private boolean allowOnlyContributorsToTrackTime;
    @JsonProperty("enable_issue_dependencies")
    private boolean enableIssueDependencies;

}
