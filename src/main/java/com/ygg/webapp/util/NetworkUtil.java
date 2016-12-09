package com.ygg.webapp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtil
{
    
    public static String getLocalIp() {
        InetAddress addr;
        try
        {
            addr = InetAddress.getLocalHost();
            return addr.getHostAddress().toString();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
}
