package com.feiyu.repository;

import com.feiyu.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    /*
     * 返回前10首歌,根据type
     */
    public abstract List<Music> findFirst10ByType(String type);

    /*
    返回歌曲地址不是空的
     */
    public abstract List<Music> findFirst10BysaveDirNotNull();

    /*
    返回推荐
     */
    public abstract List<Music> findFirst10ByOrderByAllNumDesc();
    /*
    根据id返回歌曲
     */
    public abstract Music findByMusicIdEquals(String musicId);

    /*
    返回前十首新歌
     */
    public abstract List<Music> findFirst10ByOrderByTimeDesc();

    /*
    返回前十首一周内点播量最大的歌
     */
    public abstract List<Music> findFirst10ByOrderByWeekNumDesc();
    
    public abstract List<Music> findFirst50BySaveDirNotNull();

    @Transactional
    @Modifying
    @Query(value = "update Music m set m.allNum =?2,m.weekNum=?3 where m.musicId = ?1")
    public abstract int updateMusic(String musicId, int allNum, int weekNum);
    @Transactional
    @Modifying
    @Query(value = "update Music m set m.allNum =?2 where m.musicId = ?1")
    public abstract int updateMusicDownLoad(String musicId, int downLoad);

}
