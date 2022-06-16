<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "jspstudy.domain.BoardVo"%> 
<%
    BoardVo bv = (BoardVo)request.getAttribute("bv");

%>
<%
if(session.getAttribute("midx") ==null){
	out.println("<script>alert('로그인해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판 답변달기</title>
<script>
function check(){
	var fm = document.frm
	if (fm.subject.value == ""){
	       alert("제목을 입력하세요");
	        fm.subject.focus();
	        return;
	}else if(fm.content.value==""){
	       alert("내용을 입력하세요")
	        fm.content.focus();
	        return;
	}else if(fm.writer.value==""){
	       alert("작성자를 입력하세요")
	        fm.writer.focus();
	        return;
	
	}
	alert("전송합니다");
fm.action = "<%=request.getContextPath()%>/board/boardReplyAction.do"     
    fm.method="post"; 
    fm.submit();
      return;
     
}
</script>
</head>
<body>
<h1>답변쓰기</h1>
<form name = "frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx()%>">
<input type="hidden" name="originbidx" value="<%=bv.getOriginbidx()%>">
<input type="hidden" name="depth" value="<%=bv.getDepth()%>">
<input type="hidden" name="level_" value="<%=bv.getLevel_()%>"> 
<table border = 1 style = "width : 800px; border-collapse : collapse">
<tr>
<td style="width : 100px;">제목</td>
<td><input type ="text" name ="subject" size = "100" value ="<%=bv.getSubject() %>-1"></td>
</tr>
<tr>
<td>내용</td>
<td><textarea  cols="100" rows="10" name = "content" ><%=bv.getContent()%></textarea></td>
</tr>
<tr>
<td>작성자</td>
<td><input type ="text" name ="writer" size = "100"></td>
</tr>
<tr>
<td colspan=2 style= "text-align : right"> <input type="button"  value="확인" onclick="check();"> 
<input type="reset" value="다시작성"></td>
</tr>

</table>



</form>



</body>
</html>