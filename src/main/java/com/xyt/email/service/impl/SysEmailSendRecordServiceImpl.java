package com.xyt.email.service.impl;

import com.xyt.common.base.BaseServiceImpl;
import com.xyt.email.dao.SysEmailSendRecordMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.email.model.SysEmailSendRecord;

/**
 * @author copyright by 武汉信运通信息产业有限公司
 * @version R1.0
 * @category 邮件记录表
 * @since 2019/09/10
 */
@Service
public class SysEmailSendRecordServiceImpl extends BaseServiceImpl<SysEmailSendRecordMapper, SysEmailSendRecord> implements SysEmailSendRecordService {

    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.username}")
    private String emailUsername;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.hostname}")
    private String emailHostname;
}