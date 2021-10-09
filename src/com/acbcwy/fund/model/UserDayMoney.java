package com.acbcwy.fund.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDayMoney implements IObjectToJson, Serializable{
	private static final long serialVersionUID = 6179725840068003226L;
	private Long userId ;               //用户ID  
	private Date accountDay;            // 结算日 
	private Float totalMoney;           /* 总资金 */
	private Long createdBy;
	private Date createTime;
	private Long lastUpdatedBy ;
	private Date lastUpdateTime ;
	
	private Double yieldRate;
	
	@Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"userId\":"+userId);
			sb.append(",\"accountDay\":\""+accountDay+"\"");
			sb.append(",\"totalMoney\":"+totalMoney);
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
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getAccountDay() {
		return accountDay;
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
	public Float getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
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

	public void setYieldRate(Double yieldRate) {
		this.yieldRate = yieldRate;
	}

	public Double getYieldRate() {
		return yieldRate;
	}

}
