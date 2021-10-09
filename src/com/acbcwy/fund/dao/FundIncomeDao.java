package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.FundIncome;

public interface FundIncomeDao {
	public List<FundIncome> findFundIncome(FundIncome fundIncome);
	public void addFundIncome(FundIncome fundIncome);
	public void updateFundIncome(FundIncome fundIncome);
	public void deleteFundIncome(FundIncome fundIncome);
	public String findMaxDate(FundIncome fundIncome);
	
	/**
	 * 根据日期返回当前日期前所有日期的申赎金额
	 * @param fundIncome
	 * @return
	 */
	public FundIncome findBuySaleTotalValueLessThanDay(FundIncome fundIncome);
	
	public FundIncome findBuySaleTotalValueCurrDay(FundIncome fundIncome);
	
	public List<FundIncome> findBuySaleTotalValueLastTwoDay(FundIncome fundIncome);
}
