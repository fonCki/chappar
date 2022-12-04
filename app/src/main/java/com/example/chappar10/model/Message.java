package com.example.chappar10.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private String senderId;
    private String receiverId;
    private Timestamp timestamp;

    public Message(String message, String senderId, String receiverId) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = Timestamp.now();
    }

    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
