package com.acbcwy.fund.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.FundInfo;
import com.acbcwy.fund.service.FundInfoService;

public class FundInfoTest {	
	private ApplicationContext ctx=null;
	private FundInfoService fundInfoService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		fundInfoService =(FundInfoService)ctx.getBean("fundInfoService");
	}
	
    @Test
	public void testAddFundInfo(){
	    FundInfo fundInfo=new FundInfo();
	    fundInfo.setFundName("阳光私募一号基金");
	    fundInfo.setManager("王亚伟");
	    fundInfo.setRecommend("天天涨停板");
	    fundInfoService.addFundInfo(fundInfo);
    }
    
    @Test
    public void testFindFundInfo(){
    	 FundInfo fundInfo=new FundInfo();
    	 fundInfo.setFundId(1);
    	 FundInfo fundinfo= fundInfoService.findFundInfo(fundInfo);
    	 System.out.println(fundinfo.toJson());
    	 
    }
	
    @Test
    public void testUpdateFundInfo(){
    	FundInfo fundInfo=new FundInfo();
	   	fundInfo.setFundId(1);
	   	FundInfo fundinfo= fundInfoService.findFundInfo(fundInfo);
	   	fundInfo.setManager("张三");
	   	fundInfoService.updateFundInfo(fundInfo);
    }
    
    @Test
    public void testDeleteFundInfo(){
    	fundInfoService.deleteFundInfo(1);
    }

}
