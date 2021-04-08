package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssuePostList {

    @SerializedName("issue")
    List<IssuePostDTO> issuePostDTOList;

    public IssuePostList(List<IssuePostDTO> issuePostDTOList) {
        this.issuePostDTOList = issuePostDTOList;
    }

    public List<IssuePostDTO> getIssuePostDTOList() {
        return issuePostDTOList;
    }

    public void setIssuePostDTOList(List<IssuePostDTO> issuePostDTOList) {
        this.issuePostDTOList = issuePostDTOList;
    }
}
