<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "jspstudy.*"%> <!-- BoardDao생성을 위함 -->
<%@ page import = "java.util.*" %> <!-- ArrayList사용을 위함 -->
<%@ page import = "jspstudy.domain.*" %>
<%
 

    PageMaker pm = (PageMaker)request.getAttribute("pm");
   ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
    //controller에서 받아온 alist정보를 jsp화면에 구현
     
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
게시판리스트
<form name="frm" action="<%=request.getContextPath()%>/board/boardList.do" method="post">
<table border=0 style ="with:800px;text-align:right">
<tr>
<td style="width:620px;">
   <select name="searchType">
   <option value="subject">제목</option>
   <option value="writer">작성자</option> 
   </select> 
</td>
<td>
   <input type="text" name ="keyword" size="10">
</td>
<td>
   <input type="submit" name="submit" value="검색">
</td>
</tr>
</table>
</form>
<table border ="1" style ="width : 800px">
<tr style="color : green;">
<td>bidx번호</td>
<td>제목</td>
<td>작성자</td>
<td>작성일</td>
</tr>
<%  for (BoardVo bv : alist){ %>
<tr>
<td><%=bv.getBidx() %></td>
<td>
<%
for(int i =1; i<=bv.getLevel_();i++){
	out.println("&nbsp;&nbsp;");
	if(i == bv.getLevel_()){
		out.println("ㄴ");	
		
		
		
	}
}




%>
<a href = "<%= request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx()%>"><%=bv.getSubject() %></a></td>
<td><%=bv.getWriter() %></td>
<td><%=bv.getWriteday() %></td>
</tr>
<% } %>










</table>
<table style ="width : 800px;text-align: center;">
<tr>
<td style ="width : 200px;text-align:right">
<% 
String keyword = pm.getScri().getKeyword();

String searchType = pm.getScri().getSearchType();


 if(pm.isPrev() == true){
	 out.println("<a href ='"+request.getContextPath()+"/board/boardList.do?page="+(pm.getStartPage()-1)+"&keyword="+keyword+"&searchType="+searchType+"'>◀</a>");
 }
%>
</td>
<td><% 
for(int i = pm.getStartPage(); i<=pm.getEndPage(); i++){
  out.println("<a href ='"+request.getContextPath()+"/board/boardList.do?page="+i+"&keyword="+keyword+"&searchType="+searchType+"'>"+i+"</a>");

}
%></td>
<td style ="width : 200px;text-align:left">
<%
if (pm.isNext()&&pm.getEndPage() >0){
   out.println("<a href ='"+request.getContextPath()+"/board/boardList.do?page="+(pm.getEndPage()+1)+"&keyword="+keyword+"&searchType="+searchType+"'>▶</a>");
}
%></td>
</tr>





</table>




</body>
</html>