package com.acbcwy.fund.test;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.UserDayMoney;
import com.acbcwy.fund.model.UserSummary;
import com.acbcwy.fund.service.UserDayMoneyService;

public class UserDayMoneyTest {	
	private ApplicationContext ctx=null;
	private UserDayMoneyService userDayMoneyService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		userDayMoneyService =(UserDayMoneyService)ctx.getBean("userDayMoneyService");
	}
	
    @Test
	public void testAddUserDayMoney(){
	    UserDayMoney userDayMoney=new UserDayMoney();
	    userDayMoney.setAccountDay(new Date());
	    userDayMoney.setUserId(1L);
	    userDayMoney.setTotalMoney(10000F);
	    userDayMoney.setCreatedBy(1L);
	    userDayMoneyService.addUserDayMoney(userDayMoney);
    }
    
    @Test
    public void testFindUserDayMoney(){
    	 UserDayMoney userDayMoney=new UserDayMoney();
    	 userDayMoney.setUserId(1L);
    	 userDayMoney.setAccountDay(new Date());
    	 List<UserDayMoney> fundList= userDayMoneyService.findUserDayMoney(userDayMoney);
    	 Assert.assertEquals(1, fundList.size());
    	 
    }
	
    @Test
    public void testUpdateUserDayMoney(){
    	UserDayMoney userDayMoney=new UserDayMoney();
    	userDayMoney.setUserId(1L);
    	userDayMoney.setAccountDay(new Date());
	   	List<UserDayMoney> fundList= userDayMoneyService.findUserDayMoney(userDayMoney);
	   	userDayMoney=fundList.get(0);
	   	userDayMoney.setTotalMoney(88888F);
	   	userDayMoneyService.updateUserDayMoney(userDayMoney);
    }
    
    @Test
    public void testDeleteUserDayMoney(){
    	UserDayMoney userDayMoney=new UserDayMoney();
    	userDayMoney.setUserId(1L);
    	userDayMoney.setAccountDay(new Date());
    	userDayMoneyService.deleteUserDayMoney(userDayMoney);
    }
    
    @Test
    public void testFindUserSummary(){
    	UserSummary result = userDayMoneyService.findUserSummary(3L);
    	System.out.println(result.toString());
    }

}
