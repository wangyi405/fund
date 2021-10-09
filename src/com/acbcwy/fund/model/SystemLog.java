package com.acbcwy.fund.model;

import java.io.Serializable;
import java.util.Date;

public class SystemLog implements IObjectToJson, Serializable{
	private static final long serialVersionUID = -5860513490102227152L;
	private Long logId;                 //日志ID/
	private Long userId;                //操作用户
	private Date logTime;             //操作时间
	private String content;             //日志内容 
	
	@Override
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
			sb.append("\"logId\":"+logId);
			sb.append(",\"userId\":"+userId);
			sb.append(",\"logTime\":\""+logTime+"\"");
			sb.append(",\"content\":\""+ content+"\"");			
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		return toJson();
	}	
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
