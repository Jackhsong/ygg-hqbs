package com.ygg.webapp.code;

public enum KuaiDiCompanyAndPhoneTypeEnum
{
    /** 申通快递 */
    shentong("申通快递", "400-889-5543"),
    
    /** 中通速递 */
    zhongtong("中通速递", "400-827-0270"),
    
    /** 圆通速递 */
    yuantong("圆通速递", "021-697-77888"),
    
    /**百世汇通*/
    baishihuitong("百世汇通", "400-956-5656"),
    
    /** 韵达快运 */
    yunda("韵达快运", "400-821-6789"),
    
    /** EMS */
    ems("EMS", "11183"),
    
    /** 顺丰速运 */
    shunfeng("顺丰速运", "95338");
    
    private String channel;
    
    private String telePhone;
    
    private KuaiDiCompanyAndPhoneTypeEnum(String channel, String telePhone)
    {
        this.channel = channel;
        this.telePhone = telePhone;
    }
    
    public static String getPhone(String channel)
    {
        for (KuaiDiCompanyAndPhoneTypeEnum e : KuaiDiCompanyAndPhoneTypeEnum.values())
        {
            if (e.channel.startsWith(channel))
            {
                return e.telePhone;
            }
        }
        return null;
    }
    
}
