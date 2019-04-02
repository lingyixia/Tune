package com.feiyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "passWord", length = 15)
    private String passWord;

    @Column(name = "nickName", length = 255)
    private String nickName;

    @Column(name = "sex", length = 1)
    private boolean sex;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "registerTime")
    private Date registerTime;

    @Column(name = "collection")
    private String collection;

    @Column(name = "history")
    private String history;

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public User() {
    }

    public User(long id, String passWord, String nickName, boolean sex, String email, String phone, Date birthday,
                String imagePath, String introduce) {
        super();
        this.id = id;
        this.passWord = passWord;
        this.nickName = nickName;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.imagePath = imagePath;
        this.introduce = introduce;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
