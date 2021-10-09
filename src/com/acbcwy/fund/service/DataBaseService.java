package com.acbcwy.fund.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataBaseService {
	// MySQL安装目录的Bin目录的绝对路径  
	@Value("#{config.mysqlBinPath}")
    private String mysqlBinPath;  
    
    // 访问MySQL数据库的用户名  
	@Value("#{config.userName}")
    private String username;  
    
    // 访问MySQL数据库的密码 
	@Value("#{config.password}")
    private String password;  
	
	//数据库名称
	@Value("#{config.dbName}")
	private String dbName;
    
    public String getMysqlBinPath() {  
        return mysqlBinPath;  
    }
    
    public void setMysqlBinPath(String mysqlBinPath) {  
        this.mysqlBinPath = mysqlBinPath;  
    }
    
    public String getUsername() {  
        return username;  
    }  
    
    public void setUsername(String username) {  
        this.username = username;  
    }  
    
    public String getPassword() {  
        return password;  
    }  
    
    public void setPassword(String password) {  
        this.password = password;  
    }  
    
    public boolean backup(String toFilePath){
    	int count=20;
    	try{
	        String command = "cmd /c " + mysqlBinPath + "mysqldump -u" + username + " -p" + password + " --set-charset=utf8 " + dbName+" > " + toFilePath;  
	        Runtime.getRuntime().exec(command); 
	        
	        File file=new File(toFilePath);
	        while(!file.exists()){
	        	if((count--)<=0){
	        		return false;
	        	}else{
	        		Thread.sleep(1000);
	        	}
	        }
	        return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public void restore (String toFilePath) throws IOException{
     	String[] command={"cmd","/C",mysqlBinPath + "mysql -u" + username + " -p" + password +" " + dbName+" < " + toFilePath };
        Process p=Runtime.getRuntime().exec(command); 
        try{
        	Thread.sleep(1000);
        }catch(Exception e){
        	
        }
      //取得命令结果的输出流    
        InputStream fis=p.getInputStream();    
       //用一个读输出流类去读    
        InputStreamReader isr=new InputStreamReader(fis);    
       //用缓冲器读行    
        BufferedReader br=new BufferedReader(isr);    
        String line=br.readLine();    
       //直到读完为止    
       while(line!=null)    
        {    
            System.out.println(line);   
            line=br.readLine();
        }    
    }
    
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbName() {
		return dbName;
	}  
	
	
	@Override
	public String toString(){
		String result="dbName = "+dbName+
		              ", mysqlBinPath="+mysqlBinPath+
		              ", username="+username+
		              ", password="+password;
		return result;
	}
}  