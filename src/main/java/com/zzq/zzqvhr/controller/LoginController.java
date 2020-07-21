package com.zzq.zzqvhr.controller;

import com.sun.deploy.net.HttpResponse;
import com.zzq.zzqvhr.model.RespBean;
import com.zzq.zzqvhr.utils.VerificationCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class LoginController {
    /*@GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登陆，请登录");
    }*/
    @GetMapping("/verifyCode")
    public void verifyCode(HttpSession session, HttpServletResponse response) throws IOException {
        VerificationCode code =new VerificationCode();
        BufferedImage image =code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code",text);
        VerificationCode.output(image,response.getOutputStream());
    }
}
