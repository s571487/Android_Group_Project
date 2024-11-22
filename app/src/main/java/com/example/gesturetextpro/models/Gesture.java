package com.example.gesturetextpro.models;

public class Gesture {

    private String id;
    private String userId;
    private String gestureType;
    private String message;

    public Gesture() {
    }

    public Gesture(String userId, String gestureType, String message) {
        this.userId = userId;
        this.gestureType = gestureType;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGestureType() {
        return gestureType;
    }

    public void setGestureType(String gestureType) {
        this.gestureType = gestureType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
