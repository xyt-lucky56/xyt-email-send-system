package com.xyt.sms.service.impl;

import com.xyt.sms.dao.SmsSendRecordDao;
import com.xyt.sms.model.SmsEntity;
import com.xyt.sms.service.SmsSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/18 0018
 */
@Service
public class SmsSendRecordServiceImpl implements SmsSendRecordService {
    @Autowired
    private SmsSendRecordDao smsSendRecordDao;
    @Override
    public int saveSendSmsLogs(SmsEntity smsEntity) {
        return smsSendRecordDao.saveSendSmsLogs(smsEntity);
    }

    @Override
    public SmsEntity getMobileCode(String phoneNumber) {
        return smsSendRecordDao.getMobileCode(phoneNumber);
    }

    @Override
    public List<String> getBlackIps() {
        return smsSendRecordDao.getBlackIps();
    }

    @Override
    public void deleteOutOfDateLogs(Date date) {
        smsSendRecordDao.deleteOutOfDateLogs(date);
    }
}
