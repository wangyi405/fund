package com.acbcwy.fund.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acbcwy.fund.model.FundIncome;
import com.acbcwy.fund.model.FundInfo;
import com.acbcwy.fund.model.InvestData;
import com.acbcwy.fund.model.User;
import com.acbcwy.fund.service.DataBaseService;
import com.acbcwy.fund.service.FundIncomeService;
import com.acbcwy.fund.service.FundInfoService;
import com.acbcwy.fund.service.InvestDataService;
import com.acbcwy.fund.service.UserService;
import com.acbcwy.utils.ListToJson;

public class PcTerminalServlet extends HttpServlet {
	private static final long serialVersionUID = 8895917009360059278L;
	private final String servletPath="services/computer";
	
	public PcTerminalServlet() {
		super();
	}

	public void destroy() {
		super.destroy();  
	}
	
	public void init() throws ServletException {
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request,response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String currentReqPath=parseRequestPath(request);
		if("/islogin".equalsIgnoreCase(currentReqPath)){
			doIsLogin(request,response);
		}else if("/login".equalsIgnoreCase(currentReqPath)){
			doLogin(request,response);
		}else if("/searchuser".equalsIgnoreCase(currentReqPath)){
			doSearchUser(request,response);
		}else if("/saveUser".equalsIgnoreCase(currentReqPath)){
			doSaveUser(request,response);
		}else if("/deleteUser".equalsIgnoreCase(currentReqPath)){
			doDeleteUser(request,response);
		}else if("/searchUserById".equalsIgnoreCase(currentReqPath)){
			doSearchUserById(request,response);
		}else if("/buyOrSales".equalsIgnoreCase(currentReqPath)){
			doBuyOrSales(request,response);
		}else if("/showMoneyDetail".equalsIgnoreCase(currentReqPath)){
			doShowMoneyDetail(request,response);
		}else if("/findFundInfo".equalsIgnoreCase(currentReqPath)){
			doFindFundInfo(request,response);
		}else if("/saveFundInfo".equalsIgnoreCase(currentReqPath)){
			doSaveFundInfo(request,response);
		}else if("/findIncomeInfoByAccountDay".equalsIgnoreCase(currentReqPath)){
			doFindIncomeInfoByAccountDay(request,response);
		}else if("/findMaxDate".equalsIgnoreCase(currentReqPath)){
			doFindMaxDate(request,response);
		}else if("/saveYieldRate".equalsIgnoreCase(currentReqPath)){
			doSaveYieldRate(request,response);
		}else if("/findFundIncome".equalsIgnoreCase(currentReqPath)){
			doFindFundIncome(request,response);
		}else if("/backup".equalsIgnoreCase(currentReqPath)){
			doBackup(request,response);
		}else if("/findAllowBuyOrSale".equalsIgnoreCase(currentReqPath)){
			doFindAllowBuyOrSale(request,response);
		}
	}

	//查询收益率的最新日期
	private void doFindAllowBuyOrSale(HttpServletRequest request,HttpServletResponse response)throws IOException  {
		FundIncome fundIncome=new FundIncome();
		FundInfo fundInfo=(FundInfo)request.getSession().getAttribute("fundinfo");
		if(fundInfo!=null){
			fundIncome.setFundId(fundInfo.getFundId());
		}
		FundIncomeService fundIncomeService=getBean(request,FundIncomeService.class);
		String accountDay = fundIncomeService.findMaxDate(fundIncome);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(new Date());
		if(today.equals(accountDay)){
			response.getWriter().write("false");
		}else{
			response.getWriter().write("true");
		}
	}

