package com.example.chappar10.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat implements Serializable {
    private UUID chatId;
    private String senderId;
    private String receiverId;
    List<Message> messages;

    public Chat() {}

    public Chat(String senderId, String receiverId) {
        messages = new ArrayList<>();
        this.chatId = java.util.UUID.randomUUID();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messages = messages;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLatestMessage() {
        if (messages == null || messages.size() == 0) {
            return new Message("No messages yet", senderId, receiverId);
        }
        return messages.get(messages.size() - 1);
    }
}
