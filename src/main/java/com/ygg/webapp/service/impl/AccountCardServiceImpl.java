package com.ygg.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.AccountCardDao;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.AccountCardService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;

@Service("accountCardService")
public class AccountCardServiceImpl implements AccountCardService
{
    
    @Resource(name = "accountCardDao")
    private AccountCardDao accountCardDao;
    
    @Override
    public String getAllAccountCard(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        
        String accountId = params.get("accountId") == null ? "" : params.get("accountId").getAsString();
        
        if (accountId.equals("") || !CommonUtil.isNumeric(accountId))
        {
            
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_CARD_SHOW_STATUS.ACCOUNT_ID_ERROR.getValue());
            return result.toString();
        }
        
        List<AccountCartEntity> aces = this.accountCardDao.queryAccountCard(Integer.parseInt(accountId));
        for (AccountCartEntity ace : aces)
        {
            String cardNumber = ace.getCardNumber();
            if (ace.getType() == (byte)CommonEnum.ACCOUNT_CARD_TYPE.TYPE_BANK_TYPE.getValue()) // 是银行卡　取卡号后四位　
            {
                cardNumber = CommonUtil.getAccountCardValue(ace.getCardNumber(), 4, "bank");
            }
            else
            {
                if (ace.getCardNumber().length() > 15)
                {
                    cardNumber = CommonUtil.getAccountCardValue(ace.getCardNumber(), 13, "ali");
                    cardNumber += "...";
                }
            }
            ace.setCardNumber(cardNumber);
            
        }
        
        result.add("aces", parser.parse(JSONUtils.toJson(aces, false)));
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String editAccountCard(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String accountId = params.get("accountId") == null ? "" : params.get("accountId").getAsString();
        String type = params.get("type") == null ? "" : params.get("type").getAsString();
        String bankType = params.get("bankType") == null ? "" : params.get("bankType").getAsString();
        String cardNumber = params.get("cardNumber") == null ? "" : params.get("cardNumber").getAsString();
        String cardName = params.get("cardName") == null ? "" : params.get("cardName").getAsString();
        String accountCardId = params.get("accountCardId") == null ? "0" : params.get("accountCardId").getAsString();
        
        if (accountId.equals("") || bankType.equals("") || cardNumber.equals("") || cardName.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_CARD_EDIT_STATUS.PARAMS_ERROR.getValue());
            return result.toString();
        }
        
        if (Integer.parseInt(accountCardId) <= 0) // 新增
        {
            // 检查唯一性错误
            if (this.accountCardDao.isExistAccountType(Integer.parseInt(accountId), Integer.parseInt(type)))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ACCOUNT_CARD_EDIT_STATUS.ACCOUNTID_TYPE_ERROR.getValue());
                return result.toString();
            }
        }
        
        AccountCartEntity ace = new AccountCartEntity();
        ace.setType(Byte.parseByte(type));
        ace.setBankType(Byte.parseByte(bankType));
        ace.setCardNumber(cardNumber);
        ace.setCardName(cardName);
        ace.setAccountId(Integer.parseInt(accountId));
        if (accountCardId.equals("") || accountCardId.equals("0")) // 新增
        {
            if (accountCardDao.insertAccountCard(ace) == 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                return result.toString();
            }
            
        }
        else if (Integer.parseInt(accountCardId) > 0) // 修改
        {
            ace.setId(Integer.parseInt(accountCardId));
            if (accountCardDao.updateAccountCard(ace) == 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                return result.toString();
            }
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String getAccountCardById(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String acId = params.get("acId") == null ? "" : params.get("acId").getAsString();
        
        AccountCartEntity ace = this.accountCardDao.queryAccountCardById(Integer.parseInt(acId));
        if (ace == null)
            ace = new AccountCartEntity();
        
        result.add("ace", parser.parse(JSONUtils.toJson(ace, false)));
        
        return result.toString();
    }
    
    @Override
    public String getAccountCardValueById(int accountCardId)
        throws ServiceException
    {
        AccountCartEntity ace = this.accountCardDao.queryAccountCardById(accountCardId);
        
        return CommonUtil.getAccountCardValue(ace);
        // return accountCardValue ;
    }
    
}
