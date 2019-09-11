package com.xyt.email.service;


import com.xyt.common.base.BaseService;
import com.xyt.email.model.EmailEntity;
import com.xyt.email.model.SysEmailSendRecord;

import java.util.List;

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

    /**
     * 根据主键删除发送邮件日志
     * @param id 主键
     * @return
     */
    int deleteLog(String id);

    /**
     * 根据主键查询对应的发送邮件日志
     * @param id 主键
     * @return
     */
    SysEmailSendRecord getSendLog(String id);

    /**
     * 查询所有发送邮件日志
     * @return
     */
    List<SysEmailSendRecord> getSendLogs();

    /**
     * 根据主键更改对应邮件日志信息
     * @param sysEmailSendRecord
     * @return
     */
    int updateById(SysEmailSendRecord sysEmailSendRecord);
}