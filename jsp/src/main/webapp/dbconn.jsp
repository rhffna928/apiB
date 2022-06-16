<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<% 

String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
String user="system";
String password="1234";


//등록한 드라이버 중에 사용가능한 클래스를 찾아서 생성 (오라클과 자바를 연결하는 과정)
Class.forName("oracle.jdbc.driver.OracleDriver");  		
//연결정보를 통해서 연결객체를 참조변수 conn에 담는다
Connection conn =  DriverManager.getConnection(url, user, password);





%>    
    
    
    
