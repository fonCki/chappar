package com.example.chappar10.data;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    public User(String uid, String nickname, String email, boolean isMale, String profileurl, Date birthDate) {
        this.uid = uid;
        this.nickname = nickname;
        this.email = email;
        this.status = Status.OFFLINE;
        location = new Location();
        this.isMale = isMale;
        this.profileurl = profileurl;
        this.birthDate = birthDate;

    }

    enum Status {
        ONLINE,
        OFFLINE
    }


    public String uid;
    public String nickname;
    public String email;
    public Status status;
    public Location location;
    public boolean isMale;
    public String profileurl;
    public Date birthDate;


    public User() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
