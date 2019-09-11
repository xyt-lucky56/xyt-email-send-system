package com.xyt.email.service.impl;

import com.xyt.common.base.BaseServiceImpl;
import com.xyt.common.base.utils.StringUtils;
import com.xyt.email.dao.SysEmailSendRecordMapper;
import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.xyt.email.service.SysEmailSendRecordService;

import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.UUID;

/**
 * @author copyright by 武汉信运通信息产业有限公司
 * @version R1.0
 * @category 邮件记录表
 * @since 2019/09/10
 */
@Service("sysEmailSendRecordService")
public class SysEmailSendRecordServiceImpl extends BaseServiceImpl<SysEmailSendRecordMapper, SysEmailSendRecord> implements SysEmailSendRecordService {

    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.username}")
    private String emailUsername;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.hostname}")
    private String emailHostname;
    @Autowired
    private SysEmailSendRecordMapper  sysEmailSendRecordMapper;
    @Override
    public void saveLog(EmailEntity emailEntity) throws Exception {
        if(StringUtils.isNotBlank(emailEntity.getEmailTo())){
            SysEmailSendRecord sysEmailSendRecord=new SysEmailSendRecord();
            sysEmailSendRecord.setId(UUID.randomUUID().toString());
            sysEmailSendRecord.setMailSubject(emailEntity.getEmailSubject());
            sysEmailSendRecord.setMailContent(emailEntity.getEmailContent());
            sysEmailSendRecord.setMailFrom(emailFrom);
            sysEmailSendRecord.setMailTo(emailEntity.getEmailTo());
            sysEmailSendRecord.setSendStatus(emailEntity.getSendStatus());
            sysEmailSendRecord.setSendTimes(1);
            sysEmailSendRecord.setErrorMsg(emailEntity.getErrorMsg());
            sysEmailSendRecord.setWorkDate(new Date());
            sysEmailSendRecordMapper.insertSelective(sysEmailSendRecord);
        }else if(emailEntity.getEmailToUsers()!=null&&emailEntity.getEmailToUsers().size()>0){
            for(InternetAddress internetAddress:emailEntity.getEmailToUsers()){
                SysEmailSendRecord sysEmailSendRecord=new SysEmailSendRecord();
                sysEmailSendRecord.setId(UUID.randomUUID().toString());
                sysEmailSendRecord.setMailSubject(emailEntity.getEmailSubject());
                sysEmailSendRecord.setMailContent(emailEntity.getEmailContent());
                sysEmailSendRecord.setMailFrom(emailFrom);
                sysEmailSendRecord.setMailTo(internetAddress.getAddress());
                sysEmailSendRecord.setSendStatus(emailEntity.getSendStatus());
                sysEmailSendRecord.setSendTimes(1);
                sysEmailSendRecord.setErrorMsg(emailEntity.getErrorMsg());
                sysEmailSendRecord.setWorkDate(new Date());
                sysEmailSendRecordMapper.insertSelective(sysEmailSendRecord);
            }
        }else {
            throw new Exception("收件人信息不存在");
        }
    }
}