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
<title>글 내용보기</title>
<script>
  function check(){
     
    var fm= document.frm;
  
   
    alert("전송합니다");
   
    fm.action = "<%=request.getContextPath()%>/board/boardContent.do"      //가상경로 활용으로 해킹 방지
    fm.method="post"; 
    fm.submit();
     
     
     
     
     return;  
  
  
  }
  
  
  </script>
</head>

<body>
<form name="frm">
<h1> 게시판 내용보기</h1>
<table border ="1" style="width:800px;">

<tr> 
<td style = "width:150px">제목(<%=bv.getWriteday().substring(0,10) %>)</td>
<td><%=bv.getSubject() %> </td>
</tr>
<tr>
<td style = "width:150px;height:300px;">내용</td>
<td><%=bv.getContent() %>
    <img src="<%=request.getContextPath()%>/images/<%=bv.getFilename()%>">
    <a href="<%=request.getContextPath()%>/board/fileDownload.do?filename=<%=bv.getFilename() %>"><%=bv.getFilename() %></a>
</td>
</tr>
<tr>
<td style = "width:150px">작성자</td>
<td><%=bv.getWriter() %></td>
</tr>

<tr>
<td style="text-align:right;" colspan="2">
<input type="button"  name="modify" value="수정" onclick="location.href='<%=request.getContextPath() %>/board/boardModify.do?bidx=<%=bv.getBidx() %>'"> 
<input type="button"  name="delete" value="삭제" onclick="location.href='<%=request.getContextPath() %>/board/boardDelete.do?bidx=<%=bv.getBidx() %>'"> 
<input type="button"  name="reply" value="답변" onclick="location.href='<%=request.getContextPath() %>/board/boardReply.do?bidx=<%=bv.getBidx() %>&originbidx=<%=bv.getOriginbidx() %>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>&subject=<%=bv.getSubject()%>&content=<%=bv.getContent()%>'"> 
<input type="button"  name="list" value="목록" onclick="location.href='<%=request.getContextPath() %>/board/boardList.do'"> 
</td>
</tr>

</table>

</form>

</body>
</html>