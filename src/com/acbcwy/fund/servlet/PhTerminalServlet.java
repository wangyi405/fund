package com.acbcwy.fund.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acbcwy.fund.model.FundInfo;
import com.acbcwy.fund.model.User;
import com.acbcwy.fund.model.UserSummary;
import com.acbcwy.fund.service.FundInfoService;
import com.acbcwy.fund.service.UserDayMoneyService;
import com.acbcwy.fund.service.UserService;

public class PhTerminalServlet extends HttpServlet {
	private static final long serialVersionUID = -8541935791225059104L;
	private final String servletPath="services/phone";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String currentReqPath=parseRequestPath(request);
		if("/login".equalsIgnoreCase(currentReqPath)){
			doLogin(request,response);
		}else if("/findUserSummary".equalsIgnoreCase(currentReqPath)){
			doFindUserSummary(request,response);
		}else if("/findFundInfo".equalsIgnoreCase(currentReqPath)){
			doFindFundInfo(request,response);
		}else if("/saveUser".equalsIgnoreCase(currentReqPath)){
			doSaveUser(request,response);
		}
	}


	private void doFindUserSummary(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		Long userId= loginUser==null ? 0 : loginUser.getUserId();
		UserDayMoneyService userDayMoneyService=getBean(request, UserDayMoneyService.class);
		UserSummary userSummary = userDayMoneyService.findUserSummary(userId);
		if(userSummary==null){
			userSummary=new UserSummary();
			userSummary.setUserName(loginUser.getUserName());
			userSummary.setTotalMoney(0d);
			userSummary.setTotalInvest(0d);
			userSummary.setTodayYieldRate(0d);
			userSummary.setEmail(loginUser.getEmail());
			userSummary.setPhone(loginUser.getPhone());
			userSummary.setIdCardNo(loginUser.getIdCardNo());
		}
		response.getWriter().append(userSummary.toJson());
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	//获取用户请求路径
	private String parseRequestPath(HttpServletRequest req){
		String uri=req.getRequestURI();
		if(uri.contains(servletPath)){
			int pos=uri.indexOf(servletPath);
			String reqPath=uri.substring(pos+servletPath.length());
			return reqPath;
		}else{
			return null;
		}
	}

	//根据类获取bean
	private <T> T getBean(HttpServletRequest request,Class<T> clazz){
		ServletContext sc = request.getSession().getServletContext();
		WebApplicationContextUtils.getWebApplicationContext(sc);
		T bean=WebApplicationContextUtils.getWebApplicationContext(sc).getBean(clazz);
		return bean;
	} 
	
	//处理用户登录
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取前台传递过来的参数
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");	
		String result="";
		if(userName==null || "".equals(userName) || password==null || "".equals(password)){
			result="{'login':'fail'}";
		}else{
			User user=new User();
			user.setUserName(userName);
			user.setPassword(password);
			user.setUserType(1);
			UserService userService = getBean(request,UserService.class);
			List<User> userList=userService.findUser(user);
			if(userList!=null && userList.size()>0){
				request.getSession().setAttribute("loginUser", userList.get(0));
				result="{'login':'success'}";
			}else{
				result="{'login':'fail'}";
			}
		}
		response.getWriter().append(result);
	}	
	
	//查询基金信息
	private void doFindFundInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		FundInfoService fundInfoService=this.getBean(request, FundInfoService.class);
		FundInfo fundInfo=fundInfoService.findFundInfo(new FundInfo());
		if(fundInfo!=null){
			request.getSession().setAttribute("fundinfo", fundInfo);
			response.getWriter().append(fundInfo.toJson());
		}
	}	
	
	//保存用户
	private void doSaveUser(HttpServletRequest request, HttpServletResponse response){
		User user=new User();
		String userId=request.getParameter("userId");
		if(userId==null){
			User loginUser =(User)request.getSession().getAttribute("loginUser");
			userId =loginUser.getUserId()+"";
		}
		user.setUserName(request.getParameter("userName"));
		String sTemp=request.getParameter("password");
		if(null!=sTemp && !"".equals(sTemp)){
			user.setPassword(sTemp);
		}else{
			user.setPassword(null);
		}
		user.setIdCardNo(request.getParameter("idCardNo"));
		user.setUserType(1);
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		user.setUserId(Long.parseLong(userId));
	    user.setLastUpdatedBy(Long.parseLong(userId));
	    UserService userService = getBean(request,UserService.class);
		userService.updateUser(user);
	
	}
}
