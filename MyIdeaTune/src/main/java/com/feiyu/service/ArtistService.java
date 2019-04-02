package com.feiyu.service;

import com.feiyu.domain.Artist;
import com.feiyu.domain.Music;
import com.feiyu.domain.MusicAndArtist;
import com.feiyu.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    /*
    根据查出的music找出其歌手
     */
    public List<MusicAndArtist> findArtistsByMusics(List<Music> musics) {
        List<MusicAndArtist> musicAndArtists = new ArrayList<MusicAndArtist>();
        for (int i = 0; i < musics.size(); i++) {
            musicAndArtists.add(new MusicAndArtist(musics.get(i),findArtistByArtistIdEquals(musics.get(i).getArtistId())));
        }
        return musicAndArtists;
    }
    public List<MusicAndArtist> findArtistsByMusicsType(List<Music> musics) {
        List<MusicAndArtist> musicAndArtists = new ArrayList<MusicAndArtist>();
        for (int i = 0; i < musics.size(); i++) {
            musicAndArtists.add(new MusicAndArtist(musics.get(i),findArtistByArtistIdEquals(musics.get(i).getArtistId())));
        }
        return musicAndArtists;
    }

    /*
    通过artistid找artist
     */
    public Artist findArtistByArtistIdEquals(String artistId) {
        return artistRepository.findArtistByArtistIdEquals(artistId);
    }
}
