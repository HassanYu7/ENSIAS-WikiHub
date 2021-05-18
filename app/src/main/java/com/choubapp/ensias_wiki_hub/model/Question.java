package com.choubapp.ensias_wiki_hub.model;

import java.util.Date;
import java.util.List;

public class Question {
    private String title, content, owner;
    private int vote;
    private Date datePosted;
    private List<String> tags;


    public Question(){

    }

    public Question(String title, String content, String owner, int vote, Date datePosted, List<String> tags) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.vote = vote;
        this.datePosted = datePosted;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
