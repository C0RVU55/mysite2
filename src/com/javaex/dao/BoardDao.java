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
				int userNo = rs.getInt("user_no");

				BoardVo bVo = new BoardVo(no, title, name, hit, regDate, userNo);
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
			pstmt.setInt(4, bVo.getUserNo()); //이걸 리스트로 뺄 순 없나
			
			count = pstmt.executeUpdate();

			// 결과 처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;
	}
	
	public BoardVo read(int no) { //*********게시글 정보 가져오기*********
		
		BoardVo bVo = null;
		
		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " SELECT  b.no, ";
			query += "         name, ";
			query += "         hit, ";
			query += "         to_char(reg_date, 'YYYY-MM-DD HH24:MI') reg_date, ";
			query += "         title, ";
			query += "         content, ";
			query += "         user_no ";
			query += " FROM board b, users u ";
			query += " where  b.user_no = u.no ";
			query += " and b.no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();

			// 결과 처리
			while(rs.next()) {
				int num = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int userNo = rs.getInt("user_no");
				
				bVo = new BoardVo(num, name, hit, regDate, title, content, userNo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return bVo;
	}
	
	public int hitPlus(int no) { //*********조회수 증가*********
		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " update board ";
			query += " set hit = hit + 1  ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();

			// 결과 처리
			System.out.println(count + "건 조회수 증가");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;
	}
	
	public int delete(int no) { //*********삭제*********

		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();

			// 결과 처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;
	}
	
	public int modify(BoardVo bVo) { //*********수정*********
		getConnection();

		try {
			// SQL문 준비 / 바인딩 / 실행 
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());
			
			count = pstmt.executeUpdate();

			// 결과 처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;
	}
}
