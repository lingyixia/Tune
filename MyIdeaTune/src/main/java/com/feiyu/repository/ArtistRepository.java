package com.feiyu.repository;

import com.feiyu.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long>{
    /*
    根据artitid返回artist
     */
    public abstract Artist findArtistByArtistIdEquals(String artistId);
}
