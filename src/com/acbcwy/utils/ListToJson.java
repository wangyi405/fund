package com.acbcwy.utils;

import java.util.List;

import com.acbcwy.fund.model.IObjectToJson;

public class ListToJson {
	public static String toJson(List<?> list){
		StringBuffer sb=new StringBuffer();
		sb.append("{\"total\":"+list.size());
			sb.append(",\"rows\":[");
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Object obj=list.get(i);
					if(obj instanceof IObjectToJson){
						sb.append(((IObjectToJson) obj).toJson());
					}else{
						sb.append(obj.toString());
					}
					if(i<list.size()-1){
						sb.append(",");
					}
				}
			}
			sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println(Math.pow(1.05,1d/365d));
		System.out.println(Math.pow(1.0001336806171135d, 10*365));
		
	
		
		
		
	}
    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}
