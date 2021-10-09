package com.acbcwy.fund.test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.User;
import com.acbcwy.fund.service.UserService;

public class UserServiceTest {	
	private ApplicationContext ctx=null;
	private UserService userService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		userService =(UserService)ctx.getBean("userService");
	}
	
    @Test
	public void testAddUser() throws UnsupportedEncodingException{
		User user=new User();
		user.setUserName(new String("按到".getBytes(),"utf-8"));
		user.setIdCardNo("12313123123123123");
		user.setPassword("123456");
		user.setPhone("13699885610");
		user.setEmail("abc@tom.com");
		user.setUserType(1);
		user.setCreatedBy(1L);
		user.setCreateTime(new Date());
		user.setLastUpdatedBy(1L);
		user.setLastUpdateTime(new Date());
		userService.addUser(user);
    }
    
    @Test
    public void testFindUser(){
    	User user=new User();
    	user.setUserId(9L);
    	List<User> userList=userService.findUser(user);
    	Assert.assertEquals(1, userList.size());
    }
	
    @Test
    public void testUpdateUser(){
    	User user=new User();
    	user.setUserId(1L);
    	List<User> userList=userService.findUser(user);
    	user=userList.get(0);
    	user.setUserName("lisi");
    	user.setPhone("1233331");
    	userService.updateUser(user);
    }
    
    @Test
    public void testDeleteUser(){
    	userService.deleteUser(3L);
    }

}
