package com.feiyu.controller;

import com.feiyu.domain.Music;
import com.feiyu.domain.MusicAndArtist;
import com.feiyu.domain.MusicList;
import com.feiyu.domain.StaticVariable;
import com.feiyu.service.ArtistService;
import com.feiyu.service.MusicService;
import com.feiyu.service.PagingAndSortService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private PagingAndSortService pagingAndSortService;

    @GetMapping("/deletecurrent")
    public String toDeleteCurrent(@RequestParam("musicid") String musicId, Model model) {
        MusicList.deleteCurrentMusic(musicId);
        System.out.println("删除" + musicId + "成功");
        model.addAttribute(StaticVariable.MUSICLIST, MusicList.getInstance());
        model.addAttribute("ifOpen", "true");
        return "play";
    }

    @GetMapping("/toShowTypePage")
    public ModelAndView toTypeMusic() {
        List<Music> typeMusics = musicService.findFirst50BySaveDirNotNull();
        List<MusicAndArtist> typeMusicsAndAetist = artistService.findArtistsByMusics(typeMusics);
        ModelAndView mav = new ModelAndView("typemusic");
        mav.addObject(StaticVariable.TYPEMUSICANDARTIST, typeMusicsAndAetist);
        mav.addObject("nextPage", 1 + "");
        return mav;
    }

    @GetMapping("/musicpages")
    public ModelAndView getEntryByPageable(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "country", defaultValue = "0") String country, @RequestParam(value = "sex", defaultValue = "0") String sex, HttpSession session) {
        if (!country.equals("0")) {
            session.setAttribute("country", country);
        }
        if (!sex.equals("0")) {
            session.setAttribute("sex", sex);
        }
        ModelAndView mav = new ModelAndView("typemusic");
        Page<Music> musicPages = pagingAndSortService.getPageMusic(page, size, (session.getAttribute("country")).toString(), (session.getAttribute("sex")).toString());
        List<Music> musicCurrent = new ArrayList<Music>();
        for (int i = 0; i < musicPages.getNumberOfElements(); i++) {
            musicCurrent.add(musicPages.getContent().get(i));
        }
        List<MusicAndArtist> typeMusicsAndArtist = artistService.findArtistsByMusicsType(musicCurrent);
        if (musicPages.isLast())
        {
            mav.addObject("isLast", true + "");
        }
        else
        {
            mav.addObject("isLast", false + "");
        }
        mav.addObject(StaticVariable.TYPEMUSICANDARTIST, typeMusicsAndArtist);
        mav.addObject("nextPage", page + "");
        return mav;
    }

    @GetMapping("/tospecifytype")
    public ModelAndView toSpecifyTypeMusic(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "type", defaultValue = "0") String type) {
        ModelAndView mav = new ModelAndView("specifytype");
        Page<Music> musicPages = pagingAndSortService.getTypePageMusic(page, size, type);
//        System.out.println(musicPages.getContent());
        List<Music> musicCurrent = new ArrayList<Music>();
        for (int i = 0; i < musicPages.getNumberOfElements(); i++) {
            musicCurrent.add(musicPages.getContent().get(i));
        }
        List<MusicAndArtist> typeMusicsAndArtist = artistService.findArtistsByMusicsType(musicCurrent);
        mav.addObject(StaticVariable.TYPEMUSICANDARTIST, typeMusicsAndArtist);
        mav.addObject("nextPage", page + "");
        mav.addObject("type", type + "");
        if (musicPages.isLast())
        {
            mav.addObject("isLast", true + "");
        }
        else
        {
            mav.addObject("isLast", false + "");
        }
        return mav;
    }

    @GetMapping("/mycollcetion")
    public ModelAndView toMyCollection() {
        ModelAndView mav = new ModelAndView("mycollection");

        return mav;
    }
}
