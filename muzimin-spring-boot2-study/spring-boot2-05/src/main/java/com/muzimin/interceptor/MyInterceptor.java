package com.muzimin.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: 李煌民
 * @date: 2024-04-11 20:17
 * 登陆检查拦截器
 * 1. 配置好拦截器要拦截哪些请求
 * 2. 把这些配置放在容器中
 **/
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    //在目标方法进行前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        log.info("在请求方法之前，拦截的所有URL：{}", requestURL);

        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            //登陆过了，将该请求进行放行
            return true;
        }

        request.setAttribute("msg", "请先登陆");
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }

    //在目标方法之后进行拦截
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //在目标方法完成后进行拦截
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
