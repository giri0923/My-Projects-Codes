package com.noblebanking.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.noblebanking.bean.IDBean;
import com.noblebanking.bean.Login;
import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.TransferBean;
import com.noblebanking.bean.UserBean;
import com.noblebanking.dao.AdminDao;
import com.noblebanking.dao.EmployeeDao;
import com.noblebanking.dao.MerchantDao;
import com.noblebanking.dao.UserInterface;

@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	public MerchantDao merchantDao;

	public void setAppService(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}
	
	@Autowired
	public EmployeeDao employeeDao;

	public void setAppService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	@Autowired
	public UserInterface userDao;

	public void setAppService(UserInterface userDao) {
		this.userDao = userDao;
	}
	
	private String user_id;
	
	@Autowired
	public AdminDao adminDao;

	public void setAppService(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	@RequestMapping("/critical")
    public ModelAndView viewCriticalTransactions() {
		List<TransactionBean> list = adminDao.retrieveCriticalTransactions();
		Map<String, Double> userBalances = adminDao.retrieveUserBalances(list);
		ModelAndView model=new ModelAndView("AdminCriticalTransactions", "command", new TransactionBean());
		for(TransactionBean transactionBean : list) // setting corresponding user balances
		{
			transactionBean.setBalance(userBalances.get(transactionBean.getFromUid()));
		}
	  	model.addObject("list",list);
		return model;
	}
	
	@RequestMapping(value="/processCriticalTransaction", method=RequestMethod.POST)
    public ModelAndView processCriticalTransaction(@ModelAttribute("AdminCriticalTransactions") TransactionBean transactionBean) {
		
		adminDao.processCriticalTransactions(transactionBean);
    	
    	List<TransactionBean> list1 = adminDao.retrieveCriticalTransactions();
    	Map<String, Double> userBalances = adminDao.retrieveUserBalances(list1);
		ModelAndView model=new ModelAndView("AdminCriticalTransactions", "command", new TransactionBean());
		for(TransactionBean transBean : list1) // setting corresponding user balances
			{
				transBean.setBalance(userBalances.get(transBean.getFromUid()));
			}
			model.addObject("list",list1);
			return model;
	}

	/*@RequestMapping("/retrievelist")
	public ModelAndView showUsers() {
		  	List<UserBean> list = userDao.selectUsers();
		 
		  	ModelAndView model=new ModelAndView("RegistrationSuccessful2");
		  	model.addObject("list",list);
		  	for(UserBean u : list)
		  	{
		  		System.out.println(u.getAddress()+" "+u.getEmail()+" "+u.getFirstname()+" "+u.getLastname());
		  	}
			return model;
	    }*/
	
	 @RequestMapping("/firstPage")
	    public String firstPage() {
	         
		 return "RegistrationSuccessful";
	    }
	 
	 
	 @RequestMapping("/credit")
	    public String credit1(ModelMap model) {
		 	model.addAttribute("iDBean", new IDBean());
	        return "credit";
	    }
	 
	 @RequestMapping(value = "/credit", method = RequestMethod.POST) // credit funds method 2
	    public String credit2(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
		 if(bindingResult.hasErrors()) {
	    	return "credit";
	    	}
	 	else{
		 	String returnString = userDao.creditFunds(iDBean, user_id);
	 		model.addAttribute("message", returnString);
	 		return "UserMessages";
	 		}
	    }
	 
	 @RequestMapping("/debit")
	    public String debit1(ModelMap model) {
		 model.addAttribute("iDBean", new IDBean());
	        return "debit";
	    }
	 
	 @RequestMapping(value = "/debit", method = RequestMethod.POST) // credit funds method 2
	    public String debit2(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
		 	//String user_id = (String)request.getSession().getAttribute("name");
		 	String returnString = "Transaction failed";
		 	if(bindingResult.hasErrors()) {
		    	return "debit";
		    	}
		 	else{
		 		if(Integer.parseInt(iDBean.getEmpid())<2000)
			 		returnString = userDao.debitFunds(iDBean, user_id);
			 	else
			 	{
			 		
			 		userDao.signKeyCritical(user_id);
					
			 		Boolean check=adminDao.verifyCritical(user_id);
			 		if(check)
			 		returnString = userDao.debitFundsCriticalTransac(iDBean, user_id);
			 		else
			 			returnString="Sorry!!The admin has rejected the request";
			 			
			 	}
		 		model.addAttribute("message", returnString);
		 		return "UserMessages";
		 		}
	    }
	
	 @RequestMapping("/transfer")
	    public String trasnfer(ModelMap model) {
		 model.addAttribute("transferBean", new TransferBean());
	        return "transfer"; 
	    }
	 
	 @RequestMapping("/homePagemer") // new chetan modified code
	    public ModelAndView merchantHome() {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 this.user_id = ((UserDetails)principal).getUsername();
		 	double balance = userDao.retrieveBalance(this.user_id);
		 	String account = userDao.retrieveAccountNumber(this.user_id);
		 	ModelAndView model1=new ModelAndView("MerchantHome");
			model1.addObject("balance", balance);
		 	model1.addObject("account", account);
			return model1;
	    }
	 
	 @RequestMapping("/HomePageemp") // new chetan modified code
	    public ModelAndView empH() {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 this.user_id = ((UserDetails)principal).getUsername();
		 
		 ModelAndView model1=new ModelAndView("EmployeeHome");
			return model1;
		 
	    }
	 
	 @RequestMapping("/login1")	 // new chetan modified code
	    public String login1(ModelMap model) {
	        return "Default";
	    }
	 
	 @RequestMapping(value = "/transfer", method = RequestMethod.POST)
	    public String transfer2(@ModelAttribute(value="transferBean") @Valid TransferBean transferBean, BindingResult bindingResult,ModelMap model) {
		 if(bindingResult.hasErrors()) {
		    	return "transfer";
		    	}
		 else
		 	{
			 	String returnString = userDao.transferFunds(transferBean,user_id);
		 		model.addAttribute("message", returnString);
		 		return "UserMessages";
		 	}
	    }
	
	/* @RequestMapping(value = "/login1", method = RequestMethod.POST)
	    public ModelAndView login(@ModelAttribute("login") Login login, ModelMap model,HttpServletRequest request) {
		 	
		 	ModelAndView model1;
		 	String role = userDao.checkLoginDetails(login);
		 	request.getSession().setAttribute("name", login.getUserId());
		 	if(!role.equals(null))
		 	{
		 		if(role.equals("admin"))
		 		{
		 			model1=new ModelAndView("AdminHomePage");
		 			return model1;
		 		}
		 		else if(role.equals("user"))
		 		{
		 			double balance = userDao.retrieveBalance(login.getUserId());
		 			model1=new ModelAndView("homepage");
		 			model1.addObject("balance", balance);
		 			return model1;
		 		}
		 		else
			 	{
			 		return new ModelAndView("login", "command", new Login());
			 	}
		 	}
		 	else
		 	{
		 		return new ModelAndView("login", "command", new Login());
		 	}
	    }*/
	
	 @RequestMapping("/login")
	    public String login(ModelMap model) {
	        return "login";
	    }
	 
	 @RequestMapping("/log")
	    public String log(ModelMap model) {
		 model.addAttribute("error", "Invalid UserId/Password");
	        return "login";
	    }
	 @RequestMapping("/user")
	    public ModelAndView userH() {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 this.user_id = ((UserDetails)principal).getUsername();
			
		 /*
		 double balance = userDao.retrieveBalance(this.user_id);
			model1=new ModelAndView("homepage");
			model1.addObject("balance", balance);
			return model1;
		 */
		 	double balance = userDao.retrieveBalance(this.user_id);
		 	String account = userDao.retrieveAccountNumber(this.user_id);

		 	ModelAndView model1=new ModelAndView("homepage");
		 	model1.addObject("balance", balance);
		 	model1.addObject("account", account);
		 	return model1;
	    }
	 @RequestMapping("/admin")
	    public ModelAndView adminH(HttpServletRequest request) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 this.user_id = ((UserDetails)principal).getUsername();
		 
		 ModelAndView model1=new ModelAndView("AdminHomePage");
			return model1;
	    }
	 
	 @RequestMapping("/homePageAdmin")
	    public ModelAndView homePageAdmin() {
		 ModelAndView model1=new ModelAndView("AdminHomePage");
			return model1;
	    }
	 
	 @RequestMapping("/adminhomepage")
	    public ModelAndView adminhomepage(HttpServletRequest request) {
		 
		 ModelAndView model1=new ModelAndView("AdminHomePage");
			return model1;
	    }
	 
	 
	 @RequestMapping("/403")
	    public ModelAndView error() {
		 ModelAndView model1=new ModelAndView("InvalidLogin");
			return model1;
	    }
	 
	 @RequestMapping("/homepage")
	    public ModelAndView displayHomePage(HttpServletRequest request) {
		 	ModelAndView model = new ModelAndView();
		 	double balance = userDao.retrieveBalance(this.user_id);
		 	
		 	String account = userDao.retrieveAccountNumber(this.user_id);
	 		model=new ModelAndView("homepage");
	 		model.addObject("balance", balance);
	 		model.addObject("account", account);
	 		return model;
	    }
	
	 @RequestMapping("/register")
	    public ModelAndView showContacts() {
	         
	        return new ModelAndView("AddUser", "command", new UserBean());
	    }

	/* @RequestMapping(value = "/addUser", method = RequestMethod.POST)
		public String addStudent(@ModelAttribute("AddUser") UserBean userBean, ModelMap model)
		{
			  model.addAttribute("firstName", userBean.getFirstname());
		      model.addAttribute("lastName", userBean.getLastname());
		      model.addAttribute("address", userBean.getAddress());
		      model.addAttribute("telephone", userBean.getTelephone());
		      model.addAttribute("emails", userBean.getEmail());
		      userDao.addUser(userBean);
		      return "homepage";
		}*/
	 
	 @RequestMapping("/deleteInternalUser")
	    public String deleteInternalUser(ModelMap model) {
	         
		 	model.addAttribute("iDBean", new IDBean());
	        return "AdminDeleteInternalUser";
	    }
	 
	 @RequestMapping(value = "/deleteInternalUser", method = RequestMethod.POST)
	    public String deleteInternalUserWithFields(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
			if(bindingResult.hasErrors()) {
		    	return "AdminDeleteInternalUser";
		    }
			else
		 	{	
				String message = adminDao.deleteUser(iDBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";

		 	}
	    }
	 
	 @RequestMapping("/deleteExternalUser")
	    public String deleteExternalUser(ModelMap model) {
		 	model.addAttribute("iDBean", new IDBean());
	        return "AdminDeleteExternalUser";
	    }
	 
	 @RequestMapping(value = "/deleteExternalUser", method = RequestMethod.POST)
	    public String deleteExternalUserWithFields(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {

			if(bindingResult.hasErrors()) {
		    	return "AdminDeleteExternalUser";
		    }
			else
		 	{	
				String message = adminDao.deleteExternalUser(iDBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";

		 	}
	    }
	 @RequestMapping("/editInternalUser")
	    public String editInternalUser(ModelMap model) {
	        model.addAttribute("iDBean", new IDBean());
	        return "AdmineditInternalUser";
	    }
	 
	 @RequestMapping(value = "/editInternalUser", method = RequestMethod.POST)
	    public String editInternalUserFields(@ModelAttribute(value="iDBean") @Valid IDBean idBean, BindingResult bindingResult,ModelMap model) {
		 if(bindingResult.hasErrors()) {
		    	return "AdmineditInternalUser";
		    }
		 else
		 {	
			 	UserBean uBean = adminDao.editUser(idBean);
			 	if(uBean.getFirstname()==null)
			 		{
			 		  String message = "No user with given id";
			 		  model.addAttribute("message", message);
			 		  return "RegistrationSuccessful2";
			 		}
			 	model.addAttribute("userBean", new UserBean());
			 	model.addAttribute("uBean",uBean);
			 	return "AdminEditInternalUserFields";
		 }
	    }
	 @RequestMapping("/editExternalUser")
	    public String editExternalUser(ModelMap model) {
	        model.addAttribute("iDBean", new IDBean());
	        return "AdmineditExternalUser";
	    }
	 
	 @RequestMapping(value = "/editExternalUser", method = RequestMethod.POST)
	    public String editExternalUserFields(@ModelAttribute(value="iDBean") @Valid IDBean idBean, BindingResult bindingResult,ModelMap model) {
		 if(bindingResult.hasErrors()) {
		    	return "AdmineditExternalUser";
		    }
		 else
		 {	
			 	UserBean uBean = adminDao.editExternalUser(idBean);
			 	if(uBean.getFirstname()==null)
			 		{
			 		 String message = "No user with given id; Or no authorization to edit/view User data";
			 		  model.addAttribute("message", message);
			 		  return "RegistrationSuccessful2";
			 		}
			 	model.addAttribute("userBean", new UserBean());
			 	model.addAttribute("uBean",uBean);
			 	return "AdmineditExternalUserFields";
		 }
	    }
	 
	 
	 @RequestMapping(value = "/editExternalUserF", method = RequestMethod.POST)
	    public String editExternalUserSubmit(@ModelAttribute(value="userBean") @Valid UserBean userBean,BindingResult bindingResult,ModelMap model) {
	        if(bindingResult.hasErrors()) {
		    	return "AdmineditExternalUserFields";
		    }
		 	else
		 	{	String message = adminDao.editExternalUserUpdateFields(userBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";		 	
		 	}
	    }
	 
	 @RequestMapping(value = "/editInternalUserF", method = RequestMethod.POST)
	    public String editInternalUserSubmit(@ModelAttribute(value="userBean") @Valid UserBean userBean,BindingResult bindingResult,ModelMap model) {
	        if(bindingResult.hasErrors()) {
		    	return "AdminEditInternalUserFields";
		    }
		 	else
		 	{	String message = adminDao.editUserUpdateFields(userBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";
		 	}
	    }
	 
	 @RequestMapping(value = "/addExternalUser", method = RequestMethod.GET)
	    public String addExternalUser(ModelMap model) {
		 	model.addAttribute("userBean", new UserBean());
	        return "AdminaddExternalUser";
	    }
	 
	 
	 @RequestMapping(value = "/addExternalUser", method = RequestMethod.POST)
	    public String addExternalUser2(@ModelAttribute(value="userBean") @Valid UserBean userBean, BindingResult bindingResult,ModelMap model) {
		 	if(bindingResult.hasErrors()) {
		    	//logs exception
				logger.error("addExternalUser validation Error");
		 		return "AdminaddExternalUser";
		    }
		 	else
		 	{	String message = adminDao.addExternalUser(userBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";
		 	}
	    }
	 
	 @RequestMapping("/addInternalUser")
	    public String addInternalUser(ModelMap model) {
		 	model.addAttribute("userBean", new UserBean());
	        return "AdminaddInternalUser";
	    }
	 
	 
	 	@RequestMapping(value = "/addInternalUser", method = RequestMethod.POST)
	    public String addInternalUser2(@ModelAttribute(value="userBean") @Valid UserBean userBean, BindingResult bindingResult,ModelMap model) {
	 		if(bindingResult.hasErrors()) {
		    	return "AdminaddInternalUser";
		    }
		 	else
		 	{	String message = adminDao.addUser(userBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful2";
		 	}
	    }
	 	
	 	 @RequestMapping("/viewLoginfo")
		    public ModelAndView viewLoginfo() {
	 		List<IDBean> list = adminDao.retrieveLogs();
			ModelAndView model=new ModelAndView("AdminViewLogs", "command", new TransactionBean());
		  	model.addObject("list",list);
			return model;
		    }
	 	 
	 	@RequestMapping("/verifyUserRequests")
	    public ModelAndView verifyUserRequests() 
	 	{
			List<TransactionBean> list = adminDao.retrieveUserRequests();
			ModelAndView model=new ModelAndView("AdminVerifyUserRequests", "command", new TransactionBean());
		  	model.addObject("list",list);
			return model;
		}
	 	
	 	@RequestMapping("/forgot")
	    public String forgot(ModelMap model) {
	     model.addAttribute("iDBean", new IDBean());
		 return "LoginForgotPassword";
	    }
	 	
	 	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	    public String forgotPassword(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
	 		if(bindingResult.hasErrors()) {
		    	return "LoginForgotPassword";
		    }
		 	else
		 	{	String message = userDao.checkExistingUser(iDBean);
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful";
		 	}
	    }
	 	
		@RequestMapping("/OTPLogin")
	    public String OTPLogin(ModelMap model) {
	     model.addAttribute("otp", new Login());
		 return "OTPLogin";
	    }
	 	
	 	@RequestMapping(value = "/OTPLogin", method = RequestMethod.POST)
	    public String OTPLogin2(@ModelAttribute(value="otp") @Valid Login login, BindingResult bindingResult,ModelMap model) {
	 		if(bindingResult.hasErrors()) {
		    	return "OTPLogin";
		    }
		 	else
		 	{	String message = userDao.loginUsingOTP(login);
		 		if(message.equals("Success"))
		 		{
		 			model.addAttribute("changePassword", new Login());
		 			model.addAttribute("id", login.getUserId());
			 		return "LoginChangePassword";
		 		}
		 		else
		 		{
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful";
		 		}
		 	}
	    }
	 	
		@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	    public String changePassword(@ModelAttribute(value="changePassword") @Valid Login login, BindingResult bindingResult,ModelMap model) {
	 		if(bindingResult.hasErrors()) {
		    	return "OTPLogin";
		    }
		 	else
		 	{	String message = userDao.changePassword(login);
		 		
		 		model.addAttribute("message", message);
		 		return "RegistrationSuccessful";
		 		
		 	}
	    }
		
		@RequestMapping("/EmployeeUserRequests")
	    public String viewuserRequests(ModelMap model) {
			List<TransactionBean> list = employeeDao.retrieveUserRequests(this.user_id);
			//ModelAndView model=new ModelAndView("ViewUserRequests", "command", new TransactionBean());
		  	model.addAttribute("list",list);
		  	model.addAttribute("transactionBean",new TransactionBean());
			return "ViewUserRequests";
		}
		
		@RequestMapping(value = "/sendToAdmin", method = RequestMethod.POST)
	    public String sendToAdmin(@ModelAttribute(value="transactionBean") @Valid TransactionBean transactionBean, BindingResult bindingResult,ModelMap model) {
			if(bindingResult.hasErrors()) {
			    	return "ViewUserRequests";
			    }
			 else
			 {	
				 	String message = employeeDao.sendToAdmin(transactionBean);
				 	model.addAttribute("message",message);
				 	return "EmployeeMessages";
			 }
		}
		
		//adding controller to view statements in user side
		@RequestMapping("/viewstatements")
	    public ModelAndView viewstatements() {
			List<TransactionBean> list = userDao.retrievetransactions(this.user_id);
			ModelAndView model=new ModelAndView("ViewStatements", "command", new TransactionBean());
		  	model.addObject("list",list);
			return model;
		}
		
		//retrieveUserRequestsMerchant
		@RequestMapping("/viewstatementsmerchant")
	    public ModelAndView viewstatementsmerchant() {
			List<TransactionBean> list = employeeDao.retrieveUserRequestsMerchant(this.user_id);
			ModelAndView model=new ModelAndView("ViewStatementsMerchant", "command", new TransactionBean());
		  	model.addObject("list",list);
			return model;
		}
		
		 @RequestMapping("/editprofile")
		    public ModelAndView editprofile() {
		       String message=  employeeDao.RequestModify(this.user_id);
		       
		       ModelAndView model1;
		       if(message.equals("false"))
		    	    model1 = new ModelAndView("editproffalse", "command", new TransactionBean());
				    
		       else	    	
		        model1 = new ModelAndView("editprof", "command", new TransactionBean());
			 	
		       
		       model1.addObject("message", message);
		 		return model1; 
			// return new ModelAndView("editprof", "command", new TransactionBean());
		    }
		 
		 @RequestMapping(value="/deletetransaction")
		    public String EmployeeHandleDeleteRequests(@RequestParam String id, ModelMap model,HttpServletRequest request) {
			 String check=employeeDao.EmployeeDeleteCheck(id,this.user_id);
			 	if(check.equals("true"))
			 		{
			 			 String del=employeeDao.deleteUserTransaction(id);
				 		 model.addAttribute("message", del);
				 		 return "EmployeeMessages";
			 		}
			 	else
			 	{
			 		 model.addAttribute("message", "User not found/No proper autherization from user to delete/Already handled delete transaction of this user, "+id+" there are no more requests");
			 		 return "EmployeeMessages";
			 	}
			  }
		 @RequestMapping(value="/createtransaction")
		    public String createtransaction(@RequestParam String id, ModelMap model,HttpServletRequest request) {
			 String check=employeeDao.EmployeeCreateCheck(id,this.user_id);
			 	if(check.equals("true"))
			 		{
			 		 String create=employeeDao.createUserTransaction(id);
			 		 model.addAttribute("message", create);
			 		 return "EmployeeMessages";
			 		}
			 	else
			 		{
			 		 model.addAttribute("message", "User not found/No proper autherization from user/Already handled create transaction of this user, "+id+" there are no more requests");
			 		 return "EmployeeMessages";
			 		}
			 }
		 @RequestMapping(value="/modifytransaction")
		    public String modifytransaction(@RequestParam String id, ModelMap model,HttpServletRequest request) {
			 String check=employeeDao.EmployeeModifyCheck(id,this.user_id);
			 	if(check.equals("true"))
			 		{
					 String modify=employeeDao.modifyUserTransaction(id);
			 		 model.addAttribute("message", modify);
			 		 return "EmployeeMessages";
			 		}
			 	else
			 		{
			 		 model.addAttribute("message", "User not found/No proper autherization from user to modify/Already handled modify transaction of this user, "+id+" there are no more requests");
			 		 return "EmployeeMessages";
			 		}
			} 
		 
		// createtransaction
		 @RequestMapping("/deletetransactionrequest")
		    public ModelAndView deletetransactionrequest() {
			 String type="delete";
			 System.out.println("user id is"+this.user_id);
			 String message=  employeeDao.RequestModifyTransaction(this.user_id,type);
		       ModelAndView model1;
		       if(message.equals("true"))//edittransuccess
		    	   model1 = new ModelAndView("edittransuccess", "command", new TransactionBean());
		       
		       else
		    	    model1 = new ModelAndView("editproffalse", "command", new TransactionBean());
		       
		       
			 	model1.addObject("message", message);
		 		return model1; 
			// return new ModelAndView("editprof", "command", new TransactionBean());
		    }
		 
		 @RequestMapping("/createtransactionrequest")
		    public String createtransaction(ModelMap model) {
		 		model.addAttribute("iDBean", new IDBean());
		        return "CreateTransactionInput";
		    }
		 
		 @RequestMapping(value = "/createtransactionrequest", method = RequestMethod.POST) // credit funds method 2
		    public String CreateTransactionSubmit(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
			 if(bindingResult.hasErrors()) {
			    	return "CreateTransactionInput";
			    	}
			 	else{
					 Double amount=Double.parseDouble(iDBean.getEmpid());
					 String type="create";
				     String message=  employeeDao.RequestModifyTransaction1(this.user_id,type,amount);
				       if(message.equals("false"))
				       		{
				    	   		message ="Sorry, you have already created a transaction which is not yet handled.";
				    	   		model.addAttribute("message", message);
			    	   			return "UserMessages";
				       		}
				       else
				    	    {
				    	   		model.addAttribute("message", message);
				    	   		return "UserMessages";
				    	    }			 		}
			 }
		
		 @RequestMapping("/modifytransactionrequest")
		  public String modifytransactionrequest(ModelMap model) {
			 model.addAttribute("transferBean", new TransferBean());
		        return "ModifyTransactionInput"; 
		    }
		 
		 @RequestMapping(value = "/modifytransactionrequest", method = RequestMethod.POST) // credit funds method 2
		    public String ModifyTransactionSubmit(@ModelAttribute(value="transferBean") @Valid TransferBean transferBean, BindingResult bindingResult,ModelMap model) {
			 
			 if(bindingResult.hasErrors()) {
			    	return "ModifyTransactionInput";
			    	}
			 else
			 {
				 String type="modify";
				 Double amount=Double.parseDouble(transferBean.getBalance());
				 int txnid=Integer.parseInt(transferBean.getToAccount());
				 String message =  employeeDao.ModifyTransactionTxnCheck(this.user_id,txnid);
				 if(message.equals("true"))
				 {
				 String message1 =  employeeDao.RequestModifyTransaction2(this.user_id,type,amount,txnid);
				 if(message1.equals("false"))
				 	{
				 		model.addAttribute("message", "Already a request to modify is placed, Which is not yet handled");
				 		return "UserMessages";
				 	}
				 else
				 {
				 		model.addAttribute("message", message1);
				 		return "UserMessages";
				 }  
			 }
				 else
				 {
				 		model.addAttribute("message", "Transaction doesn't belong to this user id");
				 		return "UserMessages";
				 }
				
			}
		 }

		 @RequestMapping("/initiatepayment")	// new chetan modified code
		    public String initiatepayment(ModelMap model) { 
			 model.addAttribute("transferBean", new TransferBean());
			 return "MerchantPaymentRequestInput"; 
		    }

		//adminmerchantpayment
		 	@RequestMapping(value = "/PaymentRequestSubmit", method = RequestMethod.POST)// new chetan modified code
		    public String PaymentRequestSubmit(@ModelAttribute(value="transferBean") @Valid TransferBean transferBean, BindingResult bindingResult,ModelMap model) {
			 if(bindingResult.hasErrors()) {
			    	//logs exception
					logger.error("merchant PaymentRequestSubmit validation Error");
			 		return "MerchantPaymentRequestInput";
			    }
			 	else
			 	{	Double amount=Double.parseDouble(transferBean.getBalance());
				 	String userid=transferBean.getToAccount();
				 	String message = employeeDao.submitpaymentrequest(userid, this.user_id, amount);
			 		model.addAttribute("message", message);
			 		logger.info("merchant PaymentRequestSubmit");
			 		return "UserMessages";
			 	}
		    }
		 
		 @RequestMapping("/adminmerchantpayment")
		    public ModelAndView adminmerchantpayment() {
			 
			 String mer=employeeDao.merchantprocesspayment(this.user_id,26);
			 if(mer.equals("success"))
			 return new ModelAndView("editprof", "command", new TransactionBean());
			 
			 else
				 return new ModelAndView("editproffalse", "command", new TransactionBean());
				
		 }
		 //ModifyTransactionSubmit
		 
		 @RequestMapping("/edittransaction")
		    public ModelAndView edittransaction() {
		       /*String message=  employeeDao.RequestModifyTransaction(this.user_id);
		       ModelAndView model1;
		       if(message.equals("false"))
		    	   model1 = new ModelAndView("editproffalse", "command", new TransactionBean());
		       
		       else
		    	    model1 = new ModelAndView("edittran", "command", new TransactionBean());
		       
		       
			 	model1.addObject("message", message);
		 		return model1;*/ 
			 return new ModelAndView("edittran", "command", new TransactionBean());
		    }
		
		 
		
		 @RequestMapping("/merchant")
		    public ModelAndView merchantH() {
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 this.user_id = ((UserDetails)principal).getUsername();
			 	double balance = userDao.retrieveBalance(this.user_id);
			 	String account = userDao.retrieveAccountNumber(this.user_id);


			 	ModelAndView model1=new ModelAndView("MerchantHome");
			 	model1.addObject("balance", balance);
			 	model1.addObject("account", account);
				return model1;
			 
		    }
		 @RequestMapping("/employee")
		    public ModelAndView employeeH() {
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 
			 this.user_id = ((UserDetails)principal).getUsername();
			 ModelAndView model1=new ModelAndView("EmployeeHome");
				return model1;
		    }
		 
		 @RequestMapping("femp")
		    public ModelAndView employeeH2() {
			 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 
			 this.user_id = ((UserDetails)principal).getUsername();
			 ModelAndView model1=new ModelAndView("EmployeeHome");
				return model1;
		    }
		 
		 //EmployeeHandleRequests
		 @RequestMapping("/EmployeeHandleRequests")
		    public String EmployeeHandleRequests(ModelMap model) {
		        model.addAttribute("iDBean",new IDBean());
		        return "EmployeeHandle";
		    }
		 
		 //EmployeeAuthorize
		 @RequestMapping(value = "/EmployeeHandleRequests", method = RequestMethod.POST) // credit funds method 2
		    public String EmployeeAuthorize(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
			 if(bindingResult.hasErrors()) 
			 	{
			    	return "EmployeeHandle";
			    }
			 else
			 	{	String userid=iDBean.getEmpid().toString();
			 		System.out.println("userid to edit is"+userid);
			 		String check=employeeDao.EmployeeProfileCheck(userid,this.user_id);
			 		if(check.equals("true"))
			 		{
			 			UserBean uBean = employeeDao.editexternalUser(userid);
			 			if(uBean.getFirstname()==null)
			 				{
						 		model.addAttribute("message", "No user with given id; Please Enter valid user id");
						 		return "EmployeeMessages";
			 				}
					 	model.addAttribute("userBean", new UserBean());
					 	model.addAttribute("uBean",uBean);
			 			return "EmployeeEditExternalUser";
			 		}
			 	else
			 		{
			 		model.addAttribute("message", "Already edited this profile/No valid autherization from user/ User not found");
			 		return "EmployeeMessages";
			 		}
			 	}
		    }
		 // Employee edit profile submit
		 @RequestMapping(value="/editExternalUserSubmit", method=RequestMethod.POST)
		    public String editExternalUserSubmitF(@ModelAttribute(value="userBean") @Valid UserBean userBean,BindingResult bindingResult,ModelMap model) {
			 if(bindingResult.hasErrors()) {
			    	return "EmployeeEditExternalUser";
			    }
			 else{
				 	String message = employeeDao.editExternalUserUpdateFields(userBean);
				 	model.addAttribute("message", message);
				 	return "EmployeeMessages";
		        }
		    }
		 
		 //EmployeeHandleDeleteRequests
		 @RequestMapping("/EmployeeHandleTransactionRequests")
		    public String EmployeeHandleTransactionRequests(ModelMap model) {
			 model.addAttribute("iDBean", new IDBean());
			 return "EmployeeHandleTransaction";

		 }
		 
		 //EmployeeAuthorizeTransaction
		 @RequestMapping(value = "/EmployeeAuthorizeTransaction", method = RequestMethod.POST) // credit funds method 2
		    public String EmployeeAuthorizeTransaction(@ModelAttribute(value="iDBean") @Valid IDBean iDBean, BindingResult bindingResult,ModelMap model) {
			 if(bindingResult.hasErrors()) {
			    	//logs exception
					logger.error("EmployeeHandleTransaction validation Error");
			 		return "EmployeeHandleTransaction";
			    }
			 	else
			 	{	
			 		model.addAttribute("id", iDBean.getEmpid());
				 	return "EmployeeEditExternalUserTransaction";
			 	}
		    }
		 
		 
			//ADDED SWETHA user clicking on accept for merchant requests
			@RequestMapping("/verifymerchantrequest")
		    public ModelAndView verifymerchantrequest() {
				List<TransactionBean> list = userDao.retrievepaymenttransactions(user_id);
				System.out.println("hi......user id got...."+this.user_id);
				System.out.println(list);
				Map<String, Double> userBalances = userDao.retrieveUserBalances(list);
				ModelAndView model=new ModelAndView("UserProcessMerchantRequest", "command", new TransactionBean());
				for(TransactionBean transactionBean : list) // setting corresponding user balances
				{
					transactionBean.setBalance(userBalances.get(transactionBean.getFromUid()));
				}
				System.out.println("created model!!!!!!");
			  	model.addObject("list",list);
			  	System.out.println("added model !!!!!");
				return model;
			}
			
		 
		//ADDED SWETHA user forwards the process back to merchant MOFIFIED FOR PKI
			//userforwardtomerchant
			@RequestMapping(value="/userforwardtomerchant", method=RequestMethod.POST)
		    public ModelAndView userforwardtomerchant(@ModelAttribute("UserProcessMerchantRequest") TransactionBean transactionBean) {
				//ADDED FOR PKI
				userDao.processPaymentTransactions(transactionBean,this.user_id);
				//CMMENTED FOR PKI
				//userDao.processPaymentTransactions(transactionBean);
		    	
		    	List<TransactionBean> list1 = userDao.retrievepaymenttransactions(user_id);
		    	Map<String, Double> userBalances = userDao.retrieveUserBalances(list1);
				ModelAndView model=new ModelAndView("UserProcessMerchantRequest", "command", new TransactionBean());
				for(TransactionBean transBean : list1) // setting corresponding user balances
					{
						transBean.setBalance(userBalances.get(transBean.getFromUid()));
					}
					model.addObject("list",list1);
					return model;
			}
			
			
			//ADDED SWETHA for the merchant to view the status and forward it to the bank
			@RequestMapping("/viewstatus")
		    public ModelAndView viewstatus(@ModelAttribute("MerchantForward") TransactionBean transactionBean) {
				
				//adminDao.processCriticalTransactions(transactionBean);
		    	
		    	List<TransactionBean> list1 = userDao.retrieveAcceptedPayments(this.user_id);
		    	Map<String, Double> userBalances = userDao.retrieveUserBalances(list1);
				ModelAndView model=new ModelAndView("MerchantForward", "command", new TransactionBean());
				for(TransactionBean transBean : list1) // setting corresponding user balances
					{
						transBean.setBalance(userBalances.get(transBean.getFromUid()));
					}
					model.addObject("list",list1);
					return model;
			}
			
			@RequestMapping("/merchantforwardtobank")
		    public ModelAndView merchantforwardtobank(@ModelAttribute("MerchantForward") TransactionBean transactionBean) {
				
		    	userDao.forwardaccepttobank(transactionBean,this.user_id);
		    	List<TransactionBean> list1 = userDao.retrieveAcceptedPayments(this.user_id);
		    	Map<String, Double> userBalances = userDao.retrieveUserBalances(list1);
				ModelAndView model=new ModelAndView("MerchantForward", "command", new TransactionBean());
				for(TransactionBean transBean : list1) // setting corresponding user balances
					{
						transBean.setBalance(userBalances.get(transBean.getFromUid()));
					}
					model.addObject("list",list1);
					return model;
			}
			
			
			@RequestMapping(value="/processPaymentTransactionsByAdmin", method=RequestMethod.POST)
		    public ModelAndView processPaymentTransactionsByAdmin(@ModelAttribute("UserPaymentSuccess") TransactionBean transactionBean) {
				
				//adminDao.processPaymentTransactions(transactionBean,this.user_id);
		    	
		    	List<TransactionBean> list1 = userDao.retrievepaymenttransactions(user_id);
		    	Map<String, Double> userBalances = userDao.retrieveUserBalances(list1);
				ModelAndView model=new ModelAndView("UserProcessMerchantRequest", "command", new TransactionBean());
				for(TransactionBean transBean : list1) // setting corresponding user balances
					{
						transBean.setBalance(userBalances.get(transBean.getFromUid()));
					}
					model.addObject("list",list1);
					return model;
			}
			
			@RequestMapping("/adminverifymerchantrequest")
		    public ModelAndView adminverifymerchantrequest() {
				List<TransactionBean> list = adminDao.retrievepaymenttransactions(user_id);
				System.out.println("hi......user id got...."+this.user_id);
				System.out.println(list);
				Map<String, Double> userBalances = userDao.retrieveUserBalances(list);
				ModelAndView model=new ModelAndView("AdminProcessMerchantRequest", "command", new TransactionBean());
				for(TransactionBean transactionBean : list) // setting corresponding user balances
				{
					transactionBean.setBalance(userBalances.get(transactionBean.getFromUid()));
				}
				System.out.println("created model!!!!!!");
			  	model.addObject("list",list);
			  	System.out.println("added model !!!!!");
				return model;
			}
			
			@RequestMapping(value="/adminperformpayment", method=RequestMethod.POST)
		    public ModelAndView adminperformpayment(@ModelAttribute("AdminProcessMerchantRequest") TransactionBean transactionBean) {
				
				adminDao.processPaymentTransactions(transactionBean, this.user_id);
				List<TransactionBean> list = adminDao.retrievepaymenttransactions(user_id);//.retrievemerchanttransactions();
		    	//List<TransactionBean> list1 = userDao.retrievepaymenttransactions(user_id);
		    	Map<String, Double> userBalances = userDao.retrieveUserBalances(list);
				ModelAndView model=new ModelAndView("AdminProcessMerchantRequest", "command", new TransactionBean());
				for(TransactionBean transBean : list) // setting corresponding user balances
					{
						transBean.setBalance(userBalances.get(transBean.getFromUid()));
					}
					model.addObject("list",list);
					return model;
			}
			
			
			
			
}