package com.ygg.webapp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReadMain
{
    
    public static void main(String[] args)
    {
        // System.out.println(FileReadMain.class.getClassLoader().getResource("wxindexshare.txt").getFile());
        //
        // InputStream is = FileReadMain.class.getResourceAsStream("wxindexshare.txt") ;
        String sb = readTxtFile(FileReadMain.class.getClassLoader().getResource("wxindexshare.txt").getFile());
        
        sb = sb.toString();
        sb = sb.replaceAll("wxshare_appid", "wxeedsdfd");
        System.out.println(sb);
    }
    
    public static String readTxtFile(String filePath)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) // 判断文件是否存在
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    sb.append(lineTxt + "\n");
                }
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }
    
}
