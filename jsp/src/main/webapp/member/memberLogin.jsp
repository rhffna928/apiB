<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE> New Document </TITLE> 
  <script>
  function check(){
     //alert("테스트입니다");
    var fm= document.frm;
  
    if (fm.memberId.value == ""){
       alert("아이디를 입력하세요");
        fm.memerId.focus();
        return;
    }else if(fm.memberPwd.value==""){
       alert("비밀번호를 입력하세요")
        fm.memberPwd.focus();
        return;
       
    }
      
    	
    alert("전송합니다");
    //fm.action="./memberJoinOk.jsp";
    fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.do"      //가상경로 활용으로 해킹 방지
    fm.method="post"; 
    fm.submit();
     
     
     
     
     return;  
  
  
  }
  
  
  </script>
 </HEAD>

 <BODY>
<center><h1>로그인</h1></center>	
<hr></hr>
<form name="frm" > 
 <table border="1" style="text-align:left;width:800px;height:300px">
<tr>
<td>아이디</td>
<td><input type="text" name="memberId" size="30"></td>
</tr>
<tr>	
<td>비밀번호</td>
<td><input type="password" name="memberPwd" size="30"></td>
</tr>
<tr>
<td>버튼</td>
<td>
<input type="button"  value="확인" onclick="check();"> 
<input type="reset" value="다시작성"> 
</td>
</tr>
 </table>
 </form>
 </BODY>
</HTML>
