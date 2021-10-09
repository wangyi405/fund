package com.acbcwy.fund.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.FundIncomeDao;
import com.acbcwy.fund.model.FundIncome;
import com.acbcwy.fund.model.UserDayMoney;

@Service
public class FundIncomeService {
	
	@Autowired
	private FundIncomeDao fundIncomeDao;
	
	@Autowired
	private UserDayMoneyService userDayMoneyService;
	
	/**
	 * 限制：时间不能早于已经录入的日期之前
	 * 1.插入录入的基金收益率数据
	 * 2.汇总用户数据
	 * @param fundIncome
	 */
	public void addFundIncome(FundIncome fundIncome){
		fundIncomeDao.addFundIncome(fundIncome);
		UserDayMoney userDayMoney=new UserDayMoney();
		userDayMoney.setAccountDay(fundIncome.getAccountDay());
		userDayMoney.setYieldRate(fundIncome.getYieldRate());
		userDayMoney.setCreatedBy(fundIncome.getCreatedBy());
		userDayMoney.setLastUpdatedBy(fundIncome.getLastUpdatedBy());
		userDayMoneyService.computeUserDayMoney(userDayMoney);
	}
	
	/**
	 * 限制：只能修改最近录入一天的数据
	 * 1.修改最近录入的数据
	 * 2.重新汇总用户数据
	 * @param fundIncome
	 */
	public void updateFundIncome(FundIncome fundIncome){
		fundIncomeDao.updateFundIncome(fundIncome);
		UserDayMoney userDayMoney=new UserDayMoney();
		userDayMoney.setAccountDay(fundIncome.getAccountDay());
		userDayMoney.setYieldRate(fundIncome.getYieldRate());
		userDayMoney.setCreatedBy(fundIncome.getCreatedBy());
		userDayMoney.setLastUpdatedBy(fundIncome.getLastUpdatedBy());
		userDayMoneyService.computeUserDayMoney(userDayMoney);
	}
	
	public void deleteFundIncome(FundIncome fundIncome){
		fundIncomeDao.deleteFundIncome(fundIncome);
	}
	
	public List<FundIncome> findFundIncome(FundIncome fundIncome){
		DecimalFormat df = new DecimalFormat("#.0000000000");
		List<FundIncome> fundList = fundIncomeDao.findFundIncome(fundIncome);
		if(fundList.size()>0){
			FundIncome income= fundList.get(0);
			income.setAccumulate(income.getYieldRate());
		}
		for(int i=1;i<fundList.size();i++){
			FundIncome preIncome= fundList.get(i-1);
			FundIncome curIncome= fundList.get(i);
			String sTemp=df.format(preIncome.getAccumulate()*curIncome.getYieldRate());
			curIncome.setAccumulate(Double.parseDouble(sTemp));
		}
		return fundList;
	}
	
	public String findMaxDate(FundIncome fundIncome){
		return fundIncomeDao.findMaxDate(fundIncome);
	}	

	/**
	 * 获取指定天的基金收益率
	 * @param fundIncome
	 * @return
	 */
	public FundIncome findYieldRateByDay(FundIncome fundIncome){
		FundIncome result=null;
		Date accountDay=fundIncome.getAccountDay();
		fundIncome.setEndDate(accountDay);
		fundIncome.setAccountDay(null);
		//查出小于结算日后的所有数据,按结算日升序排序
		List<FundIncome> list=fundIncomeDao.findFundIncome(fundIncome);
		if(list==null || list.size()<=0){ //不存在收益数据,			
			//1:获取当前日期前所有的申赎数据，申购数据总额作为上日账面，
			result=fundIncome;
			fundIncome.setAccountDay(accountDay);
			fundIncome.setEndDate(null);
			FundIncome fiManyDays=fundIncomeDao.findBuySaleTotalValueLessThanDay(fundIncome);
			if(fiManyDays!=null){
				Double moneyManyDays=fiManyDays.getBuyTotalValue() - fiManyDays.getSaleTotalValue();
				moneyManyDays= moneyManyDays < 0 ? 0: moneyManyDays;
				result.setPreBookValue(moneyManyDays);
			}else{
				result.setPreBookValue(0.0);
			}
			
			// 2：获取当前日期的申赎数据，
			FundIncome fiCurrDay=fundIncomeDao.findBuySaleTotalValueCurrDay(fundIncome);
			if(fiCurrDay !=null){
				result.setBuyTotalValue(fiCurrDay.getBuyTotalValue());
				result.setSaleTotalValue(fiCurrDay.getSaleTotalValue());
			}else{
				result.setBuyTotalValue(0.0);
				result.setSaleTotalValue(0.0);
			}
		}else{ //存在收益数据
			//1.获取当日申赎数据
			fundIncome.setAccountDay(accountDay);
			fundIncome.setEndDate(null);
			FundIncome fiCurrDay=fundIncomeDao.findBuySaleTotalValueCurrDay(fundIncome);

			//取最新一天的数据
			int count=list.size();
			result=list.get(count-1);
			if(accountDay.equals(result.getAccountDay())){  //2.1该天数据已存在
				//设置申赎数据
				if(fiCurrDay !=null){
					result.setBuyTotalValue(fiCurrDay.getBuyTotalValue());
					result.setSaleTotalValue(fiCurrDay.getSaleTotalValue());
				}else{
					result.setBuyTotalValue(0.0);
					result.setSaleTotalValue(0.0);
				}	
				if(list.size()>1){ //存在上一天数据
					result.setPreBookValue(list.get(count-2).getTodayBookValue());
					
				}else{ //不存在上一天数据
					Double preBookValue=0.0 ; 
					result.setPreBookValue(preBookValue);
				}

			}else{   //2.2该天数据不存在
                result=new FundIncome();
                result.setFundId(fundIncome.getFundId());
                result.setPreBookValue(list.get(list.size()-1).getTodayBookValue());
                result.setAccountDay(accountDay);
                if(fiCurrDay !=null){
					result.setBuyTotalValue(fiCurrDay.getBuyTotalValue());
					result.setSaleTotalValue(fiCurrDay.getSaleTotalValue());
				}else{
					result.setBuyTotalValue(0.0);
					result.setSaleTotalValue(0.0);
				}	
			}
		}
		return result;
	}
}
