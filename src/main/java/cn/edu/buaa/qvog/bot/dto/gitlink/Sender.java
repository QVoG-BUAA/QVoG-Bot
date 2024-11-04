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
        "id",
        "login",
        "login_name",
        "full_name",
        "email",
        "avatar_url",
        "language",
        "is_admin",
        "last_login",
        "created",
        "restricted",
        "active",
        "prohibit_login",
        "location",
        "website",
        "description",
        "visibility",
        "followers_count",
        "following_count",
        "starred_repos_count",
        "username"
})
@Data
public class Sender {

    @JsonProperty("id")
    private int id;
    @JsonProperty("login")
    private String login;
    @JsonProperty("login_name")
    private String loginName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("language")
    private String language;
    @JsonProperty("is_admin")
    private boolean isAdmin;
    @JsonProperty("last_login")
    private String lastLogin;
    @JsonProperty("created")
    private String created;
    @JsonProperty("restricted")
    private boolean restricted;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("prohibit_login")
    private boolean prohibitLogin;
    @JsonProperty("location")
    private String location;
    @JsonProperty("website")
    private String website;
    @JsonProperty("description")
    private String description;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("followers_count")
    private int followersCount;
    @JsonProperty("following_count")
    private int followingCount;
    @JsonProperty("starred_repos_count")
    private int starredReposCount;
    @JsonProperty("username")
    private String username;

}
