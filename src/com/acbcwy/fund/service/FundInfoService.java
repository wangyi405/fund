package com.acbcwy.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.FundInfoDao;
import com.acbcwy.fund.model.FundInfo;

@Service
public class FundInfoService {
	@Autowired
	private FundInfoDao fundInfoDao;
	
	public void addFundInfo(FundInfo fundInfo){
		fundInfoDao.addFundInfo(fundInfo);
	}
	public void updateFundInfo(FundInfo fundInfo){
		fundInfoDao.updateFundInfo(fundInfo);
	}
	public void deleteFundInfo(Integer fundInfoId){
		fundInfoDao.deleteFundInfo(fundInfoId);
	}
	public FundInfo findFundInfo(FundInfo fundInfo){
		 List<FundInfo> list = fundInfoDao.findFundInfo(fundInfo);
		 if(list!=null && list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
	}
	
	public FundInfo findFundInfo(){
		 FundInfo fundInfo=new FundInfo();
		 List<FundInfo> list = fundInfoDao.findFundInfo(fundInfo);
		 if(list!=null && list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
	}

}
