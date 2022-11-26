package com.example.chappar10.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat implements Serializable {
    private String chatId;
    private String senderId;
    private String receiverId;
    private String LastMessage;
    private Timestamp timestamp;
    private List<User> InvolvedUsers;
    private List<String> InvolvedUsersId;

    public Chat() {}

    public Chat(String chatId, User senderId, User receiverId, Message message) {
        this.chatId = chatId;
        this.senderId = senderId.getUid();
        this.receiverId = receiverId.getUid();
        this.LastMessage = message.getMessage();
        this.timestamp = message.getTimestamp();
        this.InvolvedUsers = new ArrayList<>();
        this.InvolvedUsers.add(senderId);
        this.InvolvedUsers.add(receiverId);
        this.InvolvedUsersId = new ArrayList<>();
        this.InvolvedUsersId.add(senderId.getUid());
        this.InvolvedUsersId.add(receiverId.getUid());
    }


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<User> getInvolvedUsers() {
        return InvolvedUsers;
    }

    public void setInvolvedUsers(List<User> involvedUsers) {
        InvolvedUsers = involvedUsers;
    }

    public List<String> getInvolvedUsersId() {
        return InvolvedUsersId;
    }

    public void setInvolvedUsersId(List<String> involvedUsersId) {
        InvolvedUsersId = involvedUsersId;
    }
}
