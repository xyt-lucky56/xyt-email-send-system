package com.xyt;

import com.xyt.email.dao.SysEmailSendRecordMapper;
import com.xyt.email.model.EmailEntity;
import com.xyt.email.service.SysEmailSendRecordService;
import com.xyt.utils.EmailSendUtil;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class EmailSendSystemApplication {

    public static void main(String[] args) throws Exception{

        SpringApplication.run (EmailSendSystemApplication.class, args);
    }

}
