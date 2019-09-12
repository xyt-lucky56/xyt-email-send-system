package com.xyt.email.controller;

import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.utils.EmailSendUtil;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.InternetAddress;
import java.util.*;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/11 0011
 */
@RestController
public class SysEmailSendRecordController {
    private static final Logger logger=LoggerFactory.getLogger(SysEmailSendRecordController.class);
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

    @PostMapping("/email/sendToUser")
    public void sendEmail(@RequestParam("emailToUser") String emailToUser,@RequestParam("templateName") String templateName)throws Exception{
        EmailEntity emailEntity=new EmailEntity();
        Map map=new HashMap();
        switch (templateName){
            case "forgetPassword":
                emailEntity.setEmailSubject(SysEmailSendRecord.forgetPassword);
                map.put("emaileTo",emailToUser);
                break;
            case "registerUser":
                emailEntity.setEmailSubject(SysEmailSendRecord.registerUser);
                break;
            default:
                break;
        }
        emailEntity.setEmailTo(emailToUser);
        String sendPfxHtmlContentFromTemplate = EmailSendUtil.getSendPfxHtmlContentFromTemplate(map, templateName);
        emailEntity.setEmailContent(sendPfxHtmlContentFromTemplate);
        emailEntity.setEmailFrom(emailFrom);
        emailEntity.setEmailUserName(emailUsername);
        emailEntity.setHostName(emailHostname);
        emailEntity.setPassWord(emailPassword);
        emailEntity.setMailTempleteId(templateName);
        try{
            EmailSendUtil.sendHtmlEmail(emailEntity);
            emailEntity.setSendStatus(1);
            logger.info("成功");
        }catch (EmailException ex){
            emailEntity.setErrorMsg(ex.toString());
            emailEntity.setSendStatus(0);
            logger.info("失败,失败信息为{}",ex);
        }
        sysEmailSendRecordService.saveLog(emailEntity);
    }

    @PostMapping("/email/sendToUsers")
    public void sendEmails(@RequestParam("emailToUsers") List<String> emailToUsers,@RequestParam("templateName") String templateName)throws Exception{
        EmailEntity emailEntity=new EmailEntity();
        for(String address:emailToUsers){
            emailEntity.setEmailTo(address);
            Map map=new HashMap();
            map.put("emaileTo",address);
            String sendPfxHtmlContentFromTemplate = EmailSendUtil.getSendPfxHtmlContentFromTemplate(map, templateName);
            emailEntity.setEmailContent(sendPfxHtmlContentFromTemplate);
            emailEntity.setEmailFrom(emailFrom);
            emailEntity.setEmailUserName(emailUsername);
            emailEntity.setHostName(emailHostname);
            emailEntity.setPassWord(emailPassword);
            emailEntity.setMailTempleteId(templateName);
            switch (templateName){
                case "forgetPassword":
                    emailEntity.setEmailSubject(SysEmailSendRecord.forgetPassword);
                    break;
                case "registerUser":
                    emailEntity.setEmailSubject(SysEmailSendRecord.registerUser);
                    break;
                default:
                    break;
            }
            try{
                EmailSendUtil.sendHtmlEmail(emailEntity);
                emailEntity.setSendStatus(1);
                logger.info("成功");
            }catch (EmailException ex){
                emailEntity.setErrorMsg(ex.toString());
                emailEntity.setSendStatus(0);
                logger.info("失败,失败信息为{}",ex);
            }
            sysEmailSendRecordService.saveLog(emailEntity);
        }


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
