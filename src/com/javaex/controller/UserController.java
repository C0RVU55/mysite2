package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action="+action);
		
		if ("joinForm".equals(action)) {
			System.out.println("회원가입폼");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(action)) {
			System.out.println("회원가입");
			
			//dao --> insert() 저장 --> http://localhost:8088/mysite2/user?uid=ldh&pw=1111&uname=이다현&gender=F&action=join	
			//	파라미터값 꺼내기
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");			
			
			//	vo로 묶기
			UserVo uVo = new UserVo(id, password, name, gender);
			System.out.println(uVo.toString());
			
			//	dao클래스 insert(UserVo)함수 사용 --> 저장 --> 회원가입
			UserDao uDao = new UserDao();
			uDao.insert(uVo);
			
			//포워드 --> joinOk.jsp (받은 파라미터를 db에 저장하고 회원가입완료 페이지만 띄우면 됨)
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if ("loginForm".equals(action)) {
			System.out.println("로그인 폼");
			
			//포워드 --> loginForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} 

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
