package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.FundInfo;

public interface FundInfoDao {
	public List<FundInfo> findFundInfo(FundInfo fundInfo);
	public void updateFundInfo(FundInfo fundInfo);
	public void addFundInfo(FundInfo fundInfo);
	public void deleteFundInfo(Integer fundId);
}
