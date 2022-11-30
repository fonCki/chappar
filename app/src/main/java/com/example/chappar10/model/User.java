package com.example.chappar10.model;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    public enum Status {
        ONLINE,
        OFFLINE
    }
    public User(String uid, String nickname, String email, boolean isMale, Date birthDate) {
        this.uid = uid;
        this.nickname = nickname;
        this.email = email;
        this.status = Status.OFFLINE;
        this.isMale = isMale;
        this.birthDate = birthDate;
        location = new Location();
        bio = "";
    }


    private String uid;
    private String nickname;
    private String email;
    private Status status;
    private Location location;
    private boolean isMale;
    private String profileImageUrl;
    private Date birthDate;
    private String bio;

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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isMale() == user.isMale() && Objects.equals(getUid(), user.getUid()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getEmail(), user.getEmail()) && getStatus() == user.getStatus() && Objects.equals(getLocation(), user.getLocation()) && Objects.equals(getProfileImageUrl(), user.getProfileImageUrl()) && Objects.equals(getBirthDate(), user.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getNickname(), getEmail(), getStatus(), getLocation(), isMale(), getProfileImageUrl(), getBirthDate());
    }
}
