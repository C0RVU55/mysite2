package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	// 컨트롤러에서 포워드랑 리다이렉트 반복적으로 쓰기 때문에 유틸로 빼서 관리.
	// 같은 기능을 하는 코드를 하나의 함수로 묶을 수 있음.

	// forward
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher(path); // jsp파일 위치
		rd.forward(request, response);
	}

	// redirect
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		
		response.sendRedirect(url); // 재요청할 파일 주소(컨트롤러 거쳐가는 그 주소)
	}
}
