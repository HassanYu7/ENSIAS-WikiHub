package com.choubapp.ensias_wiki_hub;

import java.util.Date;

public class PostItem {
    private String title;
    private int vote;
    private Date date;

    public PostItem(){
    }

    public PostItem(String title, int vote, Date date) {
        this.title = title;
        this.vote = vote;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
