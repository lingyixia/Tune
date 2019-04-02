package com.feiyu.controller;


import com.feiyu.domain.StaticVariable;
import com.feiyu.domain.User;
import com.feiyu.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegisterAndLoginController {
    @Autowired
    private UserService userService;
    /*
     * 注销函数，返回登陆界面
	 */

    @GetMapping("/toLogOut")
    public String toLogOut(HttpSession session) {
        session.removeAttribute(StaticVariable.CURRENTUSER);
        return "login";
    }

    @PostMapping("/toPostRegister")
    public String register(@RequestParam("nickName") String nickName, @RequestParam("passWord") String passWord,
                           HttpSession session) {
        User user = new User();
        user.setNickName(nickName);
        user.setPassWord(passWord);
        userService.register(user);
        session.setAttribute(StaticVariable.CURRENTUSER, user);
        return "login";
    }

    @GetMapping("toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/toPostLogIn")
    public String logIn(Model model, @RequestParam("nickName") String nickName, @RequestParam("passWord") String passWord,
                        HttpSession session) {
        String redirect = null;
        try {
            User user = userService.findByNickNameAndPassWord(nickName, passWord);
            if (user != null) {
                user.setImagePath(StaticVariable.ROOT + user.getImagePath());
                session.setAttribute(StaticVariable.CURRENTUSER, user);
                redirect = "redirect:/";
            } else {
                model.addAttribute("msg", "信息有误");
                redirect = "login";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return redirect;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam(value = "form-username") String formUserName, @RequestParam(value = "form-password") String formPassword) {
        User userTemp = userService.findByNickName(formUserName);
        ModelAndView mav;
        if (userTemp == null) {
            mav = new ModelAndView("login");
            User user = new User();
            user.setNickName(formUserName);
            user.setPassWord(formPassword);
            user.setBirthday(new Date());
            user.setCollection("[]");
            user.setSex(false);
            user.setImagePath("head.jpeg");
            userService.register(user);
        } else {
            mav = new ModelAndView("register");
            mav.addObject("msg", "昵称已存在");
        }
        return mav;
    }
}
