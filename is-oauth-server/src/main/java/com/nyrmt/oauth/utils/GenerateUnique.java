package com.nyrmt.oauth.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GenerateUnique {

    public String GetUniqueStr(){
        //服务器IP+时间戳+随机整数
        //最大27位 最小18位

        //4~12位
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress().replace(".", "");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(hostAddress == null){
            hostAddress = "127001";
        }

        //13位
        long l = System.currentTimeMillis();

        //1~2位
        int x = (int)(Math.random()*100);

        return hostAddress+l+x;
    }

}
