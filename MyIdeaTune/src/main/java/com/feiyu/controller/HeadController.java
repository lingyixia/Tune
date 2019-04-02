package com.feiyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HeadController {

    @GetMapping("/toMyPrivate")
    public String toMyPrivate() {
        return "redirect:/toShowUserMessage";
    }

    @GetMapping("/totypemusic")
    public String toTypeMusic(HttpSession session) {
        session.setAttribute("country", "0");
        session.setAttribute("sex", "0");
        return "redirect:musicpages";
    }

    @GetMapping("/tomycollection")
    public String toMyCollection() {
        return "redirect:mycollcetion";
    }
}
