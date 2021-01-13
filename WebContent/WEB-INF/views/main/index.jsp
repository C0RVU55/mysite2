<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--
	//로그인(세션에 데이터 유무) 체크를 위해 세션값 받아서 if문 넣어줌
	//HttpSession session = request.getSesstion(); --> 컨트롤러에서 사용된 거라 다시 선언 불가
	
	//Object로 들어오니까 형변환
	//UserVo authUser = (UserVo)session.getAttribute("authUser");
	// --> <jsp:include><jsp:include> 공통되는 코드를 묶어서 jsp파일로 따로 뺌 --> header+navi, footer 옮김. 
	
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/main.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

	<%-- header + navi 공통으로 옮기고 <jsp:include>태그로 불러옴. 경로는 파일 경로고 다른 jsp 조합 가능 (html 주석으로 다니까 무슨 아파치 오류남)--%>
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	

		<!-- aside없음 -->

		<div id="full-content">
		
			<!-- content-head 없음 -->

			<div id="index"> 
			
				<img id="profile-img" src="/mysite2/assets/image/profile.jpg">
				
				<div id="greetings">
					<p class="text-xlarge">
						<span class="bold">안녕하세요!!<br>
						황일영의 MySite에 오신 것을 환영합니다.<br>
						<br>
						이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.<br>
						</span>
						<br>
						사이트 소개, 회원가입, 방명록, 게시판으로 구성되어 있으며<br>
						jsp&serlvet(모델2) 방식으로 제작되었습니다.<br>
						<br>
						자바 수업 + 데이터베이스 수업 + 웹프로그래밍 수업<br>
						배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.<br>
						<br>
						(자유롭게 꾸며보세요!!)<br>
						<br><br>
						<a class="" href="">[방명록에 글 남기기]</a>
					</p>	
				</div>
				<!-- //greetings -->
				
				<div class="clear"></div>
				
			</div>
			<!-- //index -->
			
		</div>
		<!-- //full-content -->
		<div class="clear"></div>
		
		<!-- footer -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

	</div>
	<!-- //wrap -->

</body>

</html>