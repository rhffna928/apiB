package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;

public class MemberDao {
	private Connection conn;  
	private PreparedStatement pstmt;
	public MemberDao() {
		
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
		
	}
	
	
	
		public int insertMember (String memberId, String memberPwd,String memberName,String memberGender,String memberAddr,String memberJumin,String memberphone,String ip,String memberEmail,String hobby){
			int value = 0;
			
		    
		    String sql = "insert into b_member(MEMBERID,MEMBERPWD,MEMBERName,"
		    		+"MEMBERGender,MEMBERAddr,MEMBERJumin,MEMBERphone,memberIP,MEMBEREmail,memberHobby)" 
		    		+"values(?,?,?,?,?,?,?,?,?,?)";
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
		    finally {
				
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}
			return value;
		}

		public ArrayList<MemberVo> memberSelectAll(){
			//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체 생성한다
			ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
			
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
			}finally {
			
			try {
				
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			return alist;
			
		}   	
		public MemberVo memberLogin(String memberId, String memberPwd) {
			MemberVo mv = null;
			ResultSet rs = null;
			String sql ="select * from b_member where delyn ='N' and memberid = ? and memberpwd = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				pstmt.setString(2, memberPwd);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					mv = new MemberVo();
					mv.setMidx(rs.getInt("midx"));
					mv.setMemberid(rs.getString("memberid"));
					mv.setMembername(rs.getString("memberName"));
					
					
					
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				
			}
			
			
			return mv;
		}
	
		
		}
