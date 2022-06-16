<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "jspstudy.domain.BoardVo"%> 
<%
    BoardVo bv = (BoardVo)request.getAttribute("bv");

%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script>
  function check(){
     
    var fm= document.frm;
  
   
   
    alert("전송합니다");
   
    fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do"      //가상경로 활용으로 해킹 방지
    fm.method="post"; 
    fm.submit();
     
     
     
     
     return;  
  
  
  }
</script>  
</head>
<body>
<h1> 삭제여부확인</h1>
<form name="frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx()%>">
<table border = 1 style="width : 800px; border-collapse : collapse; text-align : center">
<tr>
<td> 삭제하시겠습니까? </td>
</tr>
<tr>
<td>
<input type="button"   value="삭제" onclick="check();">
<input type="button"   value="취소" onclick="history.back();">
</td>
</tr>
</table>
</form>

</body>
</html>