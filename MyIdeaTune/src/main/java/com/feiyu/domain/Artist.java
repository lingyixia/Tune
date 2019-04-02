package com.feiyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Artist {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "artistId", length = 10)
    private String artistId;

    @Column(name = "artistName", length = 225)
    private String artistName;

    @Column(name = "artistCountry", length = 1)
    private int artistCountry;

    @Column(name = "sex", length = 1)
    private int sex;

    @Column(name = "artistImg",length = 225)
    private String artistImg;

    public Artist() {
    }

    public String getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(String artistImg) {
        this.artistImg = artistImg;
    }

    public long getId() {
        return id;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getArtistCountry() {
        return artistCountry;
    }

    public int getSex() {
        return sex;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtistCountry(int artistCountry) {
        this.artistCountry = artistCountry;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
