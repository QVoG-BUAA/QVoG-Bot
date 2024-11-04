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
        "owner",
        "name",
        "full_name",
        "description",
        "empty",
        "private",
        "fork",
        "template",
        "parent",
        "mirror",
        "size",
        "language",
        "languages_url",
        "html_url",
        "url",
        "link",
        "ssh_url",
        "clone_url",
        "original_url",
        "website",
        "stars_count",
        "forks_count",
        "watchers_count",
        "open_issues_count",
        "open_pr_counter",
        "release_counter",
        "default_branch",
        "archived",
        "created_at",
        "updated_at",
        "archived_at",
        "permissions",
        "has_issues",
        "internal_tracker",
        "has_wiki",
        "has_pull_requests",
        "has_projects",
        "has_releases",
        "has_packages",
        "has_actions",
        "ignore_whitespace_conflicts",
        "allow_merge_commits",
        "allow_rebase",
        "allow_rebase_explicit",
        "allow_squash_merge",
        "allow_rebase_update",
        "default_delete_branch_after_merge",
        "default_merge_style",
        "default_allow_maintainer_edit",
        "avatar_url",
        "internal",
        "mirror_interval",
        "mirror_updated",
        "repo_transfer"
})
@Data
public class Repository {

    @JsonProperty("id")
    private int id;
    @JsonProperty("owner")
    @Valid
    private Owner owner;
    @JsonProperty("name")
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("empty")
    private boolean empty;
    @JsonProperty("private")
    private boolean _private;
    @JsonProperty("fork")
    private boolean fork;
    @JsonProperty("template")
    private boolean template;
    @JsonProperty("parent")
    private Object parent;
    @JsonProperty("mirror")
    private boolean mirror;
    @JsonProperty("size")
    private int size;
    @JsonProperty("language")
    private String language;
    @JsonProperty("languages_url")
    private String languagesUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("url")
    private String url;
    @JsonProperty("link")
    private String link;
    @JsonProperty("ssh_url")
    private String sshUrl;
    @JsonProperty("clone_url")
    private String cloneUrl;
    @JsonProperty("original_url")
    private String originalUrl;
    @JsonProperty("website")
    private String website;
    @JsonProperty("stars_count")
    private int starsCount;
    @JsonProperty("forks_count")
    private int forksCount;
    @JsonProperty("watchers_count")
    private int watchersCount;
    @JsonProperty("open_issues_count")
    private int openIssuesCount;
    @JsonProperty("open_pr_counter")
    private int openPrCounter;
    @JsonProperty("release_counter")
    private int releaseCounter;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("archived")
    private boolean archived;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("archived_at")
    private String archivedAt;
    @JsonProperty("permissions")
    @Valid
    private Permissions permissions;
    @JsonProperty("has_issues")
    private boolean hasIssues;
    @JsonProperty("internal_tracker")
    @Valid
    private InternalTracker internalTracker;
    @JsonProperty("has_wiki")
    private boolean hasWiki;
    @JsonProperty("has_pull_requests")
    private boolean hasPullRequests;
    @JsonProperty("has_projects")
    private boolean hasProjects;
    @JsonProperty("has_releases")
    private boolean hasReleases;
    @JsonProperty("has_packages")
    private boolean hasPackages;
    @JsonProperty("has_actions")
    private boolean hasActions;
    @JsonProperty("ignore_whitespace_conflicts")
    private boolean ignoreWhitespaceConflicts;
    @JsonProperty("allow_merge_commits")
    private boolean allowMergeCommits;
    @JsonProperty("allow_rebase")
    private boolean allowRebase;
    @JsonProperty("allow_rebase_explicit")
    private boolean allowRebaseExplicit;
    @JsonProperty("allow_squash_merge")
    private boolean allowSquashMerge;
    @JsonProperty("allow_rebase_update")
    private boolean allowRebaseUpdate;
    @JsonProperty("default_delete_branch_after_merge")
    private boolean defaultDeleteBranchAfterMerge;
    @JsonProperty("default_merge_style")
    private String defaultMergeStyle;
    @JsonProperty("default_allow_maintainer_edit")
    private boolean defaultAllowMaintainerEdit;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("internal")
    private boolean internal;
    @JsonProperty("mirror_interval")
    private String mirrorInterval;
    @JsonProperty("mirror_updated")
    private String mirrorUpdated;
    @JsonProperty("repo_transfer")
    private Object repoTransfer;

}
