package com.feiyu.domain;

public class MusicAndArtist {
    private Music music;
    private Artist artist;

    public MusicAndArtist() {
    }

    public MusicAndArtist(Music music, Artist artist) {
        this.music = music;
        this.artist = artist;
    }

    public Music getMusic() {
        return music;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
