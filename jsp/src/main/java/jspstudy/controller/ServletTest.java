package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// jsp는 html베이스에 <% %>안에 자바코드, servlet(클레스로 만드는 웹 페이지, jsp는 단순히 화면으로 보여주어 서버 부하를 막기 위한 클래스)은 자바 베이스에 html을 입력한다.
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ "<head><title>서블릿</title></head>"
				+ "<body>"
				+ "<h1>안녕하세요</h1>"
				+ "<h2>반갑습니다</h2>"
				+ "</body>"
				+ "</html>");
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
