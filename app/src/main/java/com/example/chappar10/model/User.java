package com.example.chappar10.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

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
    }


    private String uid;
    private String nickname;
    private String email;
    private Status status;
    private Location location;
    private boolean isMale;
    private String profileurl;
    private Date birthDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isMale() == user.isMale() && Objects.equals(getUid(), user.getUid()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getEmail(), user.getEmail()) && getStatus() == user.getStatus() && Objects.equals(getLocation(), user.getLocation()) && Objects.equals(getProfileurl(), user.getProfileurl()) && Objects.equals(getBirthDate(), user.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getNickname(), getEmail(), getStatus(), getLocation(), isMale(), getProfileurl(), getBirthDate());
    }
}
