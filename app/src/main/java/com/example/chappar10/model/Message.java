package com.example.chappar10.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String message;
    private String senderId;
    private String receiverId;
    private LocalDateTime date;

    public Message(String message, String senderId, String receiverId) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
        return date;
    }
}
