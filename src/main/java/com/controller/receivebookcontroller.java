package com.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;
import com.model.booking;
import com.service.onequeueitemcount;
import com.service.viewCart;

@Controller
public class receivebookcontroller {
	
		@RequestMapping("/viewcart")
		public ModelAndView viewcart(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
		{
			ModelAndView mv = new ModelAndView();
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			
			String buttonvalue = request.getParameter("button");
			
			System.out.println(username);
			
			if(buttonvalue.equals("viewcart")) {
				
				booking book = new booking();
				book.setUsername(username);
				
				viewCart cart = new viewCart();
				cart.viewcart(book);
				
				mv.setViewName("cart.jsp");
				
			}
			return mv;
			
			
		}
		
		@RequestMapping("/handlecart")
		public ModelAndView handlecart(HttpServletRequest request,HttpServletResponse respond) throws ClassNotFoundException, SQLException
		{
			ModelAndView mv = new ModelAndView();
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			
			String buttonvalue = request.getParameter("button");
			
			System.out.println(username);
			
			if(buttonvalue.equals("addqueue")) {
				
				booking book2 = new booking();
				book2.setUsername(username);
				com.service.onequeueitemcount onequeueitemcount = new onequeueitemcount();
				int count = onequeueitemcount.ueuecount(book2);
				
				if(count < 3) {
					//can add book
					
					
				}
				else {
					mv.setViewName("addbookqueueError.jsp");
				}
				
				
				
			}
			else if(buttonvalue.equals("removecart")) {
				
				int cartitemid = Integer.parseInt(request.getParameter("cartid"));
				
				booking book1 = new booking();
				book1.setCartitemid(cartitemid);
				
				
				book1.setUsername(username);
				
				viewCart cart = new viewCart();
				cart.viewcart(book1);
				
				mv.setViewName("cart.jsp");
				
				
				
			}
			return mv;
			
			
		}

}
