package com.xyt.sms.dao;

import com.xyt.sms.model.SmsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/18 0018
 */
@Mapper
public interface SmsSendRecordDao {
    int saveSendSmsLogs(SmsEntity smsEntity);
    SmsEntity getMobileCode(String phoneNumber);
    List<String> getBlackIps();
    void deleteOutOfDateLogs(Date date);
}
