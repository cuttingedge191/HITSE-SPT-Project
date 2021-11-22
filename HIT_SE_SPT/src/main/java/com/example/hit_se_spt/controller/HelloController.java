package com.example.hit_se_spt.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello(Model model){
        model.addAttribute("name", "李四");
        return "index";
    }
    @RequestMapping("login")
    public String login(String email, String password, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        System.out.println(email + " " + password);
        return "login";
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token=new UsernamePasswordToken(email,password);
//        try {
//            session.setAttribute("username",email);
//            subject.login(token);//执行登录
//            // 保存cookie，实现自动登录
//            Cookie cookie_username = new Cookie("username", email);
//            // 设置cookie的持久化时间，1天
//            cookie_username.setMaxAge( 24 * 60 * 60);
//            // 设置为当前项目下都携带这个cookie
//            cookie_username.setPath(request.getContextPath());
//            // 向客户端发送cookie
//            response.addCookie(cookie_username);
//            return "succeed";
//        }catch (UnknownAccountException e){
//            model.addAttribute("msg","用户名错误");
//            return  "login";
//        }catch (IncorrectCredentialsException e){
//            model.addAttribute("msg","密码错误");
//            return "login";
//        }
    }

    @RequestMapping("logup")
    public String logup(Model model){
        return "logup";
    }

    @RequestMapping("addCustomer")
    public String addCustomer(Model model){
        return "addCustomer";
    }
}
