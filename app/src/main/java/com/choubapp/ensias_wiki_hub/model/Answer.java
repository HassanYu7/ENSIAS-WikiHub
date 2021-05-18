package com.choubapp.ensias_wiki_hub.model;

import java.util.Date;

public class Answer {
    private String content, owner;
    private int vote;
    private Date date;

    public Answer() {
    }

    public Answer(String content, String owner, int vote, Date date) {
        this.content = content;
        this.owner = owner;
        this.vote = vote;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
