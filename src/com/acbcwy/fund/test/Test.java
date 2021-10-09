package com.acbcwy.fund.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Test {
	@org.junit.Test
	public void test() throws Exception{
		Class.forName("com.mysql.jdbc.Driver") ;   
		String url = "jdbc:mysql://127.0.0.1:3306/fund_one?useUnicode=true&characterEncoding=UTF-8" ;    
	    String username = "root" ;   
	    String password = "r00t@123456" ;  
	    Connection conn = DriverManager.getConnection(url , username , password ) ;
	    Statement stat = conn.createStatement();
	    String sql="INSERT INTO user(user_name, id_card_no, password, phone,email," +
	    		"user_type,createdby,create_time,last_updatedby,last_update_time) VALUES ("+
				   "\"张珊珊\",\"123456677\",\"12333\",\"1111111\",\"abc@bbb.com\"," +
				   "1,1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP())";
	    stat.executeUpdate(sql);
	}
}
