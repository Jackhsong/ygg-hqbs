package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.IOException;
/*     */
import java.net.InetAddress;
/*     */
import java.net.Socket;
/*     */
import java.net.UnknownHostException;
/*     */
import java.security.cert.X509Certificate;

/*     */
import javax.net.ssl.HostnameVerifier;
/*     */
import javax.net.ssl.SSLContext;
/*     */
import javax.net.ssl.SSLSession;
/*     */
import javax.net.ssl.SSLSocketFactory;
/*     */
import javax.net.ssl.TrustManager;
/*     */
import javax.net.ssl.X509TrustManager;

/*     */
/*     */public class BaseHttpSSLSocketFactory extends SSLSocketFactory
/*     */
{
    /*     */private SSLContext getSSLContext()
    /*     */
    {
        /* 33 */return createEasySSLContext();
        /*     */
    }
    
    /*     */
    /*     */public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3)
        /*     */throws IOException
    /*     */
    {
        /* 39 */return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
        /* 40 */arg2, arg3);
        /*     */
    }
    
    /*     */
    /*     */public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
        /*     */throws IOException, UnknownHostException
    /*     */
    {
        /* 46 */return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
        /* 47 */arg2, arg3);
        /*     */
    }
    
    /*     */
    /*     */public Socket createSocket(InetAddress arg0, int arg1)
        throws IOException
    /*     */
    {
        /* 52 */return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
        /*     */
    }
    
    /*     */
    /*     */public Socket createSocket(String arg0, int arg1)
        /*     */throws IOException, UnknownHostException
    /*     */
    {
        /* 58 */return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
        /*     */
    }
    
    /*     */
    /*     */public String[] getSupportedCipherSuites()
    /*     */
    {
        /* 64 */return null;
        /*     */
    }
    
    /*     */
    /*     */public String[] getDefaultCipherSuites()
    /*     */
    {
        /* 70 */return null;
        /*     */
    }
    
    /*     */
    /*     */public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3)
        /*     */throws IOException
    /*     */
    {
        /* 76 */return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
        /* 77 */arg2, arg3);
        /*     */
    }
    
    /*     */
    /*     */private SSLContext createEasySSLContext()
    {
        /*     */try
        {
            /* 82 */SSLContext context = SSLContext.getInstance("SSL");
            /* 83 */context.init(null,
            /* 84 */new TrustManager[] {MyX509TrustManager.manger}, null);
            /* 85 */return context;
            /*     */
        }
        catch (Exception e)
        {
            /* 87 */e.printStackTrace();
            /* 88 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static class MyX509TrustManager
    /*     */implements X509TrustManager
    /*     */
    {
        /* 94 */static MyX509TrustManager manger = new MyX509TrustManager();
        
        /*     */
        /*     */public X509Certificate[] getAcceptedIssuers()
        /*     */
        {
            /* 100 */return null;
            /*     */
        }
        
        /*     */
        /*     */public void checkClientTrusted(X509Certificate[] chain, String authType)
        /*     */
        {
            /*     */
        }
        
        /*     */
        /*     */public void checkServerTrusted(X509Certificate[] chain, String authType)
        /*     */
        {
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public static class TrustAnyHostnameVerifier implements HostnameVerifier
    /*     */
    {
        /*     */public boolean verify(String hostname, SSLSession session)
        /*     */
        {
            /* 116 */return true;
            /*     */
        }
        /*     */
    }
    /*     */
}
