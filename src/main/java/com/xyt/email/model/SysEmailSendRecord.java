package com.xyt.email.model;

import com.xyt.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author copyright by 武汉信运通信息产业有限公司
 * @since 2019/09/10
 * @version R1.0
 * @category 文件表
 */

@ApiModel("文件表")
@Table(name = "sys_emailsendrecord")
public class SysEmailSendRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="邮件模板编号")
	@Column(name = "mailTempleteId")
	private String mailTempleteId;//邮件模板编号
	
	@ApiModelProperty(value="邮件主题")
	@Column(name = "mailSubject")
	private String mailSubject;//邮件主题
	
	@ApiModelProperty(value="邮件正文")
	@Column(name = "mailContent")
	private String mailContent;//邮件正文
	
	@ApiModelProperty(value="邮件发送方")
	@Column(name = "mailFrom")
	private String mailFrom;//邮件发送方
	
	@ApiModelProperty(value="邮件接收方")
	@Column(name = "mailTo")
	private String mailTo;//邮件接收方
	
	@ApiModelProperty(value="发送状态(0:失败  1:成功)")
	@Column(name = "sendStatus")
	private Integer sendStatus;//发送状态(0:失败  1:成功)
	
	@ApiModelProperty(value="发送次数")
	@Column(name = "sendTimes")
	private Integer sendTimes;//发送次数
	
	@ApiModelProperty(value="错误信息")
	@Column(name = "errorMsg")
	private String errorMsg;//错误信息
	
	@ApiModelProperty(value="创建时间")
	@Column(name = "workDate")
	private java.util.Date workDate;//创建时间

	public String getMailTempleteId() {
		return mailTempleteId;
	}

	public void setMailTempleteId(String mailTempleteId) {
		this.mailTempleteId = mailTempleteId;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
}