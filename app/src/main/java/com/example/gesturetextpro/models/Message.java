package com.example.gesturetextpro.models;

import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class Message {
    private String senderId;
    private String receiverId;
    private String content;
    private Timestamp timestamp;

    public Message() {}

    public Message(String senderId, String receiverId, String content, Timestamp timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        if (timestamp == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(timestamp.toDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(senderId, message.senderId) &&
                Objects.equals(receiverId, message.receiverId) &&
                Objects.equals(content, message.content) &&
                Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, receiverId, content, timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getFormattedTime() {
        if (timestamp == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(timestamp.toDate());
    }
}
