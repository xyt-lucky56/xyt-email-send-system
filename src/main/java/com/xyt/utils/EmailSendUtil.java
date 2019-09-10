package com.xyt.utils;

import com.xyt.fileupload.model.EmailEntity;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.*;

public class EmailSendUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailSendUtil.class);

    /**
     * 邮件模板的存放位置
     */
    private static final String TEMPLATE_PATH = "/static/emailTemplate/";

    /**
     * 发送pfx文件邮件模板名称
     */
    private static final String SEND_PFX_TEMPLATE_NAME = "sendPfx.ftl";

    public static final String SIGNATRUE = "武汉信运通信息产业有限公司";

    private static final String REGISTER_MEESAGE_TEMPLATE = "尊敬的{0}，您已经在武汉信运通信息产业有限公司平台注册成功，查看准备的资料并进行登录完善企业信息资料！【{1}】";

    private static final String INVITATION_MESSAGE_TEMPLATE = "尊敬的{0}，{1}已经向您发出邀请，请通过下面网址进行确认邀请。【{2}】";

    public static void main(String[] args) throws EmailException {
        EmailEntity ee = new EmailEntity();

        ee.setEmailContent("<input type='button' value='提交' />");
        ee.setEmailFrom("yanzhi@fengunion.com");
        ee.setEmailSubject("武汉信运通信息产业有限公司平台注册成功");
        // ee.setEmailTo("zhanggongyuan@fengunion.com");
        ee.setEmailUserName("yanzhi@fengunion.com");
        ee.setPassWord("123456Qw");
        ee.setHostName("smtp.fengunion.com");
        List<InternetAddress> list = new ArrayList<InternetAddress>();
        try {
            list.add(new InternetAddress("zhanggongyuan@fengunion.com"));
            list.add(new InternetAddress("zhoupan@fengunion.com"));
        } catch (AddressException e) {
            e.printStackTrace();
        }
        ee.setEmailToUsers(list);

        System.out.println(sendHtmlEmail(ee));
    }

    /**
     * @param @param  EmailEntity ee 发邮件信息
     * @param @return
     * @return boolean
     * @throws EmailException
     * @throws
     * @Title: sendHtmlEmail
     * @Description: 发送html格式邮件
     */
    public static boolean sendHtmlEmail(EmailEntity ee) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.addTo(ee.getEmailTo());
        email.setHostName(ee.getHostName());
        email.setAuthentication(ee.getEmailUserName(), ee.getPassWord());
        //网易邮箱需要使用授权码进行登录
        //email.setAuthenticator(new DefaultAuthenticator(emailBean.getUserName(), "TFGYLJR2017"));
        email.setFrom(ee.getEmailFrom());
        email.setSubject(ee.getEmailSubject());
        email.setHtmlMsg(ee.getEmailContent());
        email.setCharset("UTF-8"); //解决中文乱码问题
        if (CollectionUtils.isNotEmpty(ee.getAttachFiles())) {
            for (File f : ee.getAttachFiles()) {
                email.attach(f);
            }
        }
        email.send();
        return true;
    }


    /**
     * @param @param  EmailEntity ee 发邮件信息 群发
     * @param @return
     * @return boolean
     * @throws EmailException
     * @throws
     * @Title: sendHtmlEmail
     * @Description: 发送html格式邮件
     */
    public static boolean sendHtmlEmailToUsers(EmailEntity ee) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setTo(ee.getEmailToUsers());
        email.setHostName(ee.getHostName());
        email.setAuthentication(ee.getEmailUserName(), ee.getPassWord());
        //网易邮箱需要使用授权码进行登录
        //email.setAuthenticator(new DefaultAuthenticator(emailBean.getUserName(), "TFGYLJR2017"));
        email.setFrom(ee.getEmailFrom());
        email.setSubject(ee.getEmailSubject());
        email.setHtmlMsg(ee.getEmailContent());
        email.setCharset("UTF-8"); //解决中文乱码问题
        if (CollectionUtils.isNotEmpty(ee.getAttachFiles())) {
            for (File f : ee.getAttachFiles()) {
                email.attach(f);
            }
        }
        email.send();
        return true;
    }

    /**
     * <pre>
     * 企业导入生成账户后发送邮件的模板
     * </pre>
     *
     * @param name 企业名称
     * @return
     */
    public static String getRegisterTemplate(String name) {
        return MessageFormat.format(REGISTER_MEESAGE_TEMPLATE, name, SIGNATRUE);
    }

    /**
     * <pre>
     * 企业导入生成账户后发送邮件的模板
     * </pre>
     *
     * @param coreName 企业名称
     * @param invName  企业名称
     * @return
     */
    public static String getInvitationTemplate(String coreName, String invName) {
        return MessageFormat.format(INVITATION_MESSAGE_TEMPLATE, coreName, invName, SIGNATRUE);
    }



    /*
     *//**
     * 获取企业录入成功后发送邮件内容
     * @param enterpriseName  链属企业名称
     * @param musers 企业登录用户信息
     * @param link 平台访问地址
     * @return
     *//*
    public static String getRegisterHtmlContent(String enterpriseName, Map<String, List<User>> musers, String realPath) {
    	StringBuffer htmlMsg = new StringBuffer();
        PropertiesConfig config = new PropertiesConfig();
        htmlMsg.append("<h3>尊敬的" + enterpriseName + "：</h3>");

        htmlMsg.append("<div class='reg_tip'>");

        htmlMsg.append("<div class='reg_group'>");
        htmlMsg.append( "<p class='reg_answer imgFix'><span class='wid28'></span>现将平台的登录信息发送给您，请妥善保留，并注意信息的保密，以免造成不必要的损失。</p>");
        htmlMsg.append("</div>");

        htmlMsg.append("<div class='reg_group'>");
        List<User> userslist=musers.get(BaseConstant.SUPPLIER_USER);//获取链属账号信息
        //如果链属已注册过账号，这里会返回一个空list，所以当list为空对象时或者有数据时才展示出来
        if(userslist ==null||!CollectionUtils.isEmpty(userslist)){
        	htmlMsg.append("<h3>账户信息：</h3>");
        	for (String title : musers.keySet()) {
                List<User> users = musers.get(title);
//                htmlMsg.append("<h4>" + title + "</h4>");
                for (User user : users) {
                    htmlMsg.append("<p class='reg_answer imgFix'>" + user.getRoleName() + "账号:" + user.getLoginName() + "&nbsp;&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;&nbsp;密码:" + user.getRealPassword()
                                   + "</p>");
                }
            }
        }
        htmlMsg.append("<p class='reg_answer imgFix '><span class='wid28'></span>平台地址:" + realPath  + "</p>");
        htmlMsg.append("<h4>如有任何问题，请致电400-0867-738。</h4>");
        htmlMsg.append("</div>");
        htmlMsg.append("</div>");
        return htmlMsg.toString();
    }*/


    /**
     * 通过模板获取发送pfx文件邮件内容
     */
    public static String getSendPfxHtmlContentFromTemplate(String enterpriseName, String password) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
            configuration.setTemplateLoader(new ClassTemplateLoader(EmailSendUtil.class, TEMPLATE_PATH));
            configuration.setEncoding(Locale.getDefault(), "UTF-8");
            configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");


            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put("enterpriseName", enterpriseName);
            parameters.put("password", password);

            Template template = configuration.getTemplate(SEND_PFX_TEMPLATE_NAME);
            StringWriter stringWriter = new StringWriter();
            template.process(parameters, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*	*//**
     * 企业角色转换 邮件模板
     *
     * @param enterpriseName
     *            企业名称
     * @param enterpriseType
     *            企业类型
     * @param userList
     *            用户列表
     * @param link
     *            平台访问路径
     * @return
     *//*
	public static String getConverContent(String enterpriseName, Integer enterpriseType, List<User> userList,
			String link) {
		StringBuffer htmlMsg = new StringBuffer();

		htmlMsg.append("<h3>尊敬的" + enterpriseName + "：</h3>");

		htmlMsg.append("<div class='reg_tip'>");

		htmlMsg.append("<div class='reg_group'>");
		htmlMsg.append("<p class='reg_answer imgFix'>首先欢迎贵企业成为本供应链金融平台的战略合作伙伴。</p>"
				+ "<p class='reg_answer imgFix '><span class='wid28'></span>我们诚挚的邀请贵企业入驻本供应链金融平台。</p>"
				+ "<p class='reg_answer imgFix'><span class='wid28'></span>现将平台的登录信息发送给您，请妥善保留，并注意信息的保密，以免造成不必要的损失。</p>");
		htmlMsg.append("</div>");

		htmlMsg.append("<div class='reg_group'>");
		if (!CollectionUtils.isEmpty(userList)) {
			htmlMsg.append("<h3>账户信息：</h3>");
			String accountMessage = UserConstant.ENTERPRISE_TYPE_CORE.equals(enterpriseType + "") ? BaseConstant.CORE_USER
					: (UserConstant.ENTERPRISE_TYPE_CHAIN.equals(enterpriseType + "") ? BaseConstant.SUPPLIER_USER : "");
			htmlMsg.append("<h4>" + accountMessage + "</h4>");

			for (User user : userList) {
				String realPassword = user.getRealPassword();
				if (realPassword == null || "".equals(realPassword)) {
					realPassword = "请使用自有密码登录";
				}
				htmlMsg.append("<p class='reg_answer imgFix'>" + user.getRoleName() + "账号:" + user.getLoginName()
						+ "&nbsp;&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;&nbsp;密码:" + realPassword + "</p>");
			}
		}
		htmlMsg.append("<p class='reg_answer imgFix '><span class='wid28'></span>平台地址:" + link + "</p>");
		htmlMsg.append("</div>");

		htmlMsg.append("<div class='reg_tip'>");
		htmlMsg.append("<h4>如有任何问题,请致电：400-0867-738。</h4>");
		htmlMsg.append("</div>");
		return htmlMsg.toString();
	}*/
}
