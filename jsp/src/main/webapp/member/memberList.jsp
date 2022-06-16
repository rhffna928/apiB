<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "jspstudy.*"%> <!-- MemberDao생성을 위함 -->
<%@ page import = "java.util.*" %> <!-- ArrayList사용을 위함 -->
<%@ page import = "jspstudy.domain.*" %>
<%
 
// out.println(alist.get(0).getMembername()+"<br>");

   ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");
    //controller에서 받아온 alist정보를 jsp화면에 구현
     
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
<%
if(session.getAttribute("midx") != null){
	out.println("회원아이디 :"+session.getAttribute("memberId")+"<br>");
	out.println("회원이름 :"+session.getAttribute("memberName")+"<br>");
	
	out.println("<a href='"+request.getContextPath()+"/member/memberLogOut.do'>로그아웃</a>"+"<br>");
	
}



%>
회원 목록 만들기
<table border ="1" style ="width : 800px">
<tr style="color : green;">
<td>midx번호</td>
<td>이름</td>
<td>전화번호</td>
<td>작성일</td>
</tr>
<%  for (MemberVo mv : alist){ %>
<tr>
<td><%=mv.getMidx() %></td>
<td><%=mv.getMembername() %></td>
<td><%=mv.getMemberphone() %></td>
<td><%=mv.getWriteday() %></td>
</tr>
<% } %>










</table>







</body>
</html>