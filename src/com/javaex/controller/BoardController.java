package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		//리스트(로그인상태에서만 삭제, 글쓰기, 수정 보임) / 등록 / 삭제(리스트 재요청) / 수정(로그인만 가능) / 보기(글수정버튼, 조회수+)
		
		if("list".equals(action)) {
			//System.out.println("리스트");
			
			List<BoardVo> bList = new ArrayList<BoardVo>();
			BoardDao bDao = new BoardDao();
			bList = bDao.getList();
			
			//어트리뷰트
			request.setAttribute("bList", bList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		} else if ("wform".equals(action)) {
			System.out.println("글쓰기폼");
			
			//로그인 세션 받는 거 추가
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인 확인(없으면 아무것도 안 뜸)
			int no = authUser.getNo();
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if ("write".equals(action)) {
			System.out.println("글쓰기");
			
			//파라미터
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int hit = 0;
			
			//세션에서 유저 no 받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo();
			
			BoardVo bVo = new BoardVo(title, content, hit, userNo);
			BoardDao bDao = new BoardDao();
			bDao.write(bVo);			
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/bc?action=list");
			
		}
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
