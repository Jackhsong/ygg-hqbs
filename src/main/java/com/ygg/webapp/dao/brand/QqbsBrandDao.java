package com.ygg.webapp.dao.brand;

import java.util.List;

import com.ygg.webapp.entity.brand.QqbsBrandEntity;

public interface QqbsBrandDao
{
    List<QqbsBrandEntity> getQqbsBrands()
        throws Exception;
}
