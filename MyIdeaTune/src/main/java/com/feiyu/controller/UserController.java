package com.feiyu.controller;

import com.feiyu.domain.*;
import com.feiyu.service.ArtistService;
import com.feiyu.service.MusicService;
import com.feiyu.service.UserService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private ArtistService artistService;

    /*
    当前加入收藏列表
     */
    @PostMapping("/collect")
    @ResponseBody
    public Map<String, String> toCollectCurrent(@RequestParam("musicid") String musicId, HttpSession session) {
        Map<String, String> msg = new HashMap<String, String>();
        boolean ifExits = false;
        long useId = ((User) session.getAttribute(StaticVariable.CURRENTUSER)).getId();
        JSONArray collection = JSONArray.fromObject(userService.findByIdEquals(useId).getCollection());
        JSONObject jsonObject = null;
        for (int i = 0; i < collection.size(); i++) {
            jsonObject = collection.getJSONObject(i);
            if (jsonObject.getString("musicId").equals(musicId)) {
                /*
                收藏列表中已经存在该music
                 */
                ifExits = true;
                collection.remove(jsonObject);
                System.out.println("remove: " + musicId);
                break;
            }
        }
        if (!ifExits) {
            jsonObject = new JSONObject();
            jsonObject.put("musicId", musicId);
            collection.add(jsonObject);
            System.out.println("add: " + musicId);
        }
        System.out.println("collection.size():" + collection.size() + "collection:" + collection.toString());
        userService.updateCollection(useId, collection.toString());
        msg.put("result", "SUCCESS");
        msg.put(StaticVariable.COLLECTION, JSONArray.fromObject(userService.findByIdEquals(useId).getCollection()).toString());
        return msg;
    }

    @GetMapping("/toShowUserMessage")
    public ModelAndView setUserMessage(HttpSession session) {
        long useId = ((User) session.getAttribute(StaticVariable.CURRENTUSER)).getId();
        User currentUser = userService.findByIdEquals(useId);
        ModelAndView mav = new ModelAndView("myprivate");
//        System.out.println("过三个地方" + currentUser.getImagePath());
        JSONArray collection = JSONArray.fromObject(currentUser.getCollection());
        List<Music> collectionMusic = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            Music music = new Music();
            JSONObject jsonObject = collection.getJSONObject(i);
            collectionMusic.add(musicService.findByMusicIdEquals(jsonObject.getString("musicId")));
        }
        List<MusicAndArtist> collectionMusicAndArtist = artistService.findArtistsByMusics(collectionMusic);
        currentUser.setImagePath(StaticVariable.ROOT + currentUser.getImagePath());
        mav.addObject(StaticVariable.COLLECTIONMUSICANDARTIST, collectionMusicAndArtist);
        mav.addObject(StaticVariable.CURRENTUSER, currentUser);
        return mav;
    }

    @PostMapping("/updateUserMsg")
    public ModelAndView updateUserMsg(@RequestParam("formNickName") String formNickName, @RequestParam("sex") String sex, @RequestParam("formEmail") String formEmail, @RequestParam("formPhone") String formPhone, @RequestParam("formDate") String formDate, @RequestParam("formIntroduce") String formIntroduce, HttpSession session) {
        long useId = ((User) session.getAttribute(StaticVariable.CURRENTUSER)).getId();
        User currentUser = userService.findByIdEquals(useId);
        currentUser.setNickName(formNickName);
        currentUser.setSex(sex.equals("0") ? false : true);
        System.out.println(currentUser.getSex());
        currentUser.setEmail(formEmail);
        currentUser.setPhone(formPhone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currentUser.setBirthday(sdf.parse(formDate));
        } catch (Exception e) {
            currentUser.setBirthday(null);
        }
        currentUser.setIntroduce(formIntroduce);
        userService.updateUser(currentUser);
        currentUser.setImagePath(StaticVariable.ROOT + currentUser.getImagePath());
        ModelAndView mav = new ModelAndView("myprivate");
        mav.addObject(StaticVariable.CURRENTUSER, currentUser);
        session.setAttribute("currentUser", currentUser);
        return mav;
    }

    @PostMapping("/updatePassword")
    public ModelAndView updatePassword(@RequestParam("formpassword") String formpassword, @RequestParam("formpassword2") String formpassword2, @RequestParam("formnewpassword") String formnewpassword, HttpSession session) {
        long useId = ((User) session.getAttribute(StaticVariable.CURRENTUSER)).getId();
        User currentUser = userService.findByIdEquals(useId);
        ModelAndView mav = new ModelAndView("myprivate");
        if (!formpassword.equals(formpassword2)) {
            System.out.println("不相等");
            currentUser.setImagePath(StaticVariable.ROOT + currentUser.getImagePath());
            mav.addObject(StaticVariable.CURRENTUSER, currentUser);
            return mav;
        } else {
            System.out.println("执行更新");
            currentUser.setPassWord(formnewpassword);
            userService.updateUser(currentUser);
            currentUser.setImagePath(StaticVariable.ROOT + currentUser.getImagePath());
            mav.addObject(StaticVariable.CURRENTUSER, currentUser);
            session.setAttribute("currentUser", currentUser);
            return mav;
        }
    }
}
