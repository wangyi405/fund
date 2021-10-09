package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.InvestData;
import com.acbcwy.fund.model.UserDayMoney;
import com.acbcwy.fund.model.UserSummary;

public interface UserDayMoneyDao {
	public List<UserDayMoney> findUserDayMoney(UserDayMoney userDayMoney);
	public void updateUserDayMoney(UserDayMoney userDayMoney);
	public void addUserDayMoney(UserDayMoney userDayMoney);
	public void deleteUserDayMoney(UserDayMoney userDayMoney);
	public void computeUserDayMoney(UserDayMoney userDayMoney);
	public void updateUserDayMoneyByInvestData(InvestData investData);
	public UserSummary findUserSummary(Long userId);
	
}
