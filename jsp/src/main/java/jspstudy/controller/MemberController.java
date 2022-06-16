package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jspstudy.domain.MemberVo;
import jspstudy.service.MemberDao;


@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		
		String pj = request.getContextPath();    //getpath : 프로젝트이름
		String command = uri.substring(pj.length());
		System.out.println("command:"+command);
		
		
		
		if(command.equals("/member/memberJoinAction.do")) {
			String memberId = request.getParameter("memberId");
		    String memberPwd = request.getParameter("memberPwd");
		    String memberName = request.getParameter("memberName");
		    String memberphone = request.getParameter("memberphone");
		    String memberEmail = request.getParameter("memberEmail");
		    String memberJumin = request.getParameter("memberJumin");
		    String memberGender = request.getParameter("memberGender");
		    String memberAddr = request.getParameter("memberAddr");
		    String[] memberHobby = request.getParameterValues("memberHobby");   
		    //getParameterValues : 중복값을 담을 수 있는 툴
		    String hobby = "";
		    for(int i=0; i<memberHobby.length;i++){
		    	hobby = hobby +","+ memberHobby[i];
		    //	out.println(memberHobby[i]+"<br>");
		    
		    }
		    hobby = hobby.substring(1);
		    
//		    out.println(memberId+"<br>");
//		    out.println(memberPwd+"<br>");
		//   out.println(memberName+"<br>");
//		    out.println(memberphone+"<br>");
//		    out.println(memberEmail+"<br>");
//		    out.println(memberJumin+"<br>");
//		    out.println(memberGender+"<br>");
//		    out.println(memberAddr+"<br>");

		    String ip = InetAddress.getLocalHost().getHostAddress();   //회원 ip뽑아오기
		    MemberDao md = new MemberDao();
		    int value = md.insertMember(memberId,memberPwd,memberName,memberGender,memberAddr,memberJumin,memberphone,ip,memberEmail,hobby);

		    // out.println(value);
		    // html은 보내는 기능말고 받는 기능이 없으므로 위에서 작업을 통해 받는 기능을 만든다. 
		    // getparameter는 받은 자료를 출력하는 메소드이다.   
		    // html은 String타입만 넘길수 있다.
		        //입력이 되었으면			
		    PrintWriter out = response.getWriter();
		    if (value ==1){
		    	response.sendRedirect(request.getContextPath()+"/member/memberList.do");
		    //	out.println("<script> alert('회원가입성공');location.href='"+request.getContextPath()+"/' </script>");
		    		//index로 가는 이유는 서버에 기본적으로 index가 welcome페이지에 등록 되어 있어 따로 지정 하지 않아도 연결된다. 만약 다른 페이지로 이동하고 싶다면 / 뒤에 페이지를 쓰거나 서버 웰컴페이지에 그 페이지를 등록하여야 한다.
		        //자바에서 서버 이동 방식은 forward이고 update delete같은 처리해야하는 방식이 끝났을 때는 sendRedirect방식으로 새로 이동한다. (보안상 이유)
		    }else{
		    	response.sendRedirect(request.getContextPath()+"/member/memberJoin.do");
		    //	out.println("<script> alert('회원가입실패');location.href='"+request.getContextPath()+"/' </script>");
		    }
		 
		}else if (command.equals("/member/memberJoin.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
			rd.forward(request, response);
		}else if(command.equals("/member/memberList.do")) {
			
			 MemberDao md = new MemberDao();	
			 ArrayList<MemberVo> alist = md.memberSelectAll();
			request.setAttribute("alist", alist);
			//setattribute를 통해 controller가 객체 생성 및 데이터를 담는 과정을 모두 담당
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request, response);
			
			
		}else if (command.equals("/member/memberLoginAction.do")) {
			System.out.println("로그인처리화면에 들어왔음");
			//1. 넘어온값
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			
			//2. 처리
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			System.out.println("mv"+mv);
			HttpSession session = request.getSession();
			
			
			//3. 이동
	        if(mv!=null) {
	        	session.setAttribute("midx", mv.getMidx());
	        	session.setAttribute("memberId", mv.getMemberid());
	        	session.setAttribute("membername", mv.getMembername());
	        	
	        	if(session.getAttribute("saveUrl") != null){
	        	   response.sendRedirect((String)session.getAttribute("saveUrl"));
	        	}else {
	        		response.sendRedirect(request.getContextPath()+"/member/memberList.do");
	        	}
	        	
	        }else {
	        response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");	
	        }
			
		}else if (command.equals("/member/memberLogin.do")) {
			System.out.println("로그인화면에 들어왔음");
			
			
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
			rd.forward(request, response);
		}else if (command.equals("/member/memberLogOut.do")) {
			HttpSession session = request.getSession();
			session.invalidate();  //세션 초기화
			response.sendRedirect(request.getContextPath()+"/");
			// welcome 페이지로 서버에 기본 등록 되어있기 때문에 생략되어도 index로 간다
		}
			
		
		}
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
