package com.muzimin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author : 李煌民
 * @date : 2021-08-03 14:18
 **/
public class HelloServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("初始化，第一次启动开始调用");

        System.out.println(servletConfig.getServletName());
        System.out.println(servletConfig.getInitParameter("name"));
        System.out.println(servletConfig.getInitParameter("url"));

        System.out.println(servletConfig.getServletContext());
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("servlet 被访问到。。。");

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String method = request.getMethod();

        System.out.println(method);

        if ("GET".equals(method)) {
            doGet();
        } else if ("POST".equals(method)) {
            doPost();
        } else {
            System.out.println("请求方法有误。。");
        }
    }

    private void doGet() {
        System.out.println("处理Get请求的方法");
    }

    private void doPost() {
        System.out.println("处理Post请求的方法");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("web 工程结束后调用");
    }
}
