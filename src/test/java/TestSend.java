import com.xyt.EmailSendSystemApplication;
import com.xyt.email.model.EmailEntity;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.utils.EmailSendUtil;
import org.apache.commons.mail.EmailException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/11 0011
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmailSendSystemApplication.class)
public class TestSend {
    @Autowired
    private SysEmailSendRecordService sysEmailSendRecordService;
    @Test
    public void testA()throws Exception{
        EmailEntity ee = new EmailEntity();
        ee.setEmailContent("测试邮件发送33");
        ee.setEmailFrom("ceshi@lucky56.com.cn");
        ee.setEmailSubject("武汉信运通信息产业有限公司平台注册成功");
        // ee.setEmailTo("zhanggongyuan@fengunion.com");
        ee.setEmailUserName("ceshi@lucky56.com.cn");
        ee.setPassWord("Xyt168168");
        ee.setHostName("smtp.exmail.qq.com");
        List<InternetAddress> list = new ArrayList<InternetAddress>();
        try {
            list.add(new InternetAddress("yangkai@lucky56.com.cn"));
            list.add(new InternetAddress("liumenghui@lucky56.com.cn"));
        } catch (AddressException e) {
            e.printStackTrace();
        }
        ee.setEmailToUsers(list);
        try{
            EmailSendUtil.sendHtmlEmailToUsers(ee);
            ee.setSendStatus(1);
            System.out.println("成功");
        }catch (EmailException ex){
            ee.setErrorMsg(ex.toString());
            ee.setSendStatus(0);
            System.out.println(ex);
        }
        sysEmailSendRecordService.saveLog(ee);
    }
}
