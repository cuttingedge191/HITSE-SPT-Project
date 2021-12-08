package com.hit.spt.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        // 登录/注册/获取静态资源放行
        if (arg0.getRequestURI().equals("/error") || arg0.getRequestURI().contains(".")
                || arg0.getRequestURI().contains("/login") || arg0.getRequestURI().contains("/logup")
                || arg0.getRequestURI().contains("/logout")) {
            return true;
        }

        // 会话状态检测
        if (arg0.getSession(false) == null) {
            arg1.setContentType("text/html;charset=utf-8");
            arg1.getWriter().write("<script>alert('您当前未登录或会话过期！'); window.location.href='/login';</script>");
            arg1.getWriter().flush();
            return false;
        }

        // 根据权限所禁止的页面
        String[][] forbidden_uris= new String[][]{
                {,},
                {"goodsView","addGoods","updateGoods",},
                {"addInventory",},
                {"inventoryCheck","inventoryView",},
                {"inventoryTrans","inventoryView",},
                {"addOrder","ordersView","orderReview",},
                {"pos","ordersView","orderReview",},
                {,},
                {,},
                {"clientInfoSearch","addCustomer","upcos",},
                {"userInfoSearch","upUser"}};

        // 权限管理拦截
        // 首先单独判断几个URI
        if (arg0.getSession().getAttribute("permissions").toString().charAt(3) == '1' ||
                arg0.getSession().getAttribute("permissions").toString().charAt(4) == '1') {
            if (arg0.getRequestURI().equals("/inventoryView"))
                return true;
        }
        if (arg0.getSession().getAttribute("permissions").toString().charAt(5) == '1' ||
                arg0.getSession().getAttribute("permissions").toString().charAt(6) == '1') {
            if (arg0.getRequestURI().equals("/ordersView") || arg0.getRequestURI().equals("/orderReview"))
                return true;
        }
        // 然后是对上面禁止URI的拦截
        for (int i = 0; i < forbidden_uris.length; ++i) {
            if (arg0.getSession().getAttribute("permissions").toString().charAt(i) != '1') {
                if (Arrays.deepToString(forbidden_uris[i]).contains(arg0.getRequestURI().substring(1))) {
                    arg1.setContentType("text/html;charset=utf-8");
                    arg1.getWriter().write("<script>alert('您当前没有权限！'); window.location.href='/index';</script>");
                    arg1.getWriter().flush();
                    return false;
                }
            }
        }
        return true;
    }
}
