package com.xyt.sms.controller;

import com.xyt.utils.RandomCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/17 0017
 */
@RestController
public class CookieController {
    @GetMapping("/getCookieCode")
    public Map<String,Object> getCookieCode(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap();
        String phoneNumber = request.getParameter("phoneNumber");
        if(StringUtils.isBlank(phoneNumber)){
            map.put("error","请传递手机号!");
        }else {
            String randomCode = RandomCodeUtil.getRandomCode();
            Cookie cookie=new Cookie(phoneNumber,randomCode);
            cookie.setMaxAge(2);
            cookie.setPath(request.getRequestURI());
            response.addCookie(cookie);
            map.put("success","成功");
        }
        return map;
    }
}
