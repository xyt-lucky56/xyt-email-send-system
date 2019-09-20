package com.xyt.sms.controller;

import com.xyt.utils.RandomCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/17 0017
 */
@RestController
public class CookieController {
    @CrossOrigin
    @GetMapping("/getCookieCode")
    public Map<String,Object> getCookieCode(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap();
        String phoneNumber = request.getParameter("phoneNumber");
        if(StringUtils.isBlank(phoneNumber)){
            map.put("error","请传递手机号!");
        }else {
            String randomCode = RandomCodeUtil.getRandomCode();
            String myOrigin = request.getHeader("origin");
            myOrigin = getDoMain(myOrigin);
            Cookie cookie=new Cookie(phoneNumber,randomCode);
            cookie.setMaxAge(2*60);
            cookie.setPath("/");
            cookie.setDomain(myOrigin);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
            map.put("超时时间",2);
        }
        return map;
    }
    private String getDoMain(String myOrigin){
        String newOrigin = myOrigin.replace("https://","")
                .replace("http://","");
        int i = newOrigin.indexOf(":");
        if (i > -1) {
            newOrigin = newOrigin.substring(0,i);
        }
        int index = newOrigin.indexOf(".");
        if (index > -1) {
            newOrigin = newOrigin.substring(index + 1);
        }
        return newOrigin;
    }
}
