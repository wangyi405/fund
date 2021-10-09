package com.acbcwy.fund.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvestData implements IObjectToJson, Serializable{
	private static final long serialVersionUID = 9110208133199147695L;
	private Long investId;                // 投资ID  
	private Long userId;                  // 用户ID 
	private Date investDate;              // 投资日期  
	private Float investAmount;           // 投资金额  
	private Integer investDirection;      // 投资方向  ,1:买入，-1：卖出
	private Long createdBy ;
	private Date createTime ;
	private Long lastUpdatedBy;
	private Date lastUpdateTime ;
	
	//冗余字段
	private String userName;
	private String createdByName;
	private String lastUpdatedByName;
	
	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

	@Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"investId\":"+investId);
			sb.append(",\"userId\":"+userId);
			sb.append(",\"userName\":\""+ userName+"\"");	
			sb.append(",\"investDate\":\""+formatDate(investDate,"yyyy-MM-dd")+"\"");
			sb.append(",\"investAmount\":"+investAmount);
			sb.append(",\"investDirection\":"+investDirection);
			sb.append(",\"createdBy\":"+ createdBy);
			sb.append(",\"createTime\":\""+ formatDate(createTime,"yyyy-MM-dd HH:mm:ss")+"\"");
			sb.append(",\"lastUpdatedBy\":"+ lastUpdatedBy);
			sb.append(",\"lastUpdateTime\":\""+formatDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss")+"\"");
			sb.append(",\"createdByName\":\""+ createdByName+"\"");		
			sb.append(",\"lastUpdatedByName\":\""+ lastUpdatedByName+"\"");	
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJson();
	}
    
	public Long getInvestId() {
		return investId;
	}
	public void setInvestId(Long investId) {
		this.investId = investId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getInvestDate() {
		return investDate;
	}
	public void setInvestDate(Date investDate) {
		if(investDate!=null){
			try{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date= sdf.parse(sdf.format(investDate));
				this.investDate=date;
			}catch(Exception e){
				this.investDate=null;
			}
		}else{
			this.investDate=null;
		}
	}
	public Float getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(Float investAmount) {
		this.investAmount = investAmount;
	}
	
	public boolean isBuy(){
		return investDirection==0 ? true :false;
	} 
	
	public Integer getInvestDirection() {
		return investDirection;
	}
	public void setInvestDirection(Integer investDirection) {
		this.investDirection = investDirection;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
	public String formatDate(Date date,String format){
		if(date==null) return "";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
