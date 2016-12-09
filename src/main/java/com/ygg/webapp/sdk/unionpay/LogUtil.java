package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.util.Iterator;
/*     */
import java.util.Map;

/*     */
import org.slf4j.Logger;
/*     */
import org.slf4j.LoggerFactory;

/*     */
/*     */
/*     */
/*     */public class LogUtil
/*     */
{
    /* 27 */private static final Logger GATELOG = LoggerFactory.getLogger("Info");
    
    /*     */
    /* 29 */private static final Logger GATELOG_ERROR = LoggerFactory.getLogger("Info");
    
    /*     */
    /* 31 */private static final Logger GATELOG_MESSAGE = LoggerFactory.getLogger("Info");
    
    /*     */static final String LOG_STRING_REQ_MSG_BEGIN = "============================== SDK REQ MSG BEGIN ==============================";
    
    /*     */static final String LOG_STRING_REQ_MSG_END = "==============================  SDK REQ MSG END  ==============================";
    
    /*     */static final String LOG_STRING_RSP_MSG_BEGIN = "============================== SDK RSP MSG BEGIN ==============================";
    
    /*     */static final String LOG_STRING_RSP_MSG_END = "==============================  SDK RSP MSG END  ==============================";
    
    /*     */
    /*     */public static void writeLog(String cont)
    /*     */
    {
        /* 44 */GATELOG.info(cont);
        /*     */
    }
    
    /*     */
    /*     */public static void writeLog(String cont, String frameId)
    /*     */
    {
        /* 55 */GATELOG.info("[" + frameId + "]" + cont);
        /*     */
    }
    
    /*     */
    /*     */public static void writeErrorLog(String cont)
    /*     */
    {
        /* 64 */GATELOG_ERROR.error(cont);
        /*     */
    }
    
    /*     */
    /*     */public static void writeErrorLog(String cont, String frameId)
    /*     */
    {
        /* 75 */GATELOG_ERROR.error("[" + frameId + "]" + cont);
        /*     */
    }
    
    /*     */
    /*     */public static void writeErrorLog(String cont, Throwable ex)
    /*     */
    {
        /* 85 */GATELOG_ERROR.error(cont, ex);
        /*     */
    }
    
    /*     */
    /*     */public static void writeErrorLog(String cont, String frameId, Throwable ex)
    /*     */
    {
        /* 97 */GATELOG_ERROR.error("[" + frameId + "]" + cont, ex);
        /*     */
    }
    
    /*     */
    /*     */public static void writeMessage(String msg)
    /*     */
    {
        /* 106 */GATELOG_MESSAGE.info(msg);
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static void printRequestLog(Map<String, String> reqParam)
    /*     */
    {
        /* 115 */writeMessage("============================== SDK REQ MSG BEGIN ==============================");
        /* 116 */Iterator it = reqParam.entrySet().iterator();
        /* 117 */while (it.hasNext())
        {
            /* 118 */Map.Entry en = (Map.Entry)it.next();
            /* 119 */writeMessage("[" + (String)en.getKey() + "] = [" + (String)en.getValue() + "]");
            /*     */
        }
        /* 121 */writeMessage("==============================  SDK REQ MSG END  ==============================");
        /*     */
    }
    
    /*     */
    /*     */public static void printResponseLog(String res)
    /*     */
    {
        /* 130 */writeMessage("============================== SDK RSP MSG BEGIN ==============================");
        /* 131 */writeMessage(res);
        /* 132 */writeMessage("==============================  SDK RSP MSG END  ==============================");
        /*     */
    }
    
    /*     */
    /*     */public static void trace(String cont)
    /*     */
    {
        /* 141 */if (GATELOG.isTraceEnabled())
            /* 142 */GATELOG.trace(cont);
        /*     */
    }
    
    /*     */
    /*     */public static void debug(String cont)
    /*     */
    {
        /* 152 */if (GATELOG.isDebugEnabled())
            /* 153 */GATELOG.debug(cont);
        /*     */
    }
    /*     */
}
