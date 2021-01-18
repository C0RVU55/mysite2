package com.javaex.vo;

public class BoardVo {

	// 필드 (필요한 것만 넣어야 되고 비밀번호 같은 건 보안상 안 넣는 게 좋음)
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String name;


	// 생성자
	public BoardVo() {
	}

	//수정
	public BoardVo(int no, String title, String content) { 
		this.no = no;
		this.title = title;
		this.content = content;
	}
	
	//글리스트
	public BoardVo(int no, String title, String name, int hit, String regDate, int userNo) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
	}
	
	//글쓰기
	public BoardVo(String title, String content, int hit, int userNo) { 
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.userNo = userNo;
	}

	//글읽기
	public BoardVo(int no, String name, int hit, String regDate, String title, String content, int userNo) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.name = name;
		this.userNo = userNo;
	}

	// 메소드 겟셋
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//메소드 일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", name=" + name + "]";
	}
	
}
