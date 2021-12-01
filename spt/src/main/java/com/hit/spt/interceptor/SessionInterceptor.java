package com.hit.spt.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        return true;
    }
}
