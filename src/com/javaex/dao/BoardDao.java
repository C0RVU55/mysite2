package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private int count = 0;

	// 생성자
	// 메소드 겟셋
	// 메소드 일반

	// DB접속 메소드
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

	// 자원 정리 메소드
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

	///////////////////////////////////////////////////////
	
	public List<BoardVo> getList(){ //*********리스트*********
		List<BoardVo> bList = new ArrayList<BoardVo>();

		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " SELECT  b.no, ";
			query += "         title, ";
			query += "         content, ";
			query += "         hit, ";
			query += "         to_char(reg_date, 'YYYY-MM-DD HH24:MI') reg_date, ";
			query += "         user_no, ";
			query += "         id, ";
			query += "         password, ";
			query += "         name, ";
			query += "         gender ";
			query += " FROM board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by b.no desc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 결과 처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");

				BoardVo bVo = new BoardVo(no, title, name, hit, regDate);
				bList.add(bVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return bList;
	}
	
	public int write(BoardVo bVo) { //*********등록*********

		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, ?, sysdate, ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getHit());
			pstmt.setInt(4, bVo.getUserNo());
			
			count = pstmt.executeUpdate();

			// 결과 처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;
	}
}
