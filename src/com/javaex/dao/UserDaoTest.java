package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {
		// Dao에 있는 메소드 테스트하고 싶은데 파라미터까지 다 갖춰져야만 가능한 경우
		// 메소드만 테스트하려면 임의로 main 클래스 만들어서 값 넣어보면 됨
		// 서버랑 상관없으니까 run은 자바 어플리케이션으로 하면 됨
		
		// Dao조건에 맞으면 각 컬럼에 맞는 값 가져오고 안 맞으면 null이라고만 뜨면 정상 작동하는 거
		
		UserDao uDao = new UserDao();
		UserVo uVo = uDao.getUser("aaaㅁ", "1234");
		
		
		System.out.println(uVo);

	}

}
