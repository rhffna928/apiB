<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@  page import="java.sql.*" %>
<%@ page import ="java.util.*"  %>
<%@ page import ="jspstudy.*" %>
<%@ page import ="jspstudy.domain.*" %>
<%!
public int insertMember (Connection conn,String memberId, String memberPwd,String memberName,String memberphone,String memberEmail,String memberJumin,String memberGender,String memberAddr,String hobby,String ip){
	int value = 0;
	PreparedStatement pstmt = null; 
    
    String sql = "insert into b_member(MIDX,MEMBERID,MEMBERPWD,MEMBERName,"
    		+"MEMBERGender,MEMBERAddr,MEMBERJumin,MEMBERphone,memberIP,MEMBEREmail,memberHobby)" 
    		+"values(midx_b_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
    try{
    pstmt = conn.prepareStatement(sql);    // statement는 해킹 위험이 있어서 preparedstatement를 사용한다.
    pstmt.setString(1, memberId);	
    pstmt.setString(2, memberPwd);
    pstmt.setString(3, memberName);
    pstmt.setString(4, memberGender);
    pstmt.setString(5, memberAddr);
    pstmt.setString(6, memberJumin);
    pstmt.setString(7, memberphone);
    pstmt.setString(8, ip);
    pstmt.setString(9, memberEmail);
    pstmt.setString(10, hobby);
    value = pstmt.executeUpdate();
    	
    //구문을 만드는 객체 (createStatement), 연결객체를 통해서 Statement 구문객체를 생성해서 stmt에 담는다. 
    //Statement stmt = conn.createStatement();
    //구문을 실행하고 리턴값으로 실행되었으면 1아니면 0을 value 변수에 담는다.
    //value = stmt.executeUpdate(sql);

    }catch(Exception e){
    	e.printStackTrace();}
	return value;
}

public ArrayList<MemberVo> memberSelectAll(Connection conn){
	//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체 생성한다
	ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// 쿼리구문을 문자열로 만들어 놓는다.
	String sql = "select * from b_member where delyn = 'N' order by midx desc";
	//연결객체에 있는 preparesStatement 메소드를 실행해서 sql 문자열을 담아 구문객체를 만든다.
	try{
	pstmt = conn.prepareStatement(sql);
	rs =  pstmt.executeQuery();
	
	while(rs.next()){
		//반복할때마다 객체생성한다
		MemberVo mv = new MemberVo();
		// rs에 담아놓은 컬럼값들을 mv에 옮겨 담는다.
		mv.setMidx(rs.getInt("midx"));
		mv.setMembername(rs.getString("memberName"));
		mv.setMemberphone(rs.getString("memberphone"));
		mv.setWriteday(rs.getString("writeday"));
		//alist에 값을 담은 객체를 추가한다.
		alist.add(mv);
	}
	
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	
	
	return alist;
	
}     










%>