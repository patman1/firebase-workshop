package com.wearadvisor.storage.entities;

public class Question {

    public String id;
    public String text;
    public String owner;
    public long timestamp;

    //    String ownerName;
    //    long votesYes;
    //    long votesNo;

    public Question(String id, String text, String owner, long timestamp) {
        this.id = id;
        this.text = text;
        this.owner = owner;
        this.timestamp = timestamp;
    }
}
