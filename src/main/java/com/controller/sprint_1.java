package com.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model.Addbook;
import com.model.booking;
import com.model.register_GS;
import com.service.Deletebookdb;
import com.service.addbookavailability;
import com.service.addbookdb;
import com.service.addbooktocart;
import com.service.addlibrarian;
import com.service.addtoqueue;
import com.service.check_login;
import com.service.check_loginCredential;
import com.service.deletebookavailability;
import com.service.hgetcopycount;
import com.service.hststus;
import com.service.hupdatecopycount;
import com.service.hupdatestatus;
import com.service.onequeueitemcount;
import com.service.register_Q;
import com.service.removecart;
import com.service.searchanja;
import com.service.searchbook;
import com.service.updateavailability;
import com.service.updatebookdb;
import com.service.viewCart;
import com.service.viewlibrarians;
import com.service.viewqueue;

@Controller
public class sprint_1{
		
		@RequestMapping("/register")
		
		public ModelAndView insert(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
		{
			String btn_name = request.getParameter("st");
			ModelAndView mv = new ModelAndView();
			
			if(btn_name.equals("useradd")) {
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			String birthday = request.getParameter("birthday");
			String nic = request.getParameter("nic");
			String mail = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			register_GS obj = new register_GS();
			
			obj.setName(name);
			obj.setAddress(address);
			obj.setPhone(phone);
			obj.setBirthday(birthday);
			obj.setNic(nic);
			obj.setEmail(mail);
			obj.setUsername(username);
			obj.setPassword(password);
			
			register_Q us = new register_Q();
			us.adduser(obj);
			
			System.out.println(name);
			mv.setViewName("login.jsp");
			
			}
			else if(btn_name.equals("libadd")) {
				String name = request.getParameter("name");
				String address = request.getParameter("address");
				String phone = request.getParameter("phone");
				String birthday = request.getParameter("birthday");
				String nic = request.getParameter("nic");
				String mail = request.getParameter("email");
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				register_GS obj = new register_GS();
				
				obj.setName(name);
				obj.setAddress(address);
				obj.setPhone(phone);
				obj.setBirthday(birthday);
				obj.setNic(nic);
				obj.setEmail(mail);
				obj.setUsername(username);
				obj.setPassword(password);
				
				addlibrarian us = new addlibrarian();
				us.addlibrarian(obj);
				
				System.out.println(name);
				viewlibrarians lib = new viewlibrarians();
				ArrayList list = lib.viewlib(obj);
				
				
				mv.setViewName("librarians.jsp");
				mv.addObject("result",list);
				
				}
			return mv;
			
		}
		@RequestMapping("/login")
		public ModelAndView login(HttpServletRequest request,HttpServletResponse respond)throws ClassNotFoundException, SQLException
		{
			String uname = request.getParameter("username");
			String password = request.getParameter("password");
			
			ModelAndView mv = new ModelAndView();
			
			register_GS obj2 = new register_GS();
			
			obj2.setUsername(uname);
			obj2.setPassword(password);
			
			check_loginCredential lc = new check_loginCredential();
			boolean result = lc.loginUserService(obj2);
			System.out.println(result);
			//-------------------------
			//Get if user or admin
			register_GS obj3 = new register_GS();
			
			obj3.setUsername(uname);
			obj3.setPassword(password);
		
			check_login cl  = new check_login();
			String check = cl.check_login(obj3);
			
			//----------------------------
			
			if(result == true && check.equals("Admin"))
			{
				//recordLoginTime LT = new recordLoginTime();
				//LT.logingRecord(uname);
				
				HttpSession session = request.getSession(true);  
				
				
				 
		        session.setAttribute("currentSessionUser",obj3); 
		       
		        
		   
		        //Pass daily attendance!!!!
		       // getEmployeeService ES= new getEmployeeService();
				//ArrayList search_list = ES.searchEmployeeNames();

				//search4Service US = new search4Service();//Create Search4 service object
				//ArrayList search_list2 = US.getDailyStatus();
				
				mv.setViewName("first_librarian.jsp");//logged-in Admin page  
				
			}
			else if(result == true && check.equals("User"))
			{
				//recordLoginTime LT = new recordLoginTime();
				//LT.logingRecord(uname); 
		        
				HttpSession session = request.getSession(true);  
		        session.setAttribute("currentSessionUser",obj3); 
		       
				mv.setViewName("first_member.jsp");//logged-in User page    
			}
			else
			{
				String erorr = "Eneter Correct Credentials!!";
				mv.addObject("result",erorr);
				mv.setViewName("LoginError.jsp");
			}
			
			return mv;
		}
		
@RequestMapping("/addbook")
		
		public ModelAndView addbook(HttpServletRequest request,HttpServletResponse respond)
		{
			String bookno = request.getParameter("bookno");
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			int copyno = Integer.parseInt(request.getParameter("copyno"));
			String description = request.getParameter("description");
			
			Addbook objb = new Addbook();
			
			objb.setBookId(bookno);
			objb.setBookTitle(title);
			objb.setAuthor(author);
			objb.setCopy_No(copyno);
			objb.setDescription(description);
			
			addbookdb bk = new addbookdb();
			bk.addbookdb(objb);
			
			addbookavailability bka = new addbookavailability();
			bka.addbookdb(objb);
			
			System.out.println(bookno);
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("first_librarian.jsp");
			
			return mv;
		}

@RequestMapping("/search")

public ModelAndView search(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{
	String search = request.getParameter("search");
	
	register_GS objb = new register_GS();
	
	objb.setSearch(search);
	
	searchbook se = new searchbook();
	ArrayList list = se.searchbook(objb);
	
	System.out.println(search);
	
	ModelAndView mv = new ModelAndView();
	mv.setViewName("viewsearch.jsp");
	mv.addObject("result",list);
	
	return mv;
}

@RequestMapping("/add_cart")

public ModelAndView add_cart(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{
	String btn_name = request.getParameter("st");
	String title = request.getParameter("title");
	//LocalDate lblOrderDate = java.time.LocalDate.now();
	String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MMMM yyyy"));
	
	System.out.println("Date :"+date);
	ModelAndView mv = new ModelAndView();
	
	if(btn_name.equals("profile"))
	{
		register_GS objb = new register_GS();
		
		objb.setSearch(title);
		
		searchbook se = new searchbook();
		ArrayList list = se.searchbook(objb);
		
		System.out.println(title);
		mv.setViewName("bookprofile_user.jsp");
		mv.addObject("result",list);
	}
	else if(btn_name.equals("addtocart"))
	{
		String id = request.getParameter("id");
		
		

		//System.out.println(username);
		
		Addbook add = new Addbook();
		HttpSession session = request.getSession();
		register_GS obj = (register_GS) session.getAttribute("currentSessionUser");
		String username = obj.getUsername();
		System.out.println(username);
		//HttpSession session = request.getSession();
		//String username = (String) session.getAttribute("currentSessionUser");
		// request.getSession().setAttribute("currentSessionUser", add);
		add.setUsernam(username);
		add.setBookId(id);
		
		addbooktocart cart = new addbooktocart();
		cart.addbookcart(add);
		
		booking book = new booking();
		book.setUsername(username);
		
		viewCart view = new viewCart();
		ArrayList list = view.viewcart(book);
		mv.setViewName("cart.jsp");
		mv.addObject("result",list);
	}
	
	
	return mv;
}

@RequestMapping("/viewupdate_book")

public ModelAndView viewupdate_book(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{
	
	String btn_name = request.getParameter("st");
String bookno = request.getParameter("bookno");
ModelAndView mv = new ModelAndView();
Addbook objb = new Addbook();
	
	objb.setBookId(bookno);
	if(btn_name.equals("update"))
	{
	
	searchanja se = new searchanja();
	ArrayList list = se.searchbook(objb);
	
	System.out.println(bookno);
	
	
	mv.setViewName("updatebook.jsp");
	mv.addObject("result",list);
	}
	else if(btn_name.equals("delete"))
	{
		deletebookavailability de = new deletebookavailability();
		de.Deletebookdb(objb);
		Deletebookdb del = new Deletebookdb();
		del.Deletebookdb(objb);
		
		mv.setViewName("first_librarian.jsp");
	}
	
	return mv;
}

@RequestMapping("/search1")

public ModelAndView search1(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{
	String search = request.getParameter("search");
	
	register_GS objb = new register_GS();
	
	objb.setSearch(search);
	
	searchbook se = new searchbook();
	ArrayList list = se.searchbook(objb);
	
	System.out.println(search);
	
	ModelAndView mv = new ModelAndView();
	mv.setViewName("viewsearch_librarian.jsp");
	mv.addObject("result",list);
	
	return mv;
}

@RequestMapping("/updatebook")

public ModelAndView updatebook(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{	ModelAndView mv = new ModelAndView();
	String btn_name = request.getParameter("st");
	String bookno = request.getParameter("bookno");
	int copyno = Integer.parseInt(request.getParameter("copies"));
	int addcopy = Integer.parseInt(request.getParameter("newcopies"));
	
	Addbook objb = new Addbook();
	
	objb.setBookId(bookno);
	objb.setCopy_No(copyno);
	objb.setAddcopy(addcopy);
	
System.out.println(addcopy);
	
	System.out.println(bookno);
	if(btn_name.equals("update"))
	{
	updatebookdb se = new updatebookdb();
	se.updatebookdb(objb);
	
	updateavailability se2 = new updateavailability();
	se2.updatebookdb(objb);
	
	searchanja se1 = new searchanja();
	ArrayList list = se1.searchbook(objb);
	
	System.out.println(bookno);
	
	mv.setViewName("viewbook_librarian.jsp");
	mv.addObject("result",list);
	}
	
	return mv;
	
}
@RequestMapping("/cart")

public ModelAndView add_queue(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
{
	String btn_name = request.getParameter("st");
	String title = request.getParameter("title");
	//LocalDate lblOrderDate = java.time.LocalDate.now();
	String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MMMM yyyy"));
	
	int cartid = Integer.parseInt(request.getParameter("id"));
	
	booking add = new booking();
	HttpSession session = request.getSession();
	register_GS obj = (register_GS) session.getAttribute("currentSessionUser");
	String username = obj.getUsername();
	System.out.println(username);
	//HttpSession session = request.getSession();
	//String username = (String) session.getAttribute("currentSessionUser");
	// request.getSession().setAttribute("currentSessionUser", add);
	add.setUsername(username);
	add.setCartitemid(cartid);
	
	System.out.println("Date :"+date);
	ModelAndView mv = new ModelAndView();
	booking book = new booking();
	
	String bookid = request.getParameter("bookid");
	
	book.setBookid(bookid);
	book.setSystemdate(date);
	book.setUsername(username);
	book.setCartitemid(cartid);
	
	System.out.println(bookid);
	System.out.println(date);
	System.out.println(username);
	System.out.println(cartid);
	
	if(btn_name.equals("remove"))
	{
		
		removecart rem = new removecart();
		rem.removecart(add);
		
		viewCart view = new viewCart();
		ArrayList list = view.viewcart(add);
		mv.setViewName("cart.jsp");
		mv.addObject("result",list);
	}
	else if(btn_name.equals("addqueue"))
	{
		
		
		//onequeueitemcount coun = new onequeueitemcount();
		//int count = coun.ueuecount(book);
		
		//System.out.println("count"+count);
		
		//if(count < 3) {
			addtoqueue addqueue = new addtoqueue();
			addqueue.addqueue(book);
			
			hgetcopycount copy = new hgetcopycount();
			int avaliablecopy = copy.getcopy(book);
			book.setCopycount(avaliablecopy);
			
			hupdatecopycount update = new hupdatecopycount();
			update.updatecopycount(book);
			
			hststus statu = new hststus();
			String status = statu.status(book);
			
			book.setStatus(status);
			hupdatestatus ustat = new hupdatestatus();
			ustat.updatestatus(book);
			
			System.out.println(avaliablecopy);
			System.out.println(status);
			
			removecart rem = new removecart();
			rem.removecart(book);
			
			viewCart view = new viewCart();
			ArrayList list = view.viewcart(book);
			mv.setViewName("cart.jsp");
			mv.addObject("result",list);
			
			
		}
	else if(btn_name.equals("queue"))
	{
	viewqueue view = new viewqueue();
	ArrayList list = view.viewqueue(book);
	mv.setViewName("queue.jsp");
	mv.addObject("result",list);
	}	
	
	return mv;
}



}
