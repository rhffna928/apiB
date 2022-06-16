package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.MemberVo;
import jspstudy.domain.SearchCriteria;


public class BoardDao {
	private Connection conn;  
	private PreparedStatement pstmt;
	public BoardDao() {
		
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
		
	}
	
	
	
		public int insertBoard (String subject, String content, String writer, String ip, int Midx,String fileName){
			int value = 0;
			
		    //level은 예약어로 되어 있어 만들때 level_으로 만들었다
		    String sql = "insert into b_board(subject,content,writer,ip,originbidx,depth,level_,filename)" 
		    		+"select max ?,?,?,?,?,(bidx)+1,0,0,? from b_board";
		    try{
		    pstmt = conn.prepareStatement(sql);    //문자열된 쿼리를 연결시켜 구문화함
		    pstmt.setString(1, subject);
		    pstmt.setString(2, content);
		    pstmt.setString(3, writer);
		    pstmt.setString(4, ip);
		    pstmt.setInt(5, Midx);
		    pstmt.setString(6, fileName);

		    
		    value = pstmt.executeUpdate();
		    	
		   

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

		public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
			
			ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
			
			ResultSet rs = null;
			
			String str="";
			if(scri.getSearchType().equals("subject")) {
				str = "and subject like ?";	
			}else {
				str = "and writer like ?";
				
			}
			
	//		String sql = "select * from b_board where delyn = 'n' order by originbidx desc, depth asc";
		    String sql = "SELECT * FROM b_board WHERE delyn='n' "+ str +"ORDER BY ORIGINBIDx DESC, DEPTH asc limit ?, ?";	
			try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			pstmt.setInt(2, (scri.getPage()-1)*15+1);
			pstmt.setInt(3, (scri.getPage())*15);
			
			rs =  pstmt.executeQuery();
			
			
			
			
			while(rs.next()){
				
				BoardVo bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx"));
				bv.setSubject(rs.getString("subject"));
				bv.setContent(rs.getString("content"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				bv.setLevel_(rs.getInt("level_"));
				
				alist.add(bv);
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
		
		public BoardVo boardSelectOne(int bidx) {
			 BoardVo bv = null;
			 ResultSet rs = null;
			 String sql = "select * from b_board where bidx =?";
			
			 try {
				pstmt =  conn.prepareStatement(sql);   //쿼리화 시킴
				pstmt.setInt(1, bidx);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {       //다음값이 존재하면 true 커서는 다음행으로 이동
					bv = new BoardVo();
					bv.setBidx(rs.getInt("bidx"));   //rs에 담겨져있는 데이터를 bv에 담는다
					bv.setOriginbidx(rs.getInt("originbidx"));
					bv.setDepth(rs.getInt("depth"));
					bv.setLevel_(rs.getInt("level_"));
					bv.setSubject(rs.getString("subject"));
					bv.setContent(rs.getString("content"));
					bv.setWriter(rs.getString("writer"));
					bv.setWriteday(rs.getString("writeday"));
					bv.setFilename(rs.getString("filename"));
					
					
					
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
			
			
			return bv;
		}
		
		public int BoardModify (String subject, String content, String writer, String ip, int Midx,int Bidx_){
			int value = 0;
			
		    
		    String sql = "update b_board set subject=?,content=?,writer=?,ip=?,writeday=sysdate where bidx =?"; 
		    		
		    try{
		    pstmt = conn.prepareStatement(sql);    //문자열된 쿼리를 연결시켜 구문화함
		    pstmt.setString(1, subject);
		    pstmt.setString(2, content);
		    pstmt.setString(3, writer);
		    pstmt.setString(4, ip);
		    pstmt.setInt(5, Bidx_);

		    
		    value = pstmt.executeUpdate();
		    	
		   

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
		
		
		//디버깅시 확인할 것 :  쿼리를 그대로 복사해서 db에서 실행해본다.
		
		public int BoardDelete (int Bidx_){
			int value = 0;
			
		    
		    String sql = "update b_board set delyn='y',writeday=now() where bidx=?"; 
		    		
		    try{
		    pstmt = conn.prepareStatement(sql);    //문자열된 쿼리를 연결시켜 구문화함
		    pstmt.setInt(1, Bidx_);
		    
		    value = pstmt.executeUpdate();
		    	
		   

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
		
		
		

		public int BoardReply (BoardVo bv){
			int value = 0;
			
		    //level은 예약어로 되어 있어 만들때 level_으로 만들었다
		    String sql1 ="update b_board set depth = depth+1 where originbidx = ? and depth > ?";
		    
		    String sql2 ="insert into b_board(subject,content,writer,ip,midx,originbidx,depth,level_)" 
		    		      +"values(?,?,?,?,?,?,?,?)";
		    try{
		    conn.setAutoCommit(false);    //오라클 설정이 기본적으로 오토커밋 되어 있는데 이것을 종료시켜 수동으로 바꿈
		    pstmt = conn.prepareStatement(sql1);	
		    pstmt.setInt(1, bv.getOriginbidx());
		    pstmt.setInt(2, bv.getDepth());
		    pstmt.executeUpdate();
		    
		    
		    pstmt = conn.prepareStatement(sql2);    //문자열된 쿼리를 연결시켜 구문화함
		    
		    pstmt.setString(1, bv.getSubject());
		    pstmt.setString(2, bv.getContent());
		    pstmt.setString(3, bv.getWriter());
		    pstmt.setString(4, bv.getIp());
		    pstmt.setInt(5, bv.getMidx());
		    pstmt.setInt(6, bv.getOriginbidx());
		    pstmt.setInt(7, bv.getDepth()+1);
		    pstmt.setInt(8, bv.getLevel_()+1);
		    
		    value = pstmt.executeUpdate();
		    	
		    conn.commit();

		    }catch(Exception e){
		    	try {
					conn.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		    	e.printStackTrace();}
		    finally {
				
				try {
									
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}
			return  value;
			
		}
		public int boardTotal(SearchCriteria scri) {
			
			int cnt = 0;
			ResultSet rs = null;
			String str="";
			if(scri.getSearchType().equals("subject")) {
				str = "and subject like ?";	
			}else {
				str = "and writer like ?";
				
			}
			
			
			String sql = "select count(*) as cnt from b_board where delyn ='n'"+str+"";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+scri.getKeyword()+"%");
				rs = pstmt.executeQuery();
				
				
				
				if(rs.next()) {
					cnt = rs.getInt("cnt");
					
					
				}
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			
			
			return cnt;
		}
		
		
		
		
		
		}
		
		
	
	
	

