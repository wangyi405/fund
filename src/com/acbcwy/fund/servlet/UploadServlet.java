package com.acbcwy.fund.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acbcwy.fund.service.DataBaseService;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 6614736337186359610L;
    private String tempPath;      // 临时文件目录   
    private String uploadPath;    // 上传文件存放目录
    
    // 初始化  
    public void init(ServletConfig config) throws ServletException  
    {  
        super.init(config);  
        // 从配置文件中获得初始化参数  
        tempPath = getTempPath(); 
        uploadPath =getUploadPath();
    }  
      
	//获取上传文件存放的临时目录
	private String getTempPath(){
		 String  filePath = this.getServletConfig().getServletContext().getRealPath("/");
		 filePath=filePath+"temp\\";
		 File file=new File(filePath);
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 return filePath;
	}
	
	//获取上传文件存放的目录
	private String getUploadPath(){
		 String  filePath = this.getServletConfig().getServletContext().getRealPath("/");
		 filePath=filePath+"upload\\";
		 File file=new File(filePath);
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 return filePath;
	}
	
    public void doPost(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        res.setContentType("text/plain;charset=utf-8");  
        try{  
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
            // threshold 极限、临界值，即硬盘缓存 1M  
            diskFactory.setSizeThreshold(1024 * 1024);  
            
            // repository 贮藏室，即临时文件目录  
            diskFactory.setRepository(new File(tempPath));  
            ServletFileUpload upload = new ServletFileUpload(diskFactory);  

            // 设置允许上传的最大文件大小 40M  
            upload.setSizeMax(40 * 1024 * 1024);  
            
            // 解析HTTP请求消息头  
            List<FileItem> fileItems = upload.parseRequest(req);  
            Iterator<FileItem> iter = fileItems.iterator();  
            while(iter.hasNext())  
            {  
                FileItem item = (FileItem)iter.next();  
                if(item.isFormField())  
                {  
                    processFormField(item);  
                }else{  
                    String uploadFile= processUploadFile(item);  
                    DataBaseService dbService=this.getBean(req, DataBaseService.class);
                    dbService.restore(uploadFile);
                    res.getOutputStream().println("success");      
                }  
                break;
            } 
        }catch(Exception e){  
        	res.getOutputStream().println("failure");  
            System.out.println("使用 fileupload 包时发生异常 ...");  
            e.printStackTrace();  
        }  
    }  
 
 
    // 处理表单内容  
    private void processFormField(FileItem item)  
        throws Exception  
    {  
        String name = item.getFieldName();  
        String value = item.getString();          
        System.out.println(name + " : " + value + "\r\n");  
    }  
      
    // 处理上传的文件  
    private String processUploadFile(FileItem item)  
        throws Exception  
    {  
        //此时的文件名包含了完整的路径，得注意加工一下  
        String filename = item.getName();         
        int index = filename.lastIndexOf("\\");  
        filename = filename.substring(index + 1, filename.length());  
 
        long fileSize = item.getSize();  
 
        if("".equals(filename) && fileSize == 0)  
        {             
            System.out.println("文件名为空 ...");  
            return "";   
        }  
 
        File uploadFile = new File(uploadPath + "/" + filename);  
        item.write(uploadFile);  
        return uploadFile.getAbsolutePath();
    }  
      
	//根据类获取bean
	private <T> T getBean(HttpServletRequest request,Class<T> clazz){
		ServletContext sc = request.getSession().getServletContext();
		WebApplicationContextUtils.getWebApplicationContext(sc);
		T bean=WebApplicationContextUtils.getWebApplicationContext(sc).getBean(clazz);
		return bean;
	} 

    public void doGet(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        doPost(req, res);  
    }  
}
