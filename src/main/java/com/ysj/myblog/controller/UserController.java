package com.ysj.myblog.controller;

import com.ysj.myblog.entity.User;
import com.ysj.myblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yusijia
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public ModelAndView login(User user, HttpServletRequest request){

        ModelAndView mav = new ModelAndView();

        User resultUser = userService.login(user);
        if(resultUser == null){
            request.setAttribute("user", user);
            request.setAttribute("errorMsg", "错误信息");
            mav.setViewName("index");
            return mav;
        }else{
            HttpSession session = request.getSession();// request.getSession(true)
            session.setAttribute("currentUser", resultUser);
            mav.setViewName("redirect:/success.jsp");
            return mav;
        }
    }
}

