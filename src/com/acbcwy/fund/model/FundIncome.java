package com.acbcwy.fund.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FundIncome implements IObjectToJson,Serializable{
	private static final long serialVersionUID = -4972452465949446220L;
	private Integer fundId ;              //基金Id  
	private Date accountDay ;             //结算日期  
	private Double yieldRate ;            //当日收益率 
	private Double todayBookValue;        //今日账面净值
	private Double preBookValue;          //上日账面净值
	private Double buyTotalValue;         //申购总金额
	private Double saleTotalValue;        //赎回总金额
	private Long createdBy;
	private Date createTime ;
	private Long lastUpdatedBy;
	private Date lastUpdateTime ;
	
	//冗余字段
	private String fundName;
	private String abbrName;         //基金简称
	private String createdByName;
	private String lastUpdatedByName;
	private Date beginDate;
	private Date endDate;
	private Double accumulate;             //累积收益率
	
	@Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"fundId\":"+fundId);
			sb.append(",\"fundName\":\""+ fundName+"\"");	
			sb.append(",\"abbrName\":\""+ abbrName+"\"");	
			sb.append(",\"accountDay\":\""+formatDate(accountDay,"yyyy-MM-dd")+"\"");
			sb.append(",\"preBookValue\":"+preBookValue);
			sb.append(",\"todayBookValue\":"+todayBookValue);
			sb.append(",\"buyTotalValue\":"+buyTotalValue);
			sb.append(",\"saleTotalValue\":"+saleTotalValue);
			sb.append(",\"yieldRate\":"+yieldRate);
			sb.append(",\"createdBy\":"+ createdBy);
			sb.append(",\"createTime\":\""+formatDate(createTime,"yyyy-MM-dd HH:mm:ss")+"\"");
			sb.append(",\"lastUpdatedBy\":"+ lastUpdatedBy);
			sb.append(",\"lastUpdateTime\":\""+formatDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss")+"\"");
			sb.append(",\"createdByName\":\""+ createdByName+"\"");	
			sb.append(",\"lastUpdatedByName\":\""+ lastUpdatedByName+"\"");	
			sb.append(",\"accumulate\":"+ accumulate);
		sb.append("}");
		return sb.toString();
	}	
	
	public String formatDate(Date date,String format){
		if(date==null) return "";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public Date getAccountDay() {
		return accountDay;
	}
	
	public Double getAccumulate() {
		return accumulate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Double getBuyTotalValue() {
		return buyTotalValue;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	
	public Date getEndDate() {
		return endDate;
	}

	public Integer getFundId() {
		return fundId;
	}

	public String getFundName() {
		return fundName;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public Double getPreBookValue() {
		return preBookValue;
	}
	public Double getSaleTotalValue() {
		return saleTotalValue;
	}
	public Double getTodayBookValue() {
		return todayBookValue;
	}

	public Double getYieldRate() {
		return yieldRate;
	}

	public void setAccountDay(Date accountDay) {
		if(accountDay!=null){
			try{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date= sdf.parse(sdf.format(accountDay));
				this.accountDay=date;
			}catch(Exception e){
				this.accountDay=null;
			}
		}else{
			this.accountDay=null;
		}
	}

	public void setAccumulate(Double accumulate) {
		this.accumulate = accumulate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setBuyTotalValue(Double buyTotalValue) {
		this.buyTotalValue = buyTotalValue;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public void setLastUpdatedby(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}                           
	public void setPreBookValue(Double preBookValue) {
		this.preBookValue = preBookValue;
	}	
    public void setSaleTotalValue(Double saleTotalValue) {
		this.saleTotalValue = saleTotalValue;
	}
    public void setTodayBookValue(Double todayBookValue) {
		this.todayBookValue = todayBookValue;
	}
    
	public void setYieldRate(Double yieldRate) {
		this.yieldRate = yieldRate;
	}

	@Override
	public String toString(){
		return toJson();
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	public String getAbbrName() {
		return abbrName;
	}

}
