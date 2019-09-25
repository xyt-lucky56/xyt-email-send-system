package com.xyt.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lh.toolclass.DESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/17 0017
 */
public class SmsSendUtil {
    private static final Logger logger= LoggerFactory.getLogger(SmsSendUtil.class);
    public static Boolean sendSms(String PhoneNumber,String code,String type,String enKey,String enSecret,String signName)throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("default",getDecode(enKey,"xyt168168"),getDecode(enSecret,"xyt168168"));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", PhoneNumber);
        request.putQueryParameter("SignName", signName);
        if("XG".equals(type)){
        request.putQueryParameter("TemplateCode", "SMS_167735348");
        }else {
            request.putQueryParameter("TemplateCode", "SMS_167735348");
        }
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        Boolean flag=false;
        try {
            CommonResponse response = client.getCommonResponse(request);
            if(response.getData().contains("OK")){
                flag=true;
            }
            logger.info("响应消息:{}",response.getData());
        } catch (ServerException e) {
            logger.info(e.getMessage());
        } catch (ClientException e) {
            logger.info(e.getMessage());
        }
        return flag;
    }

    public static String getDecode(String str,String token) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        byte[] decode = Base64.getDecoder().decode(str);
        byte[] decrypt = DESUtils.decrypt(decode, token);
        return new String(decrypt);
    }
}
