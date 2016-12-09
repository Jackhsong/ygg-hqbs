package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.service.TestService;
import com.ygg.webapp.util.CommonUtil;

@Repository("testService")
public class TestServiceImpl extends BaseDaoImpl implements TestService
{
    
    @Override
    public String testSpringTransaction(String requestParams)
        throws Exception // 对于在方法中throws 这种exception 是可以rollback的
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", CommonUtil.generateOrderNumber());
        SqlSessionTemplate sqt = this.getSqlSession();
        // System.out.println(sqt);
        sqt.insert("TestMapper.addReserveDownLoadNum", map);
        System.out.println(map);
        sqt = this.getSqlSession(); // 和上面是同一个sqlSession
        
        map = new HashMap<String, Object>();
        map.put("phoneNum", CommonUtil.generateOrderNumber() + "11111111111111");
        sqt.insert("TestMapper.addReserveDownLoadNum", map);
        
        // throw new Exception("testSpringTransaction") ; // throw 这种钟错，是不会rollback的
        // throw new RuntimeException("testSpringTransaction") ; // throw 这种运行时异常是会rollback的
        return "";
    }
    
    @Override
    public String testSpringTransaction2(String requestParams)
        throws Exception
    {
        
        return null;
    }
    
}
