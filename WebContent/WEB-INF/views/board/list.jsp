<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

	<!-- header + navi -->
	<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="/mysite2/bc" method="get"> <!-- 검색 기능 -->
						<div class="form-group text-right">
							<input type="text" name="keyword">
							<button type="submit" id=btn_search>검색</button>
							<!-- action -->
							<input type="text" name="action" value="search">
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
						
						<!-- 리스트에서 데이터(주소)를 하나 꺼내 쓰는 거니까 var이름을 list보단 vo라고 쓰는 게 좋음. -->
						<c:forEach items="${bList }" var="vo">
							<tr>
								<td>${vo.no }</td>
								<td class="text-left"><a href="/mysite2/bc?action=read&no=${vo.no }">${vo.title }</a></td>
								<td>${vo.name }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
								
								<td>
								<c:if test="${vo.userNo == sessionScope.authUser.no }"> <!-- 로그인 상태 + 본인 계정만 가능(유저번호 비교) -->
								<a href="/mysite2/bc?action=delete&no=${vo.no }">[삭제]</a> <!-- 파라미터 글번호 -->
								</c:if>
								</td> 
							
							</tr>
						</c:forEach>
						
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<c:if test="${sessionScope.authUser != null }">  <!-- 로그인만 가능 -->
					<a id="btn_write" href="/mysite2/bc?action=wform">글쓰기</a>
					</c:if>
					
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->
		
	</div>
	<!-- //wrap -->

</body>

</html>
