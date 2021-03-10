package cn.nyrmt.user.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GenerateID {

    public String GetUserId(){
        //服务器IP+时间戳+随机整数
        //最大27位 最小18位

        //4~12位
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress().replace(".", "");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //13位
        long l = System.currentTimeMillis();

        //1~2位
        int x = (int)(Math.random()*100);

        return hostAddress+l+x;
    }

}
