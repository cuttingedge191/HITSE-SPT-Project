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
            int level = user.getLevel();
            int tmp = 1;
            String rights = "";
            session.setAttribute("u_id", user.getU_id());
            session.setAttribute("name", user.getName());
            session.setAttribute("position", user.getPosition());
            // 权限标志位设置
            for (int i = 0; i < 32; ++i) {
                if ((level & tmp) > 0)
                    rights += "1";
                else
                    rights += "0";
                tmp *= 2;
            }
            session.setAttribute("permissions", rights);
            session.setMaxInactiveInterval(1800); // 会话过期时间设置
            return "redirect:../index";
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
        int level = user.getLevel();
        int tmp = 1;
        String rights = "";
        // 权限标志位设置
        for (int i = 0; i < 32; ++i) {
            if ((level & tmp) > 0)
                rights += "1";
            else
                rights += "0";
            tmp *= 2;
        }
        model.addAttribute("user", user);
        model.addAttribute("permissions", rights);
        return "updateUser";
    }

    @RequestMapping("upUserNow")
    public String updateUserNow(HttpServletRequest request) {
        // 参数不合法（应该不能通过前端检查），直接返回“系统用户管理”
        String u_id = request.getParameter("u_id");
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        if (name == null || position == null || username == null || password == null || sex == null) {
            return "redirect:userInfoSearch";
        }
        String gender = sex.equals("true") ? "male" : "female";

        // 权限信息转换
        int level = 0;
        int tmp = 1;
        for (Integer i = 1; i <= 11; ++i) {
            String p = "p" + i;
            String op = request.getParameter(p);
            if (op != null)
                level += tmp;
            tmp *= 2;
        }

        // 更新用户信息
        User user = new User(Integer.parseInt(u_id), name, gender, position, level, username, password);
        UserService.updateUser(user);
        return "redirect:userInfoSearch";
    }
}
