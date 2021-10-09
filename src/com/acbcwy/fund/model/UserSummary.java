package com.acbcwy.fund.model;


public class UserSummary extends User{
	private static final long serialVersionUID = 1702877038500960633L;                     
	private Double todayYieldRate;                       
	private Double totalInvest;
	
	
	@Override
	public String toJson() {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"userId\":"+getUserId());
			sb.append(",\"userName\":\""+getUserName()+"\"");
			sb.append(",\"idCardNo\":\""+getIdCardNo()+"\"");
			sb.append(",\"phone\":\""+getPhone()+"\"");
			sb.append(",\"email\":\""+getEmail()+"\"");
			sb.append(",\"userType\":"+getUserType());	
			sb.append(",\"todayYieldRate\":"+todayYieldRate);
			sb.append(",\"totalMoney\":"+ getTotalMoney());
			sb.append(",\"totalInvest\":"+ totalInvest);
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return toJson();
	}

	public Double getTodayYieldRate() {
		return todayYieldRate;
	}

	public void setTodayYieldRate(Double todayYieldRate) {
		this.todayYieldRate = todayYieldRate;
	}
	
	public Double getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(Double totalInvest) {
		this.totalInvest = totalInvest;
	}

}
