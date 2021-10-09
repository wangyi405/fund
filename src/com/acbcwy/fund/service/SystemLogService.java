package com.acbcwy.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.SystemLogDao;
import com.acbcwy.fund.model.SystemLog;

@Service
public class SystemLogService {
	@Autowired
	private SystemLogDao systemLogDao;
	public void addSystemLog(SystemLog systemLog){
		systemLogDao.addSystemLog(systemLog);
	}
	public void updateSystemLog(SystemLog systemLog){
		systemLogDao.updateSystemLog(systemLog);
	}
	public void deleteSystemLog(Long systemLogId){
		systemLogDao.deleteSystemLog(systemLogId);
	}
	public List<SystemLog> findSystemLog(SystemLog systemLog){
		return systemLogDao.findSystemLog(systemLog);
	}

}
