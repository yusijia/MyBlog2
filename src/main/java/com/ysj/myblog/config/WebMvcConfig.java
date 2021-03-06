package com.ysj.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yusijia
 * @Description: 相当于以前的spring-mvc.xml文件，其中部分配置放到了application-dev.properties中
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 这个暂时删除，有了下面的配置后，文件上传功能用不了，拦截了不该拦截的东西
     *
     * 修改DispatcherServlet默认配置
     * @param dispatcherServlet
     * @return
     */
    /*@Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet ){
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        // 后台管理页面用的，拦截后缀为.do的请求
        registration.addUrlMappings("*.do");
        // 前台伪静态处理，拦截后缀为.html的请求
        registration.addUrlMappings("*.html");
        // 拦截首页请求
        registration.addUrlMappings("/");
        return registration;
    }*/

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }
}
