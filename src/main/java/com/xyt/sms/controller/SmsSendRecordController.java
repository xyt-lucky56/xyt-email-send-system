package com.xyt.sms.controller;

import com.xyt.sms.model.SmsEntity;
import com.xyt.sms.service.SmsSendRecordService;
import com.xyt.utils.RandomCodeUtil;
import com.xyt.utils.SmsSendUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tool.RedisAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/17 0017
 */
@RestController
public class SmsSendRecordController {
    @Value("${aliyuncs.access-key-id}")
    private String key;
    @Value("${aliyuncs.secret}")
    private String secret;
    @Value("${aliyuncs.zcxyh-template-code}")
    private String zcxyhTemplateCode;
    @Value("${aliyuncs.xgmm-template-code}")
    private String xgmmTemplateCode;
    @Autowired
    private SmsSendRecordService smsSendRecordService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/sendMessage")
    public Map<String,Object> sendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> map=new HashMap();
        String phoneNumber = request.getParameter("phoneNumber");
        if(StringUtils.isBlank(phoneNumber)){
            throw new Exception("请传递手机号");
        }
        String type = request.getParameter("type");
        if(StringUtils.isBlank(type)){
            throw new Exception("请传递类型");
        }
        SmsEntity smsEntity=new SmsEntity();
        String ip  = request.getRemoteAddr();;
        smsEntity.setMobile(phoneNumber);
        String code = RandomCodeUtil.getSixNumberCode();
        smsEntity.setMobileCode(code);
        smsEntity.setSendAddressIp(ip);
        smsEntity.setVouType(type);
        smsEntity.setSendtime(new Date());
//        待补充模板类型
        smsEntity.setSmsTemplete("SMS_167735348");
        Boolean status = SmsSendUtil.sendSms(phoneNumber, code, type, key, secret, "风河职教");
        if(status){
            smsEntity.setSendState(1);
            map.put("success","发送成功");
        }else {
            smsEntity.setSendState(0);
            map.put("error","发送失败");
        }
        smsSendRecordService.saveSendSmsLogs(smsEntity);
        return map;
    }
    @GetMapping("/getMobileCode")
    public Map<String,Object> getMobileCode(@RequestParam(value = "phoneNumber",required = true)String phoneNumber){
        SmsEntity smsEntity = smsSendRecordService.getMobileCode(phoneNumber);
        Date date=new Date();
        long time = date.getTime() - smsEntity.getSendtime().getTime();
        Map map=new HashMap();
        if(time>=60*30*1000){
            map.put("error","验证码已超时!");
        }else {
            map.put("success",smsEntity.getMobileCode());
        }
        return map;
    }
    @GetMapping("/getBlackIp")
    public Map<String,Object> getBlackIp(){
        List<String> blackIps = smsSendRecordService.getBlackIps();
        RedisAction redisAction = new RedisAction();
        redisAction.setStringRedisTemplate(this.redisTemplate);
        for(String ip:blackIps){
            redisAction.saveRightPush("blackIp",ip);
        }
        Map<String,Object> map=new HashMap();
        map.put("success",blackIps);
        return map;
    }

    @DeleteMapping("/deleteOutOfDateLogs")
    public Map<String,Object> deleteOutOfDateLogs(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-6);
        Date time = calendar.getTime();
        smsSendRecordService.deleteOutOfDateLogs(time);
        Map map=new HashMap();
        map.put("success","操作成功");
        return map;
    }
}
