package com.enjoy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enjoy.service.OrderService;
import com.enjoy.service.UserService;
//localhost:8080/mvc/index
@Controller
public class IndexController {
	//private static final Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String getDetials(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String userView = userService.getDetial(id);
		String orderView = orderService.getDetail(id);
		
		request.setAttribute("userView", userView);
		request.setAttribute("orderView", orderView);
		//logger.info(userView);
		return "index";		
	}

}
