package com.xyt.email.controller;

import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.utils.EmailSendUtil;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/11 0011
 */
@RestController
public class SysEmailSendRecordController {
    @Autowired
    private SysEmailSendRecordService sysEmailSendRecordService;

    @PostMapping("/email/send")
    public void sendEmail(EmailEntity emailEntity)throws Exception{
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
