package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.FundIncome;
import com.acbcwy.fund.model.InvestData;

public interface InvestDataDao {
	public List<InvestData> findInvestData(InvestData investData);
	public void addInvestData(InvestData investData);
	public void updateInvestData(InvestData investData);
	public void deleteInvestData(Long investData);
	public FundIncome findSummaryByDay(FundIncome fundIncome);
}
