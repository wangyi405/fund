package com.acbcwy.fund.test;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acbcwy.fund.model.SystemLog;
import com.acbcwy.fund.service.SystemLogService;

public class SystemLogServiceTest {	
	private ApplicationContext ctx=null;
	private SystemLogService systemLogService=null;
	
	@Before
	public void setup(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		systemLogService =(SystemLogService)ctx.getBean("systemLogService");
	}
	
    @Test
	public void testAddSystemLog(){
		SystemLog systemLog=new SystemLog();
		systemLog.setContent("add a user");
		systemLog.setLogTime(new Date());
		systemLog.setUserId(1L);
		systemLogService.addSystemLog(systemLog);
    }
    
    @Test
    public void testFindSystemLog(){
    	SystemLog systemLog=new SystemLog();
    	systemLog.setLogId(1L);
    	List<SystemLog> systemLogList=systemLogService.findSystemLog(systemLog);
    	Assert.assertEquals(1, systemLogList.size());
    }
	
    @Test
    public void testUpdateSystemLog(){
    	SystemLog systemLog=new SystemLog();
    	systemLog.setLogId(1L);
    	List<SystemLog> systemLogList=systemLogService.findSystemLog(systemLog);
    	systemLog=systemLogList.get(0);
    	systemLog.setContent("remove a user!");
    	systemLogService.updateSystemLog(systemLog);
    }
    
    @Test
    public void testDeleteSystemLog(){
    	systemLogService.deleteSystemLog(1L);
    }

}
