package com.xyt.sms.service;

import com.xyt.sms.model.SmsEntity;

import java.util.Date;
import java.util.List;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/18 0018
 */
public interface SmsSendRecordService {
    int saveSendSmsLogs(SmsEntity smsEntity);
    SmsEntity getMobileCode(String phoneNumber);
    List<String> getBlackIps();
    void deleteOutOfDateLogs(Date date);
}
