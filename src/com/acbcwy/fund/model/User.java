package com.acbcwy.fund.model;

import java.io.Serializable;
import java.util.Date;
public class User implements IObjectToJson, Serializable{
	private static final long serialVersionUID = 4565314589504846844L;
	private Long userId;                  // 用户ID  
	private String userName;              // 用户名称  
	private String idCardNo;              // 身份证号码 
	private String password;              // 密码  
	private String phone;                 // 电话号码 
	private String email;                 // 邮箱 
	private Integer userType;             // 用户类型：0:系统管理员，1：投资用户
	private Long createdBy;
	private Date createTime;
	private Long lastUpdatedBy;
	private Date lastUpdateTime;
	private boolean fuzzyFlag=false;      //模糊查询标志
	
	
	//冗余字段
	private String createdByName;
	private String lastUpdatedByName;
	
	//计算字段
	private Double totalMoney;
	
	@Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"userId\":"+userId);
			sb.append(",\"userName\":\""+userName+"\"");
			sb.append(",\"idCardNo\":\""+idCardNo+"\"");
			sb.append(",\"password\":\""+password+"\"");
			sb.append(",\"phone\":\""+phone+"\"");
			sb.append(",\"email\":\""+email+"\"");
			sb.append(",\"userType\":"+userType);
			sb.append(",\"createdBy\":"+ createdBy);
			sb.append(",\"createTime\":\""+ createTime+"\"");
			sb.append(",\"lastUpdatedBy\":"+ lastUpdatedBy);
			sb.append(",\"lastUpdateTime\":\""+ lastUpdateTime+"\"");	
			sb.append(",\"createdByName\":\""+ createdByName+"\"");		
			sb.append(",\"lastUpdatedByName\":\""+ lastUpdatedByName+"\"");		
			sb.append(",\"totalMoney\":"+ totalMoney);		
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setFuzzyFlag(boolean fuzzyFlag) {
		this.fuzzyFlag = fuzzyFlag;
	}

	public boolean isFuzzyFlag() {
		return fuzzyFlag;
	}

}
