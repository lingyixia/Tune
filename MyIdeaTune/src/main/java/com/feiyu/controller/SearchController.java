package com.feiyu.controller;

import com.feiyu.domain.Music;
import com.feiyu.domain.MusicAndArtist;
import com.feiyu.domain.StaticVariable;
import com.feiyu.service.ArtistService;
import com.feiyu.service.MusicService;
import com.feiyu.service.PagingAndSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private PagingAndSortService pagingAndSortService;

    @GetMapping("/mysearchresult")
    public ModelAndView getSearchResult(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "15") int size, @RequestParam(value = "searchcontent") String searchContent) {
        ModelAndView mav = new ModelAndView("searchresult");
        Page<Music> searchMusic = pagingAndSortService.getSearchPageMusic(page, size, searchContent);
        List<Music> musicCurrent = new ArrayList<Music>();
        for (int i = 0; i < searchMusic.getNumberOfElements(); i++) {
            musicCurrent.add(searchMusic.getContent().get(i));
        }
        List<MusicAndArtist> searchMusicsAndArtist = artistService.findArtistsByMusicsType(musicCurrent);
        mav.addObject(StaticVariable.SEAEXHMUSICANSAERIAR, searchMusicsAndArtist);
        mav.addObject("nextPage", page + "");
        mav.addObject("searchContent", searchContent);
        if (searchMusic.isLast())
        {
            mav.addObject("isLast", true + "");
        }
        else
        {
            mav.addObject("isLast", false + "");
        }
        return mav;
    }
}
