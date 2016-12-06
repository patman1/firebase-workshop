package com.wearadvisor.db.items;

public class Question {

    public String author;
    public String text;
    public long timestamp;

    public Question(String author, String text, long timestamp) {
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
    }
}
