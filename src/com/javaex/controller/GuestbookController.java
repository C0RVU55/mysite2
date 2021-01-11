package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("방명록");
		
		String action = request.getParameter("action");
		
		if ("addList".equals(action)) {
			
			GuestDao gDao = new GuestDao();
			List<GuestVo> guestList = new ArrayList<GuestVo>();
			guestList = gDao.getList();
			
			//어트리뷰트
			request.setAttribute("gList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)) {
			//System.out.println("방명록 등록");
			
			//파라미터
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//dao
			GuestVo gVo = new GuestVo(name, password, content);
			GuestDao gDao = new GuestDao();
			gDao.contentAdd(gVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/gbc?action=addList");
			
		} else if ("dform".equals(action)) {
			
			//파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if ("delete".equals(action)) {
			
			// 파라미터 받기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");

			// 조건문 + contentDelete()
			GuestDao gDao = new GuestDao();
			int count = gDao.contentDelete(no, password);

			if (count == 1) {
				// redirect
				WebUtil.redirect(request, response, "/mysite2/gbc?action=addList");

			} else if (count == 0) {
				// redirect
				WebUtil.redirect(request, response, "/mysite2/gbc?action=addList");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
