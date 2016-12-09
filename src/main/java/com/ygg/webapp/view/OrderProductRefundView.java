package com.ygg.webapp.view;

import java.util.ArrayList;
import java.util.List;

public class OrderProductRefundView extends BaseView
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountCardId;
    
    private String accountCardVal; // 在提交申请的页面中显示拼接后的账号字面值
    
    private int accountId;
    
    private int orderId;
    
    private int productId;
    
    private int orderProductId;
    
    private byte count;
    
    private byte type = 1;
    
    private byte status = 1;
    
    private String explain = "";
    
    private float applyMoney = 0.0f;
    
    private float realMoney = 0.0f;
    
    private String image1 = "";
    
    private String image2 = "";
    
    private String image3 = "";
    
    private String createTime;
    
    private String checkTime;
    
    private String updateTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAccountCardId()
    {
        return accountCardId;
    }
    
    public void setAccountCardId(int accountCardId)
    {
        this.accountCardId = accountCardId;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public int getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public int getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(int orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    public byte getCount()
    {
        return count;
    }
    
    public void setCount(byte count)
    {
        this.count = count;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public byte getStatus()
    {
        return status;
    }
    
    public void setStatus(byte status)
    {
        this.status = status;
    }
    
    public String getExplain()
    {
        return explain;
    }
    
    public void setExplain(String explain)
    {
        this.explain = explain;
    }
    
    public float getApplyMoney()
    {
        return applyMoney;
    }
    
    public void setApplyMoney(float applyMoney)
    {
        this.applyMoney = applyMoney;
    }
    
    public float getRealMoney()
    {
        return realMoney;
    }
    
    public void setRealMoney(float realMoney)
    {
        this.realMoney = realMoney;
    }
    
    public String getImage1()
    {
        return image1;
    }
    
    public void setImage1(String image1)
    {
        this.image1 = image1;
    }
    
    public String getImage2()
    {
        return image2;
    }
    
    public void setImage2(String image2)
    {
        this.image2 = image2;
    }
    
    public String getImage3()
    {
        return image3;
    }
    
    public void setImage3(String image3)
    {
        this.image3 = image3;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(String checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getAccountCardVal()
    {
        return accountCardVal;
    }
    
    public void setAccountCardVal(String accountCardVal)
    {
        this.accountCardVal = accountCardVal;
    }
    
    // 控制，图片必须保证，eg：有两张图片：则必须是image1和image2，不能出现是image2和image3的情况 。这个涉及到页面展示
    public void refreshImageSequence()
    {
        List<String> imgList = new ArrayList<String>();
        if (!"".equals(image1))
        {
            imgList.add(image1);
            image1 = "";
        }
        if (!"".equals(image2))
        {
            imgList.add(image2);
            image2 = "";
        }
        if (!"".equals(image3))
        {
            imgList.add(image3);
            image3 = "";
        }
        for (int i = 0; i < imgList.size(); i++)
        {
            if (i == 0)
                image1 = imgList.get(i);
            if (i == 1)
                image2 = imgList.get(i);
            if (i == 2)
                image3 = imgList.get(i);
        }
    }
}
