package com.acbcwy.fund.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.FundIncome;
import com.acbcwy.fund.service.FundIncomeService;

public class FundIncomeTest {	
	private ApplicationContext ctx=null;
	private FundIncomeService fundIncomeService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		fundIncomeService =(FundIncomeService)ctx.getBean("fundIncomeService");
	}
	
    @Test
	public void testAddFundIncome(){
	    FundIncome fundIncome=new FundIncome();
	    fundIncome.setAccountDay(new Date());
	    fundIncome.setFundId(1);
	    fundIncome.setYieldRate(1.003423);
	    fundIncomeService.addFundIncome(fundIncome);
    }
    
    @Test
    public void testFindFundIncome(){
    	 FundIncome fundIncome=new FundIncome();
    	 fundIncome.setFundId(1);
    	 fundIncome.setAccountDay(new Date());
    	 List<FundIncome> fundList= fundIncomeService.findFundIncome(fundIncome);
    	 Assert.assertEquals(1, fundList.size());
    	 
    }
	
    @Test
    public void testUpdateFundIncome() throws ParseException{
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	FundIncome fundIncome=new FundIncome();
	   	fundIncome.setFundId(1);
	   	fundIncome.setAccountDay(sdf.parse("2017-01-03"));
	   	fundIncome.setYieldRate(1.1);
	   	fundIncome.setCreatedBy(1L);
	   	fundIncome.setLastUpdatedby(1L);
	   	fundIncomeService.updateFundIncome(fundIncome);
    }
    
    @Test
    public void testDeleteFundIncome(){
    	FundIncome fundIncome=new FundIncome();
	   	fundIncome.setFundId(1);
	   	fundIncome.setAccountDay(new Date());
    	fundIncomeService.deleteFundIncome(fundIncome);
    }

}
