package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb"; 
	private String pw = "webdb";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private int count = 0;
	
	// 생성자
	// 메소드 겟셋
	// 메소드 일반
	
	// DB접속
	public void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	//////////////////////////////////////////////////
	
	// 입력
	public int insert (UserVo uVo) {
		
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query); //쿼리로 만들기
			
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPassword());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());

			count = pstmt.executeUpdate(); //쿼리문 실행

			// 4.결과처리
			System.out.println("insert 회원정보" + count + "건 저장");


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return count;
	}
	
	//로그인(로그인폼에서 받은 아이디 비번이 DB 안에 있는 데이터와 맞는지 비교)
	public UserVo getUser(String id, String pw) {
		
		UserVo uVo = null;
		
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  no, "; //세션이 사라질 위험이 있기 때문에 일부러 최소한의 컬럼만 가져오기로 함
			query += "         name ";
			query += " FROM users ";
			query += " where id = ? ";
			query += " and password = ? ";
			
			pstmt = conn.prepareStatement(query); //쿼리로 만들기 (쿼리로 번역)
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery(); //쿼리문 실행 (select니까 rs)

			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				uVo = new UserVo(no, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return uVo;
	}
	
	// 한 회원 정보 가져오기
	public UserVo getUser(int num) { //getAllUserInfo() 따로 만들 필요없이 로그인용 메소드로 오버로딩시킴
		UserVo uVo = null;
		
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  no, "; 
			query += "         id, ";
			query += "         password, ";
			query += "         name, ";
			query += "         gender ";
			query += " FROM users ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				uVo = new UserVo(no, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return uVo;
	}
	
	public int modify(UserVo uVo) {
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update users ";  
			query += " set password = ?, ";
			query += "     name = ?, ";
			query += "     gender = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); //쿼리로 만들기
			
			pstmt.setString(1, uVo.getPassword());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setInt(4, uVo.getNo());

			count = pstmt.executeUpdate(); //쿼리문 실행

			// 4.결과처리
			System.out.println("insert 회원정보" + count + "건 수정");


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return count;
	}
}
