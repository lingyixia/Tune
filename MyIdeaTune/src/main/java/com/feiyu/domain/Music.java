package com.feiyu.domain;

import net.sf.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Music {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "musicId", length = 10)
    private String musicId;

    @Column(name = "musicName", length = 225)
    private String musicName;

    @Column(name = "artistId", length = 10)
    private String artistId;

    @Column(name = "album", length = 255)
    private String album;

    @Column(name = "type", length = 15)
    private String type;

    @Column(name = "tuner", length = 225)
    private String tuner;

    @Column(name = "lyricer", length = 255)
    private String lyricer;

    @Column(name = "time")
    private Date time;

    @Column(name = "imgDir", length = 255)
    private String imgDir;

    @Column(name = "lrcDir", length = 225)
    private String lrcDir;

    @Column(name = "saveDir", length = 255)
    private String saveDir;

    @Column(name = "downLoadNum", length = 5)
    private int downLoadNum = 0;

    @Column(name = "allNum", length = 5)
    private int allNum = 0;

    @Column(name = "weekNum", length = 5)
    private int weekNum = 0;

    @JoinColumn(name = "artist_IdFor")//关联artist表的字段
    @ManyToOne
    private Artist artistBean;

    public Artist getArtistBean() {
        return artistBean;
    }

    public void setArtistBean(Artist artistBean) {
        this.artistBean = artistBean;
    }

    public String getLrcDir() {
        return lrcDir;
    }

    public void setLrcDir(String lyrDir) {
        this.lrcDir = lyrDir;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public int getDownLoadNum() {
        return downLoadNum;
    }

    public void setDownLoadNum(int downLoadNum) {
        this.downLoadNum = downLoadNum;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public Music() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTuner() {
        return tuner;
    }

    public void setTuner(String tuner) {
        this.tuner = tuner;
    }

    public String getLyricer() {
        return lyricer;
    }

    public void setLyricer(String lyricer) {
        this.lyricer = lyricer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    /*
    json
     */
    public JSONObject toJsonObj() {
        return JSONObject.fromObject(this);
    }
}
