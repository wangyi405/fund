package com.acbcwy.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.InvestDataDao;
import com.acbcwy.fund.model.InvestData;

@Service
public class InvestDataService {
	@Autowired
	private InvestDataDao investDataDao;
	
	@Autowired
	private UserDayMoneyService userDayMoneyService;
	
	/**
	 * 如果当天已经汇总过收益，要修改对应的User_Day_Money中的数据
	 * @param investData
	 */
	public void addInvestData(InvestData investData){
		investDataDao.addInvestData(investData);
		userDayMoneyService.updateUserDayMoneyByInvestData(investData);
	}
	public void updateInvestData(InvestData investData){
		investDataDao.updateInvestData(investData);
	}
	public void deleteInvestData(Long investId){
		investDataDao.deleteInvestData(investId);
	}
	public List<InvestData> findInvestData(InvestData investData){
		return investDataDao.findInvestData(investData);
	}

}