	private void doIsLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		if(loginUser==null){
			response.getWriter().write("false");
		}else{
			response.getWriter().write("true");
		}
	}

	//获取备份文件存放的目录
	private String getBackupPath(){
		 String  filePath = this.getServletConfig().getServletContext().getRealPath("/");
		 filePath=filePath+"backup\\";
		 File file=new File(filePath);
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 return filePath;
	}


	
	
	private void doBackup(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		 isLogin(request,response);
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		 Date now = new Date();
		 String fileName = "data_"+sdf.format(now)+".bak";
		 String filePath=getBackupPath()+fileName;
		 DataBaseService dbService=this.getBean(request, DataBaseService.class);
		 boolean result=dbService.backup(filePath);
		 if(result){
			 // 读到流中
			InputStream inStream = new FileInputStream(filePath);
			// 设置输出的格式
			response.reset();
			response.setContentType("bin");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[1024];
			int len;
		    while ((len = inStream.read(b)) > 0){
		      response.getOutputStream().write(b, 0, len);
		    }
		    inStream.close();
		 }else{
			 throw new IOException("备份数据库失败！");
		 }
	}

	private void doFindFundIncome(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String fundId=request.getParameter("fundId");
		if(fundId==null || "".equals(fundId)){
			FundInfo fundInfo=(FundInfo)request.getSession().getAttribute("fundinfo");
			if(fundInfo!=null){
				fundId=fundInfo.getFundId()+"";
			}else{
				fundId="1";
			}
		}
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		FundIncome fundIncome=new FundIncome();
		try{
			fundIncome.setFundId(Integer.parseInt(fundId));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			fundIncome.setBeginDate(sdf.parse(beginDate));
			fundIncome.setEndDate(sdf.parse(endDate));
		}catch(Exception e){
			//throw new ServletException(e);
		}
		FundIncomeService fundIncomeService=getBean(request,FundIncomeService.class);
		List<FundIncome> listFundIncome = fundIncomeService.findFundIncome(fundIncome);
		response.getWriter().append(ListToJson.toJson(listFundIncome));
	}

	private void doSaveYieldRate(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		isLogin(request,response);
		String fundId=request.getParameter("_fundId");
		String operation=request.getParameter("_operation");
		String accountDay=request.getParameter("accountDay");
		String todayBookValue=request.getParameter("todayBookValue");
		String yieldRate=request.getParameter("yieldRate");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		FundIncome fundIncome=new FundIncome();
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		Long loginUserId=(loginUser==null ? 0 : loginUser.getUserId());
		try{
			fundIncome.setFundId(Integer.parseInt(fundId));
			fundIncome.setAccountDay(sdf.parse(accountDay));
			fundIncome.setTodayBookValue(Double.parseDouble(todayBookValue));
			fundIncome.setYieldRate(Double.parseDouble(yieldRate));
			fundIncome.setCreatedBy(loginUserId);
			fundIncome.setLastUpdatedBy(loginUserId);
		}catch(Exception e){
			throw new ServletException(e);
		}
		FundIncomeService fundIncomeService=getBean(request,FundIncomeService.class);
		if("create".equalsIgnoreCase(operation)){
			fundIncomeService.addFundIncome(fundIncome);
		}else{
			fundIncomeService.updateFundIncome(fundIncome);
		}
	}

	private void doFindMaxDate(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String sTemp=request.getParameter("fundId");
		FundIncome fundIncome=new FundIncome();
		if(sTemp!=null && !"".equals(sTemp)){
			fundIncome.setFundId(Integer.parseInt(sTemp));
		}else{
			fundIncome.setFundId(1);
		}
		FundIncomeService fundIncomeService=getBean(request,FundIncomeService.class);
		String maxDate=fundIncomeService.findMaxDate(fundIncome);
		if(maxDate==null || "".equals(maxDate)){
			maxDate="1980-01-01";
		}
		response.getWriter().append("{\"maxDate\":\""+maxDate+"\"}");
	}

	private void doFindIncomeInfoByAccountDay(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//查询条件:accountDay(结算日),fundId(基金ID),
		FundIncome fundIncome=new FundIncome();
		String accountDay=request.getParameter("accountDay");
		String fundId=request.getParameter("fundId");
		if(fundId!=null && !"".equals(fundId)){
			fundIncome.setFundId(Integer.parseInt(fundId));
		}else{
			FundInfo fundInfo = (FundInfo)request.getSession().getAttribute("fundinfo");
			if(fundInfo!=null){
				fundIncome.setFundId(fundInfo.getFundId());
			}
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(accountDay!=null && !"".equals(accountDay)){
				fundIncome.setAccountDay(sdf.parse(accountDay));
			}else{
				fundIncome.setAccountDay(sdf.parse(sdf.format(new Date())));
			}
		}catch(Exception e){
			throw new ServletException(e.getMessage());
		}
		
		FundIncomeService fundIncomeService = getBean(request, FundIncomeService.class);
		FundIncome result= fundIncomeService.findYieldRateByDay(fundIncome);
		if(result!=null){
			response.getWriter().append(result.toJson());
		}else{
			response.getWriter().append(fundIncome.toJson());
		}
	}

	private void doSaveFundInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		isLogin(request,response);
		FundInfo fundInfo=new FundInfo();
		fundInfo.setFundName(request.getParameter("fundName"));
		fundInfo.setAbbrName(request.getParameter("abbrName"));
		fundInfo.setManager(request.getParameter("manager"));
		fundInfo.setRecommend(request.getParameter("recommend"));
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		Long loginUserId = loginUser==null ? 0 : loginUser.getUserId();
		
		String operation=request.getParameter("operation");
		FundInfoService fundInfoService = getBean(request, FundInfoService.class);
		if("create".equalsIgnoreCase(operation)){
			//新增
			fundInfo.setCreatedBy(loginUserId);
			fundInfo.setLastUpdatedBy(loginUserId);
			fundInfoService.addFundInfo(fundInfo);
		}else{
			//修改
			Integer fundId=Integer.parseInt(request.getParameter("fundId"));
			fundInfo.setLastUpdatedBy(loginUserId);
			fundInfo.setFundId(fundId);
			fundInfoService.updateFundInfo(fundInfo);			
		}
		request.getSession().setAttribute("fundinfo", fundInfo);
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

	//显示投资详情
	private void doShowMoneyDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sTemp=request.getParameter("userId");
		if(sTemp==null || "".equals(sTemp)) {
			User loginUser =(User)request.getSession().getAttribute("loginUser");
			sTemp=loginUser.getUserId().toString();
		};
		Long userId=Long.parseLong(sTemp);
		InvestDataService investDataService = getBean(request,InvestDataService.class);
		InvestData investData=new InvestData();
		investData.setUserId(userId);
		List<InvestData>  investList = investDataService.findInvestData(investData);
		if(investList!=null ){
			response.getWriter().append(ListToJson.toJson(investList));
		}
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
	private void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			user.setUserType(0);
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
	
	//处理用户查询请求
	private void doSearchUser(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//获取前台传递过来的参数
		String userName=request.getParameter("userName");
		String idCardNo=request.getParameter("idCardNo");
		if(userName==null) userName="";
		if(idCardNo==null) idCardNo="";		
		User user=new User();
		user.setUserName("%"+userName+"%");
		user.setIdCardNo("%"+idCardNo+"%");
		UserService userService = getBean(request,UserService.class);
		List<User> userList=userService.findUser(user);
		response.getWriter().append(ListToJson.toJson(userList));
	}
	
	//根据用ID查询用户
	private void doSearchUserById(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//获取前台传递过来的参数
		String userId=request.getParameter("userId");
		UserService userService = getBean(request,UserService.class);
		User user=userService.findUserById(Long.parseLong(userId));
		if(user!=null){
			response.getWriter().append(user.toJson());
		}
	}

	//保存用户
	private void doSaveUser(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		isLogin(request,response);
		User user=new User();
		String userId=request.getParameter("userId");
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setIdCardNo(request.getParameter("idCardNo"));
		user.setUserType(Integer.parseInt(request.getParameter("userType")));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		Long loginUserId=loginUser==null ? 0 : loginUser.getUserId();
		if(userId!=null && !"".equals(userId)){ //修改用户
			user.setUserId(Long.parseLong(userId));
		    user.setLastUpdatedBy(loginUserId);
		    UserService userService = getBean(request,UserService.class);
			userService.updateUser(user);
		}else{//创建用户
		    user.setCreatedBy(loginUserId);
		    user.setLastUpdatedBy(loginUserId);
		    UserService userService = getBean(request,UserService.class);
			userService.addUser(user);
		}
	}
	
	//删除用户
	private void doDeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		isLogin(request,response);
		String[] userIds=request.getParameter("userIds").split(",");
		UserService userService = getBean(request,UserService.class);
        for(String userId : userIds){
        	userService.deleteUser(Long.parseLong(userId));
        }
	}
	
	//用户申赎资金
	private void doBuyOrSales(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		isLogin(request,response);
		Long userId=Long.parseLong(request.getParameter("userId"));
		Float investAmount=Float.parseFloat(request.getParameter("investAmount"));
		Integer investDirection=Integer.parseInt(request.getParameter("investDirection"));
		InvestData investData=new InvestData();
		investData.setUserId(userId);
		investData.setInvestAmount(investAmount);
		investData.setInvestDirection(investDirection);
		investData.setInvestDate(new Date());
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		Long loginUserId= loginUser==null ? 0 : loginUser.getUserId();
		investData.setCreatedBy(loginUserId);
		investData.setLastUpdatedBy(loginUserId);
		InvestDataService investDataService = getBean(request,InvestDataService.class);
		investDataService.addInvestData(investData);
	}
	
	private void isLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException{
		User loginUser=(User)request.getSession().getAttribute("loginUser");
		if(loginUser==null) throw new ServletException("没有登录，请先登录！");
	}
}
