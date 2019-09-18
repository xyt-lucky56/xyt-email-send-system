package com.xyt.utils;

import java.util.Random;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/17 0017
 */
public class RandomCodeUtil {
    private static final String number="0123465789";
    private static final String[] strings={"1234567890","abcdefghijklmnopqrstuvwxyz","ABCDEFGHIJKLMNOPQRSTUVWXYZ"};
    public static  String getRandomCode(){
        Random random=new Random();
        String str="";
        for(int i=0;i<4;i++){
            int index = random.nextInt(strings.length);
            str+=strings[index].charAt(random.nextInt(strings[index].length()));
        }
        return str;
    }

    public static String getSixNumberCode(){
        Random random=new Random();
        String str="";
        for(int i=0;i<6;i++){
            int index = random.nextInt(number.length());
            str+=number.charAt(index);
        }
        return str;
    }
}
