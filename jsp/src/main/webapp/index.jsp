<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import ="jspstudy.dbconn.Dbconn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
<h1>메인 페이지 입니다. test  gggg   ttt yyyasdasdasd</h1>

<%Dbconn dbconn = new Dbconn();%>


<a href="<%=request.getContextPath() %>/member/memberList.do">회원 리스트</a>
<a href="<%=request.getContextPath() %>/member/memberJoin.do">회원 가입</a>
<a href="<%=request.getContextPath() %>/member/memberLogin.do">로그인</a>
<a href="<%=request.getContextPath() %>/board/boardWrite.do">게시판 글쓰기</a>
<a href="<%=request.getContextPath() %>/board/boardList.do">게시판 리스트</a>

</body>
</html>