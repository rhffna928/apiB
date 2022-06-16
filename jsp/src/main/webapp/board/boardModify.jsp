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
<title>글 내용보기</title>
<script>
  function check(){
     
    var fm= document.frm;
  
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
         fm.writer.focus()
         return;
     }
   
    alert("전송합니다");
   
    fm.action = "<%=request.getContextPath()%>/board/boardModifyAction.do"      //가상경로 활용으로 해킹 방지
    fm.method="post"; 
    fm.submit();
     
     
     
     
     return;  
  
  
  }
  
  
  </script>
</head>
<body>
<form name="frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx()%>">
<h1> 게시판 수정하기</h1>
<table border ="1" style="width:800px;">
<tr> 
<td style = "width:150px">제목</td>
<td><input type = "text" name="subject" style = "width:600px;" value="<%=bv.getSubject() %>"> </td>
</tr>
<tr>
<td style = "width:150px;height:300px;">내용</td>
<td><textarea name="content"  style = "width:600px;height:300px;"> <%=bv.getContent()%></textarea> </td>
</tr>
<tr>
<td style = "width:150px">작성자</td>
<td><input type = "text" name="writer" style = "width:600px;" value="<%=bv.getWriter()%>"></td>
</tr>

<tr>
<td style="text-align:right;" colspan="2">

<input type="button"   value="확인" onclick="check();">
<input type="reset" value="다시작성">  
 
</td>
</tr>

</table>

</form>

</body>
</html>