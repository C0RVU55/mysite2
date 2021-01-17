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
			//System.out.println("글쓰기폼");
			
			//로그인 확인(없으면 오류)
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if ("write".equals(action)) {
			//System.out.println("글쓰기");
			
			//파라미터
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int hit = 0;
			
			//세션에서 유저 no 받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo();
			
			//vo --> dao
			BoardVo bVo = new BoardVo(title, content, hit, userNo);
			BoardDao bDao = new BoardDao();
			bDao.write(bVo);			
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/bc?action=list");
			
		} else if ("read".equals(action)) {
			//System.out.println("보기");
			
			//파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			//dao --> 조회수 증가시킨 다음 게시글 정보 가져오기
			BoardDao bDao = new BoardDao();
			
			bDao.hitPlus(no);
			BoardVo bVo = bDao.read(no);
			
			//어트리뷰트
			request.setAttribute("bVo", bVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if ("delete".equals(action)) {
			//System.out.println("삭제");
			
			//파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			//dao
			BoardDao bDao = new BoardDao();
			bDao.delete(no);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/bc?action=list");
			
		} else if ("mform".equals(action)) {
			//System.out.println("수정폼");
			
			//로그인 확인(없으면 오류)
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo();
			
			//파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			BoardVo bVo = bDao.read(no);
			
			//어트리뷰트
			request.setAttribute("bVo", bVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		} else if ("modify".equals(action)) {
			//System.out.println("수정");
			
			//파라미터 4개값 받음
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//vo --> dao
			BoardVo bVo = new BoardVo(no, title, content);
			BoardDao bDao = new BoardDao();
			bDao.modify(bVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/bc?action=list");
			
		} 
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
