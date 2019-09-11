package com.xyt.email.service;


import com.xyt.common.base.BaseService;
import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;

/**
 * @author copyright by 武汉信运通信息产业有限公司
 * @since 2018/011
 * @version R1.0
 * @category 文件表
 */
public interface SysEmailSendRecordService extends BaseService<SysEmailSendRecord> {
    /**
     * 保存发送邮件日志到数据库
     * @param emailEntity 接收人信息
     * @return
     */
    void saveLog(EmailEntity emailEntity) throws Exception;
}