package com.javaex.vo;

public class BoardVo {

	// 필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String id;
	private String password;
	private String name;
	private String gender;

	// 생성자
	public BoardVo() {
	}

	public BoardVo(int no, String title, String name, int hit, String regDate) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
	}
	
	public BoardVo(String title, String content, int hit, int userNo) {
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.userNo = userNo;
	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String id,
			String password, String name, String gender) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.id = id;
		this.password = password;
		this.name = name;
		this.gender = gender;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	// 메소드 일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", gender=" + gender + "]";
	}

}
