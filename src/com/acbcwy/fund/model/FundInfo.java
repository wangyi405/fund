package com.acbcwy.fund.model;

import java.io.Serializable;
import java.util.Date;

public class FundInfo implements IObjectToJson, Serializable{
	private static final long serialVersionUID = 7126596656611921772L;
	private Integer fundId ;          // 基金ID 
    private String fundName ;         // 基金名称 
    private String abbrName;          //基金简称  
    private String manager ;          // 基金经理 
    private String recommend ;        // 基金介绍  
    private Long createdBy ;                             
    private Date createTime ;	
    private Long lastUpdatedBy ;
    private Date lastUpdateTime;
    
    @Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"fundId\":"+fundId);
			sb.append(",\"fundName\":\""+fundName+"\"");
			sb.append(",\"abbrName\":\""+abbrName+"\"");
			sb.append(",\"manager\":\""+manager+"\"");
			sb.append(",\"recommend\":\""+recommend+"\"");
			sb.append(",\"createdBy\":"+ createdBy);
			sb.append(",\"createTime\":\""+ createTime+"\"");
			sb.append(",\"lastUpdatedBy\":"+ lastUpdatedBy);
			sb.append(",\"lastUpdateTime\":\""+ lastUpdateTime+"\"");			
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJson();
	}
    
    
    
	public Integer getFundId() {
		return fundId;
	}
	public String getFundName() {
		return fundName;
	}
	public String getManager() {
		return manager;
	}
	public String getRecommend() {
		return recommend;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	public String getAbbrName() {
		return abbrName;
	}
}
