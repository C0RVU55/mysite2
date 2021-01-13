package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action="+action);
		
		
		// 오류 : post 한글 깨짐
		
		
		if ("joinForm".equals(action)) {
			//System.out.println("회원가입폼");
			
			//포워드 --> joinForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if ("join".equals(action)) {
			//System.out.println("회원가입");
			
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
			
			//포워드 --> joinOk.jsp (받은 파라미터를 DB에 저장하고 회원가입완료 페이지만 띄우면 됨)
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if ("loginForm".equals(action)) {
			//System.out.println("로그인 폼");
			
			//포워드 --> loginForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if ("login".equals(action)) {
			//System.out.println("로그인");
			
			//로그인폼에서 받은 아이디와 비번이 DB에 있는 것과 같은지 비교하는 메소드 만들어야 됨 --> getUser()
			
			//파라미터
			String id = request.getParameter("id"); //getParameter() 안 ""은 주소에서 받는 name과 같아야 함
			String pw = request.getParameter("pw");
			
			//dao --> getUser() + vo
			UserDao uDao = new UserDao();
			UserVo authVo = uDao.getUser(id, pw); //인증된 사용자
			
			//id, pw 넣고 no, name 가져오기 (아이디, 비번이 DB에 있는 것과 맞으면 출력하고 안 맞으면 null 출력)
			System.out.println(authVo);
			
			//***세션 : 
			//1. 브라우저에 접속하는 유저에게 번호 부여해서 리퀘스트/리스폰할 때마다 특정 번호 넣어줌
			//2. 브라우저 끌 때까지 지속됨
			//3. 세션은 세션에 메모리공간을 할당하기만 함
			
			//세션에 데이터가 있으면 로그인 성공, 없으면 로그인X 상태인 걸 체크하는 코드 넣어야 됨 
			if(authVo == null) { //로그인 실패
				System.out.println("로그인 실패");
				
				//리다이렉트 --> 로그인폼 (***실패시 문구 추가를 위해 result값 추가***)
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail"); 
				
			} else { 	//로그인 성공일 때 세션 만들기
				System.out.println("로그인 성공");
				
				//세션 체크 + 세션 영역에 필요한 값(vo) 넣음
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo); //***리퀘스트 아니고 세션 영역의 attribute임 ("불러올이름", 변수명)
				
				//리다이렉트 --> main
				WebUtil.redirect(request, response, "/mysite2/main");	
			} 
			
		} else if ("logout".equals(action)) {
			//System.out.println("로그아웃");
			
			//세션 영역에 있는 vo를 삭제해야 됨
			//***jsp는 세션을 session.getAttribute()로 받아야 되고 서블렛(자바)은 아래처럼 직접 세션 꺼내 써야 됨***
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//리다이렉트 --> main
			WebUtil.redirect(request, response, "/mysite2/main");
			
		} else if ("mform".equals(action)) {
			//System.out.println("회원정보수정 폼");
			
			/* 이거 하니까 로그인한 이력이 없으면 세션이 아예 없는 거라? 오류남
			
			//세션 request.getSession(false) --> 현재 세션 있으면 기존 세션을, 없으면 null을 return 
			HttpSession session = request.getSession(false);
			
			if(session == null) {
				//로그인 세션 없으면 리다이렉트 --> main
				WebUtil.redirect(request, response, "/mysite2/main");
				
			} else {
				//이게 되나 --> 됨. 컨트롤러 안에서도 세션 getAttribute() 가능함.
				UserVo authUser = (UserVo)session.getAttribute("authUser"); 
				
				//세션에서 받은 UserVo의 no로 회원의 모든 정보를 가져온 후 (getAllUserInfo() 메소드 만들어야 됨)
				UserDao uDao = new UserDao();
				UserVo uVo = uDao.getUser(authUser.getNo());
				
				//회원정보(UserVo)를 attribute
				request.setAttribute("authUserVo", uVo);
			}
			*/
			
			//세션의 no
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인 안 한 상태면 못 가져옴(오류남)
			int no = authUser.getNo();
			
			//dao getUser() (로그인용 회원정보 가져오기)
			UserDao uDao = new UserDao();
			UserVo uVo = uDao.getUser(no);
			
			//회원정보 attribute
			request.setAttribute("uVo", uVo);
				
			//포워드 --> modifyForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			

		} else if ("modify".equals(action)) {
			//System.out.println("수정");
			
			//파라미터 (수정되는 값만 가져옴)
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//세션 가져와서 no 받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//세션의 no와 파라미터로 받은 값으로 UserVo 만듦 (맞는 생성자 만들기) --> dao modify()
			UserVo uVo = new UserVo(no, password, name, gender);
			UserDao uDao = new UserDao();
			uDao.modify(uVo);
			
			//session 정보 갱신 --> 세션에서 name값만 변경
			authUser.setName(name);
			
			//(수정한 UserVo를 세션용 UserVo에 넣고 세션 다시 불러와서 바뀐 UserVo를 setAttribute함) --> 원래 이랬는데 순서가 바뀜
			
			//리다이렉트 --> main
			WebUtil.redirect(request, response, "/mysite2/main");	
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
