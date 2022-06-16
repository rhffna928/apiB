package jspstudy.controller;

import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.MemberVo;
import jspstudy.domain.PageMaker;
import jspstudy.domain.SearchCriteria;
import jspstudy.service.BoardDao;
import jspstudy.service.MemberDao;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		
		//가상경로로 온 request가 있으면 처리
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		
		String pj = request.getContextPath();    
		String command = uri.substring(pj.length());
		System.out.println("command:"+command);
	    
		int sizeLimit = 1024*1024*15;  //15mb
		String uploadPath = "D:\\openapi(B)\\dev\\jspstudy\\src\\main\\webapp\\"; 
		String saveFolder = "images";
		String saveFullPath = uploadPath+saveFolder;
		
		if(command.equals("/board/boardWriteAction.do")) {
			System.out.println("글쓰기 처리 화면으로 들어왔음");
			
			MultipartRequest multipartRequest =null;
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"utf-8",new DefaultFileRenamePolicy());
			
			//DefaultFileRenamePolicy : 이름이 겹쳤을때 기본값을 쓰겠다는의미
			
			
			String subject = multipartRequest.getParameter("subject");
		    String content = multipartRequest.getParameter("content");
		    String writer = multipartRequest.getParameter("writer");
		    
		    //열거자인 저장될 파일을 담는 객체생성
		    Enumeration files = multipartRequest.getFileNames();
		    //담긴 객체의 파일 이름을 얻는다
		    String file = (String)files.nextElement();
		    //넘어오는 객체 중에 해당되는 파일 이름으로 되어있는 파일이름을 추출한다(저장되는 파일 이름)
		    String fileName = multipartRequest.getFilesystemName(file);
		    //원본의 파일이름
		    String originFileName = multipartRequest.getOriginalFileName(fileName);
		    
		    
		    //int midx = 2;
            HttpSession session = request.getSession();
		    int midx = (int)session.getAttribute("midx");
		    //System.out.println(subject+";"+content+";"+writer);
		
		    String ip = InetAddress.getLocalHost().getHostAddress();   
		    BoardDao bd = new BoardDao();
		    int value = bd.insertBoard(subject,content,writer,ip,midx,fileName);

		    			
		    if (value ==1){
		    	response.sendRedirect(request.getContextPath()+"/index.jsp");
		   
		    }else{
		    	response.sendRedirect(request.getContextPath()+"/board/boardWrite.do");
		  
		    }
		 
		}else if (command.equals("/board/boardWrite.do")) {
			System.out.println("글쓰기 화면에 들어왔음");
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response);
		
		
		}else if(command.equals("/board/boardList.do")) {
			
			String page = request.getParameter("page");
			if(page ==null) page ="1";
			int pagex = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");
			if(keyword ==null) keyword ="";
			
			
			String searchType = request.getParameter("searchType");
			if (searchType ==null) searchType = "subject";
			
			SearchCriteria scri = new SearchCriteria();
			 scri.setPage(pagex);
			 scri.setSearchType(searchType);
			 scri.setKeyword(keyword);
			 PageMaker pm = new PageMaker();
			
			
			
			 BoardDao bd = new BoardDao();	
			 int cnt = bd.boardTotal(scri);
			 
			 pm.setScri(scri);
			 pm.setTotalCount(cnt);
			 
			 ArrayList<BoardVo> alist = bd.boardSelectAll(scri);
			request.setAttribute("alist", alist);
			request.setAttribute("pm", pm);
			
			
			//setattribute를 통해 controller가 객체 생성 및 데이터를 담는 과정을 모두 담당
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
			
			//요청에 대한 응답을 누가할지 선택할 수 있습니다. 요청을 완전히 다른 URL로 방향을 바꿀 수 있으며(이를 리다이렉트(redirect)라고 함), 아니면 웹 애플리케이션에 있는 다른 컴포넌트(보통 JSP)에게 처리를 위임할 수도(이를 디스패치(dispatch)라고 함) 있습니다.
		    // redirect는 내부와 외부의 연결이고 forward는 서버 내에서의 연결로 forward는 클라이언트 입장에서 연결이 되었는지 알수가 없다.
			
			
		}else if(command.equals("/board/boardContent.do")) {
			 //넘어온 값을 받는다
			 String bidx = request.getParameter("bidx");
			 int bidx_ = Integer.parseInt(bidx);
			 
			 
			 //처리한다
			 BoardDao bd = new BoardDao();
			 BoardVo bv = bd.boardSelectOne(bidx_);
			 request.setAttribute("bv", bv); //내부에 같은 위치에서 자원을 공유한다   "객체명", 객체
			 //이동한다
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardcontent.jsp");
			rd.forward(request, response);
			
			
		}else if(command.equals("/board/boardModifyAction.do")) {
			System.out.println("글수정 처리 화면으로 들어왔음");
			String subject = request.getParameter("subject");
		    String content = request.getParameter("content");
		    String writer = request.getParameter("writer");
		    String bidx = request.getParameter("bidx");
		    System.out.println("bidx"+bidx);
			 int bidx_ = Integer.parseInt(bidx);
		    //int midx = 2;
		    HttpSession session = request.getSession();
		    int midx = (int)session.getAttribute("midx");
		    
		    System.out.println(subject+";"+content+";"+writer+";"+bidx_);
		
		    String ip = InetAddress.getLocalHost().getHostAddress();   
		    BoardDao bd = new BoardDao();
		    int value = bd.BoardModify(subject, content, writer, ip, midx, bidx_);

		    		System.out.println("value"+value);	
	      	if (value ==1){
				    	response.sendRedirect(request.getContextPath()+"/index.jsp");
				   
	        }else{
	        	response.sendRedirect(request.getContextPath()+"/board/boardModify.do");
	        }
				  
				    
		  
		 
		}else if(command.equals("/board/boardModify.do")) {
		    System.out.println("글수정 화면에 들어왔음");
		    String bidx = request.getParameter("bidx");
		    System.out.println("bidx"+bidx);
			 int bidx_ = Integer.parseInt(bidx);
			 
			 
			 
			 BoardDao bd = new BoardDao();
			 BoardVo bv = bd.boardSelectOne(bidx_);
			 request.setAttribute("bv", bv);
		
		
			
			
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");
			rd.forward(request, response);
		
		
		}else if(command.equals("/board/boardDeleteAction.do")) {
			System.out.println("글삭제 처리 화면으로 들어왔음");
			
		    String bidx = request.getParameter("bidx");
		    System.out.println("bidx"+bidx);
			 int bidx_ = Integer.parseInt(bidx);
		    

		    
		    System.out.println(bidx_);
		
		    String ip = InetAddress.getLocalHost().getHostAddress();   
		    BoardDao bd = new BoardDao();
		    int value = bd.BoardDelete(bidx_);

		    		System.out.println("value"+value);	
	      	if (value ==1){
				    	response.sendRedirect(request.getContextPath()+"/index.jsp");
				   
	        }else{
	        	response.sendRedirect(request.getContextPath()+"/board/boardDelete.do");
	        }
				  
				    
		  
		 
		}else if(command.equals("/board/boardDelete.do")) {
			System.out.println("글삭제 화면으로 들어왔음");
			String bidx = request.getParameter("bidx");
		    System.out.println("bidx"+bidx);
			 int bidx_ = Integer.parseInt(bidx);
			
			 BoardDao bd = new BoardDao();
			 BoardVo bv = bd.boardSelectOne(bidx_);
			 request.setAttribute("bv", bv);
			
			
			//setattribute를 통해 controller가 객체 생성 및 데이터를 담는 과정을 모두 담당
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
		
		
		
	}if(command.equals("/board/boardReplyAction.do")) {
		System.out.println("답변 처리 화면으로 들어왔음");
		String subject = request.getParameter("subject");
	    String content = request.getParameter("content");
	    String writer = request.getParameter("writer");
	    String bidx = request.getParameter("bidx");
	    String originbidx = request.getParameter("originbidx");
	    String depth = request.getParameter("depth");
	    String level_= request.getParameter("level_");
	    
	    System.out.println(subject+";"+content+";"+writer+";"+originbidx+";"+depth+";"+level_);
	
	    String ip = InetAddress.getLocalHost().getHostAddress();  
	    //int midx = 2;
	    HttpSession session = request.getSession();
	    int midx = (int)session.getAttribute("midx");
	    BoardDao bd = new BoardDao();
	    BoardVo bv = new BoardVo();
	    bv.setSubject(subject);
	    bv.setContent(content);
	    bv.setWriter(writer);
	    bv.setIp(ip);
	    bv.setBidx(Integer.parseInt(bidx));
		bv.setOriginbidx(Integer.parseInt(originbidx));
		bv.setDepth(Integer.parseInt(depth));
		bv.setLevel_(Integer.parseInt(level_));
	    bv.setMidx(midx);
	    int value = bd.BoardReply(bv);
        	
        
        if (value ==1){
	    	response.sendRedirect(request.getContextPath()+"/board/boardList.do");
	   
	    }else{
	    	response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);
	  
	    }
	 
	}else if (command.equals("/board/boardReply.do")) {
		System.out.println("글답변 화면에 들어왔음");
		
		String bidx = request.getParameter("bidx");
		String originbidx = request.getParameter("originbidx");
		String depth = request.getParameter("depth");
		String level_ = request.getParameter("level_");
	    String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		BoardVo bv = new BoardVo();
		bv.setBidx(Integer.parseInt(bidx));
		bv.setOriginbidx(Integer.parseInt(originbidx));
		bv.setDepth(Integer.parseInt(depth));
		bv.setLevel_(Integer.parseInt(level_));
		bv.setSubject(subject);
		bv.setContent(content);
		
		
		 
		request.setAttribute("bv", bv);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");
		rd.forward(request, response);
	
	
	}else if(command.equals("/board/fileDownload.do")) {
	  
	  String filename = request.getParameter("filename");
	  //파일의 전체경로
	  String filePath = saveFullPath+File.separator+filename;	
	  //separator(구분자) : 파일 경로와 파일명을 구분하는 것, 운영체제에 따라 구분자가 달라 file.separator로 표현
	  Path source = Paths.get(filePath);	
	  //파일을 실제로 찾아서 확인
	  String mimeType = Files.probeContentType(source);
	  //post방식과 같이 헤더에 mimeType(파일형식)을 담아서 넘김
	  response.setContentType(mimeType);
	  
	  //파일 이름이 글자가 깨지지 않게 인코딩
	  String encodingFileName = new String(filename.getBytes("utf-8"));
	  //첨부해서 다운로드 되는 파일을 헤더정보에 담는다 content-disposition이라는 이름으로 첨부된 파일명을 보낸다.
	  response.setHeader("Content-Disposition", "attachment;fileName="+encodingFileName);	
	  
	  //파일을 읽어들이고 출력하는 스트림, 해당 위치에 잇는 파일을 읽어들인다.
	  FileInputStream fileInputStream = new FileInputStream(filePath);
	  //파일쓰기위한 스트림(브라우저 출력)
	  ServletOutputStream servletOutStream = response.getOutputStream(); 
	  
	  //4096 / 1024 = 4kb
	  byte[] b = new byte[4096];
	  
	  int read = 0;
	  //4킬로 바이트의 처리 속도로 읽어들여서 반복문을 통해 다시 작성한다. 숫자 0은 배열 0부터를 의미한다.
	  while((read = fileInputStream.read(b, 0, b.length))!=-1) {
		  
		  servletOutStream.write(b, 0, read);
	  }
	  //출력
	  servletOutStream.flush();  //stream에 남아 있을 수 있는 데이터를 모두 처리 한다
	  servletOutStream.close();
	  fileInputStream.close();
	  
	}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
