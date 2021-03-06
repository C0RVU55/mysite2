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

//로그인 상태에서만 할 수 있는 실행문은 pk로 비교하는 게 안전함
//많이 쓰이는 list를 action if문에서 else에 넣으면 쓰기 편함 (리스트 출력과 검색을 같이 넣을 수 있을 것)

@WebServlet("/bc")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//포스트 한글깨짐 방지 코드
		request.setCharacterEncoding("UTF-8");
		
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
			
			//본인이 쓴 글에는 조회수 안 올라가게 하기 위해 세션 부름
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//파라미터
			int no = Integer.parseInt(request.getParameter("no"));
			
			//dao --> 조회수 증가시킨 다음 게시글 정보 가져오기 (2개의 쿼리문 hitPlus, read가 작동하지만 작업은 하나인 거)
			BoardDao bDao = new BoardDao();
			
			//로그인계정 본인 아닐 때만 조회수 오르는 조건
			if (authUser == null || authUser.getNo() != bDao.read(no).getUserNo() ) {
				
				bDao.hitPlus(no);
			
			}
			
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
			
		} else if("search".equals(action)) { //리스트 출력이랑 겹치는데 같이 쓸 방법없는지
			//System.out.println("검색");
			
			List<BoardVo> bList = new ArrayList<BoardVo>();
			BoardDao bDao = new BoardDao();
			bList = bDao.getList();
			
			//파라미터
			String str = request.getParameter("keyword");
			bList = bDao.getList(str);
			
			//어트리뷰트
			request.setAttribute("bList", bList);
				
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
