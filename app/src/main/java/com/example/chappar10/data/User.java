package com.example.chappar10.data;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    enum Status {
        ONLINE,
        OFFLINE
    }


    public String nickname;
    public String email;
    public Status status;
    public Location location;
    public String ImageUrl;
    public Date birthDate;
    public int age;


    public User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        this.status = Status.ONLINE;
    }

    public User(String nickname, String email, int age) {
        this.nickname = nickname;
        this.email = email;
        this.status = Status.ONLINE;
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
