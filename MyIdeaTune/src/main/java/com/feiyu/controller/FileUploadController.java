package com.feiyu.controller;

import com.feiyu.domain.FileUtil;
import com.feiyu.domain.Music;
import com.feiyu.domain.StaticVariable;
import com.feiyu.domain.User;
import com.feiyu.service.MusicService;
import com.feiyu.service.UserService;
import org.omg.CORBA.Current;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private MusicService musicService;

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/uploadPic")
    public ModelAndView uploadImg(@RequestParam("avatar") MultipartFile file, HttpSession session) {
        String newHeadPath = StaticVariable.ROOT + file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(newHeadPath)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        } else {
            System.out.println("空的");
        }
        long useId = ((User) session.getAttribute(StaticVariable.CURRENTUSER)).getId();
        User currentUser = userService.findByIdEquals(useId);
        currentUser.setImagePath(file.getOriginalFilename());
        userService.updateUser(currentUser);
        currentUser.setImagePath(newHeadPath);
        ModelAndView mav = new ModelAndView("myprivate");
        mav.addObject(StaticVariable.CURRENTUSER, currentUser);
        session.setAttribute("currentUser", currentUser);
        return mav;
    }

    @GetMapping(value = "/download")
    public void Download(HttpServletResponse res, HttpServletRequest req, @RequestParam(value = "saveSrcPath") String saveSrcPath) {
        String[] s = saveSrcPath.split("/");
        Music musicTemp = musicService.findByMusicIdEquals(s[7]);
        musicTemp.setDownLoadNum(musicTemp.getDownLoadNum() + 1);
        musicService.updateMusicDownLoad(musicTemp.getMusicId(), musicTemp.getDownLoadNum());
        String fileName = s[7] + ".mp3";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream("E://Idea//ideaWorkSpace//MyTune//" + saveSrcPath));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}