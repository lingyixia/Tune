package com.feiyu.service;

import com.feiyu.domain.Music;
import com.feiyu.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

@Service
@Transactional
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    public List<Music> findFirst10ByType(String type) {
        return musicRepository.findFirst10ByType(type);
    }

    public List<Music> findFirst10BysaveDirNotNull() {
        return musicRepository.findFirst10BysaveDirNotNull();
    }

    public Music findByMusicIdEquals(String musicId) {
        return musicRepository.findByMusicIdEquals(musicId);
    }

    public List<Music> findFirst50BySaveDirNotNull() {
        return musicRepository.findFirst50BySaveDirNotNull();
    }

    public List<Music> findFirst10ByTimeOrderByTimeDesc() {
        return musicRepository.findFirst10ByOrderByTimeDesc();
    }

    public List<Music> findFirst10ByOrderByWeekNumDesc() {
        return musicRepository.findFirst10ByOrderByWeekNumDesc();
    }

    public List<Music> findFirst10ByOrderByAllNumDesc() {
        return musicRepository.findFirst10ByOrderByAllNumDesc();
    }

    public int updateMusicDownLoad(String musicId, int downLoad) {
        return musicRepository.updateMusicDownLoad(musicId, downLoad);
    }

    public int updateMusic(String musicId, int allNum, int weekNum) {
        return musicRepository.updateMusic(musicId, allNum, weekNum);
    }
}
