package com.feiyu.controller;

import com.feiyu.domain.*;
import com.feiyu.service.ArtistService;
import com.feiyu.service.MusicService;
import com.feiyu.service.PagingAndSortService;
import com.feiyu.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomePageController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;
    @Autowired
    private UserService userService;

    @Autowired
    private PagingAndSortService pagingAndSortService;

    /*
     * 直接打开网页跳转到home界面
     */
    @GetMapping("/")
    public ModelAndView toLogIn() {
        ModelAndView mav = new ModelAndView("home");
        List<Music> hotMusicList = musicService.findFirst10ByOrderByAllNumDesc();
        List<MusicAndArtist> hotMusicAndArtists = artistService.findArtistsByMusics(hotMusicList);

        List<Music> musicFirstIssue = musicService.findFirst10ByTimeOrderByTimeDesc();
        List<MusicAndArtist> firstMusicAndArtist = artistService.findArtistsByMusics(musicFirstIssue);

        List<Music> upMusics = musicService.findFirst10ByOrderByWeekNumDesc();

        Page<Music> upChinaMusic = pagingAndSortService.getCountryPageMusic(1);
        Page<Music> upForeignMusic = pagingAndSortService.getCountryPageMusic(2);

        mav.addObject(StaticVariable.UPFOREIGNMUSICLIST, upForeignMusic);
        mav.addObject(StaticVariable.UPCHINAMUSICLIST, upChinaMusic);
        mav.addObject(StaticVariable.UPMUSICLIST, upMusics);
        mav.addObject(StaticVariable.MUSICANDARTIST, hotMusicAndArtists);
        mav.addObject(StaticVariable.FIRSTMUSICANDARTIST, firstMusicAndArtist);
        return mav;
    }

    @GetMapping("/toPlay")
    public String toPlay() {
        return "play";
    }

    @GetMapping("/musics")
    public ModelAndView toAddCurrentMusic(@RequestParam(value = "musicid", defaultValue = "0") String musicId, HttpSession session) {
        ModelAndView mav = new ModelAndView("play");
        User user = (User) session.getAttribute(StaticVariable.CURRENTUSER);
        if (user==null)
        {
            ModelAndView mTemp = new ModelAndView("login");
            mTemp.addObject("msg","请先登录");
            return mTemp;
        }
        long useId = user.getId();
        JSONArray collection = JSONArray.fromObject(userService.findByIdEquals(useId).getCollection());
        if (musicId.equals("0")) {
            mav.addObject(StaticVariable.MUSICLIST, MusicList.getInstance());
            mav.addObject(StaticVariable.COLLECTION, collection.toString());
            return mav;
        }
        Music musicTemp = musicService.findByMusicIdEquals(musicId);
        musicTemp.setAllNum(musicTemp.getAllNum() + 1);
        musicTemp.setWeekNum((musicTemp.getWeekNum() + 1));
        musicService.updateMusic(musicId, musicTemp.getAllNum(), musicTemp.getWeekNum());

        Music musicItem = musicService.findByMusicIdEquals(musicId);
        String artistName = artistService.findArtistByArtistIdEquals(musicItem.getArtistId()).getArtistName();
        musicItem.setArtistId(artistName);
//        System.out.println("犯得上个0"+musicItem.toJsonObj());
        JSONArray jsonArray = MusicList.getInstance();
        if (!MusicList.getJsonObject(musicId)) {
            jsonArray.add(musicItem.toJsonObj());
        }
//        for (Music musicItem :
//                hotMusicList) {
//            if (musicItem.getMusicId().equals(musicId)) {
//                musicItem.setArtistId(artistName);
//                JSONArray jsonArray = MusicList.getInstance();
//                if (!MusicList.getJsonObject(musicId)) {
//                    jsonArray.add(musicItem.toJsonObj());
////                    System.out.println("musicId " + musicId + "增加到列表成功");
//                }
//                break;
//            }
//        }
        mav.addObject(StaticVariable.MUSICLIST, MusicList.getInstance());
        mav.addObject(StaticVariable.COLLECTION, collection.toString());
        return mav;
    }
}
