package com.example.chappar10.data;

public class User {
    enum Status {
        ONLINE,
        OFFLINE
    }


    public String nickname;
    public String email;
    public Status status;
    public Location location;


    public User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        this.status = Status.ONLINE;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public Location getLocation() {
        return location;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
