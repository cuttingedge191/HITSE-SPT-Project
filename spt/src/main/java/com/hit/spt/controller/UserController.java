package com.hit.spt.controller;

import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.mapper.UserMapper;
import com.hit.spt.pojo.Customer;
import com.hit.spt.pojo.User;
import com.hit.spt.service.LogInUpService;
import com.hit.spt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

public class UserController {
    @Autowired
    LogInUpService logInUpService;

    @Autowired
    UserService UserService;

    /**
     * 进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param model    传递信息
     * @return 转发到主页（暂时没有）
     */
    @RequestMapping("user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletRequest request) {
        if (logInUpService.checkPassword(username, password)) {
            HttpSession session = request.getSession();
            User user = UserService.queryUserByUsername(username);
            session.setAttribute("name", user.getName());
            session.setAttribute("level", user.getLevel());
            session.setAttribute("position", user.getPosition());
            session.setMaxInactiveInterval(30);
            return "index";
        }
        model.addAttribute("msg", "用户名或者密码错误!");
        return "login";
    }

    /**
     * 进行注册
     *
     * @param user  囊括了所有的用户信息，打包到user中
     * @param model
     * @return
     */
    @RequestMapping("user/logup")
    public String logup(User user, Model model) {
        if (logInUpService.registerUser(user)) {
            return "login";
        }
        model.addAttribute("msg", "用户名已存在");
        return "logup";
    }

    @RequestMapping("userInfoSearch")
    public String userInfoSearch(Model model) {
        List<User> userList = UserService.queryUserList();
        model.addAttribute("userList", userList);
        return "userInfoSearch";
    }

    @RequestMapping("deUser")
    public String deleteUser(String u_id, Model model) {
        int iu_id = Integer.parseInt(u_id);
        UserService.deleteUserByUid(iu_id);
        return "redirect:userInfoSearch";
    }

    @RequestMapping("upUser")
    public String updateUser(String u_id, Model model) {
        int iu_id = Integer.parseInt(u_id);
        User user = UserService.queryUserByUid(iu_id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @RequestMapping("upUserNow")
    public String updateUserNow(String u_id, String name, String position, String username, String password, String sex, String level, Model model) {
        // 参数不合法（应该不能通过前端检查），直接返回“系统用户管理”
        if (name == null || position == null || username == null || password == null || sex == null || level == null) {
            return "redirect:userInfoSearch";
        }
        String gender = sex.equals("true") ? "male" : "female";
        User user = new User(Integer.parseInt(u_id), name, gender, position, Integer.parseInt(level), username, password);
        UserService.updateUser(user);
        return "redirect:userInfoSearch";
    }
}
