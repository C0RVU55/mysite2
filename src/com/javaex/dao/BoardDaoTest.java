package com.javaex.dao;

import com.javaex.vo.BoardVo;

public class BoardDaoTest {

	public static void main(String[] args) {
		
		
		BoardDao bDao = new BoardDao();
		
		BoardVo vo1 = bDao.read(2);
		System.out.println(vo1);

	}

}
