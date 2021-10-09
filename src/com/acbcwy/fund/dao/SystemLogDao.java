package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.SystemLog;

public interface SystemLogDao {
	public List<SystemLog> findSystemLog(SystemLog systemLog);
	public void updateSystemLog(SystemLog systemLog);
	public void addSystemLog(SystemLog systemLog);
	public void deleteSystemLog(Long systemLogId);
}
