package com.example.demo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.example.demo.entity.ChinaTotal;
import com.example.demo.entity.User;
import com.example.demo.service.ChinaTotalService;
import com.example.demo.service.UserService;
import com.example.demo.vo.DataView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    // 跳转
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


    /**
     * 验证码的逻辑
     */
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response,HttpSession session) throws IOException {
        // 1. 验证码对象 HuTool定义图形验证码的长和宽,验证码的位数，干扰线的条数
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 10);
        // 2.放入到session
        session.setAttribute("code",captcha.getCode());
        // 3.输出验证码
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        // 4.关闭输出流
        outputStream.close();
    }

    /**
     * 具体的登录逻辑
     */
    @RequestMapping("/login/login")
    @ResponseBody
    public DataView login(String campus_id,String password,String code,HttpSession session){
        DataView dataView = new DataView();

        // 1.首先判断验证码对不对
        String sessionCode = (String) session.getAttribute("code");
        if (code!=null && sessionCode.equals(code)){
            // shiro登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(campus_id, password);
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                dataView.setCode(200);
                dataView.setMsg("登录成功！");
                // 4.放入session
                session.setAttribute("user",user);

            } catch (Exception e) {
                e.printStackTrace();
                dataView.setCode(100);
                dataView.setMsg("用户名或者密码错误，登陆失败！");
            }
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("验证码错误！");
        return dataView;
    }


    /**
     * 退出登录
     */
    @RequestMapping("/login/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
