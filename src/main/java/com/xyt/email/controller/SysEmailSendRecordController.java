package com.xyt.email.controller;

import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.utils.EmailSendUtil;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/11 0011
 */
@RestController
public class SysEmailSendRecordController {
    @Autowired
    private SysEmailSendRecordService sysEmailSendRecordService;

    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.username}")
    private String emailUsername;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.hostname}")
    private String emailHostname;

    @PostMapping("/email/send")
    public void sendEmail(String emailTo,String templateName)throws Exception{
        EmailEntity emailEntity=new EmailEntity();
        emailEntity.setEmailTo(emailTo);
        Map map=new HashMap();
        map.put("emaileTo",emailTo);
        String sendPfxHtmlContentFromTemplate = EmailSendUtil.getSendPfxHtmlContentFromTemplate(map, templateName);
        emailEntity.setEmailContent(sendPfxHtmlContentFromTemplate);
        emailEntity.setEmailFrom(emailFrom);
        emailEntity.setEmailUserName(emailUsername);
        emailEntity.setHostName(emailHostname);
        emailEntity.setPassWord(emailPassword);
//        目前没写subject
        try{
            EmailSendUtil.sendHtmlEmailToUsers(emailEntity);
            emailEntity.setSendStatus(1);
            System.out.println("成功");
        }catch (EmailException ex){
            emailEntity.setErrorMsg(ex.toString());
            emailEntity.setSendStatus(0);
            System.out.println(ex);
        }
        sysEmailSendRecordService.saveLog(emailEntity);
    }

    @GetMapping("/email/getSendLog")
    public SysEmailSendRecord getSendLog(String id){
        return sysEmailSendRecordService.getSendLog(id);
    }

    @GetMapping("/email/getSendLogs")
    public List<SysEmailSendRecord> getSendLogs(){
        return sysEmailSendRecordService.getSendLogs();
    }

    @DeleteMapping("/email/deleteSendLog")
    public void deleteSendLog(String id){
        sysEmailSendRecordService.deleteLog(id);
    }

    @PutMapping("/email/updateSendLog")
    public void updateSendLog(SysEmailSendRecord sysEmailSendRecord){
        sysEmailSendRecordService.updateById(sysEmailSendRecord);
    }
}
