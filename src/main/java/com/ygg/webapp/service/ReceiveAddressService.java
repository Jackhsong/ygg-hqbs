package com.ygg.webapp.service;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.view.ReceiveAddressView;

public interface ReceiveAddressService
{
    
    public ReceiveAddressView findReceiveAddressViewById(int addressId)
        throws ServiceException;
    
    public ReceiveAddressView findDefaultAddressByAccountId(int accountId)
        throws ServiceException;
}
