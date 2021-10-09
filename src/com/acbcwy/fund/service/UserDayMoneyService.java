package com.acbcwy.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.UserDayMoneyDao;
import com.acbcwy.fund.model.InvestData;
import com.acbcwy.fund.model.UserDayMoney;
import com.acbcwy.fund.model.UserSummary;

@Service
public class UserDayMoneyService {
	@Autowired
	private UserDayMoneyDao userDayMoneyDao;
	
	public void addUserDayMoney(UserDayMoney userDayMoney){
		userDayMoneyDao.addUserDayMoney(userDayMoney);
	}
	public void updateUserDayMoney(UserDayMoney userDayMoney){
		userDayMoneyDao.updateUserDayMoney(userDayMoney);
	}
	public void deleteUserDayMoney(UserDayMoney userDayMoney){
		userDayMoneyDao.deleteUserDayMoney(userDayMoney);
	}
	public List<UserDayMoney> findUserDayMoney(UserDayMoney userDayMoney){
		return userDayMoneyDao.findUserDayMoney(userDayMoney);
	}
	
	public void updateUserDayMoneyByInvestData(InvestData investData){
		userDayMoneyDao.updateUserDayMoneyByInvestData(investData);
	}
	
	/**
	 * 用户资产信息汇总
	 * @param userId
	 * @return
	 */
	public UserSummary findUserSummary(Long userId){
		return userDayMoneyDao.findUserSummary(userId);
	}

	/**
	 * 
	 * @param userDayMoney(yieldDate,accountDay,createdBy,lastUpdatedBy)
	 */
	public void computeUserDayMoney(UserDayMoney userDayMoney){
		//1.删除指定天（最新一天）的数据
		deleteUserDayMoney(userDayMoney);
		
		//2.重新计算用户数据，昨日的资金总额*今日的基金收益率+今日的投资总额
		userDayMoneyDao.computeUserDayMoney(userDayMoney);
	}
}
