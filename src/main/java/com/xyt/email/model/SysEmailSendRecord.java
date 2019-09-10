package com.xyt.email.model;

import com.xyt.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author copyright by 武汉信运通信息产业有限公司
 * @since 2019/09/10
 * @version R1.0
 * @category 邮件记录表
 */

@ApiModel("邮件记录表")
@Table(name = "sys_email_send_record")
public class SysEmailSendRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="主键")
	@Column(name = "id")
	private String id;//主键
	
	@ApiModelProperty(value="邮件类型")
	@Column(name = "mail_type")
	private String mailType;//邮件类型
	
	@ApiModelProperty(value="邮件主题")
	@Column(name = "mail_subject")
	private String mailSubject;//邮件主题
	
	@ApiModelProperty(value="邮件正文")
	@Column(name = "mail_content")
	private String mailContent;//邮件正文
	
	@ApiModelProperty(value="邮件发送方")
	@Column(name = "mail_from")
	private String mailFrom;//邮件发送方
	
	@ApiModelProperty(value="邮件接收方")
	@Column(name = "mail_to")
	private String mailTo;//邮件接收方
	
	@ApiModelProperty(value="发送状态 0:失败  1:成功")
	@Column(name = "send_status")
	private Integer sendStatus;//发送状态 0:失败  1:成功
	
	@ApiModelProperty(value="发送次数")
	@Column(name = "send_times")
	private Integer sendTimes;//发送次数
	
	@ApiModelProperty(value="错误信息")
	@Column(name = "error_msg")
	private String errorMsg;//错误信息
	
	@ApiModelProperty(value="创建人名称")
	@Column(name = "create_name")
	private String createName;//创建人名称
	
	@ApiModelProperty(value="最后修改人的id")
	@Column(name = "last_modify_id")
	private String lastModifyId;//最后修改人的id
	
	@ApiModelProperty(value="最后修改人名称")
	@Column(name = "last_modify_name")
	private String lastModifyName;//最后修改人名称
	
	@ApiModelProperty(value="lastModifyTime")
	@Column(name = "last_modify_time")
	private java.util.Date lastModifyTime;//
	
	@ApiModelProperty(value="最后修改人IP")
	@Column(name = "last_modify_ip")
	private String lastModifyIp;//最后修改人IP
	
	

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	public String getMailType(){
		return mailType;
	}
	
	public void setMailType(String mailType){
		this.mailType=mailType;
	}
	public String getMailSubject(){
		return mailSubject;
	}
	
	public void setMailSubject(String mailSubject){
		this.mailSubject=mailSubject;
	}
	public String getMailContent(){
		return mailContent;
	}
	
	public void setMailContent(String mailContent){
		this.mailContent=mailContent;
	}
	public String getMailFrom(){
		return mailFrom;
	}
	
	public void setMailFrom(String mailFrom){
		this.mailFrom=mailFrom;
	}
	public String getMailTo(){
		return mailTo;
	}
	
	public void setMailTo(String mailTo){
		this.mailTo=mailTo;
	}
	public Integer getSendStatus(){
		return sendStatus;
	}
	
	public void setSendStatus(Integer sendStatus){
		this.sendStatus=sendStatus;
	}
	public Integer getSendTimes(){
		return sendTimes;
	}
	
	public void setSendTimes(Integer sendTimes){
		this.sendTimes=sendTimes;
	}
	public String getErrorMsg(){
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg){
		this.errorMsg=errorMsg;
	}
	public String getCreateName(){
		return createName;
	}
	
	public void setCreateName(String createName){
		this.createName=createName;
	}
	public String getLastModifyId(){
		return lastModifyId;
	}
	
	public void setLastModifyId(String lastModifyId){
		this.lastModifyId=lastModifyId;
	}
	public String getLastModifyName(){
		return lastModifyName;
	}
	
	public void setLastModifyName(String lastModifyName){
		this.lastModifyName=lastModifyName;
	}
	public java.util.Date getLastModifyTime(){
		return lastModifyTime;
	}
	
	public void setLastModifyTime(java.util.Date lastModifyTime){
		this.lastModifyTime=lastModifyTime;
	}
	public String getLastModifyIp(){
		return lastModifyIp;
	}
	
	public void setLastModifyIp(String lastModifyIp){
		this.lastModifyIp=lastModifyIp;
	}
}