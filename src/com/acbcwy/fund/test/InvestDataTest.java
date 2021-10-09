package com.acbcwy.fund.test;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.InvestData;
import com.acbcwy.fund.service.InvestDataService;

public class InvestDataTest {	
	private ApplicationContext ctx=null;
	private InvestDataService investDataService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		investDataService =(InvestDataService)ctx.getBean("investDataService");
	}
	
    @Test
	public void testAddInvestData(){
	    InvestData investData=new InvestData();
	    investData.setUserId(1L);
	    investData.setInvestDate(new Date());
	    investData.setInvestAmount(10000F);
	    investData.setInvestDirection(0);
	    investDataService.addInvestData(investData);
    }
    
    @Test
    public void testFindInvestData(){
    	 InvestData investData=new InvestData();
    	 investData.setInvestId(1L);
    	 List<InvestData> fundList= investDataService.findInvestData(investData);
    	 Assert.assertEquals(1, fundList.size());
    	 
    }
	
    @Test
    public void testUpdateInvestData(){
    	InvestData investData=new InvestData();
    	investData.setInvestId(1L);
	   	List<InvestData> fundList= investDataService.findInvestData(investData);
	   	investData=fundList.get(0);
	   	investData.setInvestAmount(99999F);
	   	investDataService.updateInvestData(investData);
    }
    
    @Test
    public void testDeleteInvestData(){
    	investDataService.deleteInvestData(1L);
    }

}
