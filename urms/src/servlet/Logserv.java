package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.Account;
import account.Applicant;
import account.Company;

/**
 * Servlet implementation class Logserv
 */
@WebServlet("/Logserv")
public class Logserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Account acc=null;
		String url=request.getHeader("Referer");
		String[] index=url.split("/");
		url=index[index.length-1];
		if(url.equals("login_app")) 
			acc=new Applicant();
		else
			acc=new Company();
		String info=acc.login(request);
		if(info!=null) {
			session.setAttribute("logwarn", info);
			response.sendRedirect(url);
		}
		else {
			session.removeAttribute("logwarn");
			session.setAttribute("ID", request.getParameter("userID"));
			session.setAttribute("name", acc.getname());
			session.setAttribute("avator", acc.getavator());
			session.setAttribute("type", url);
			if(url.equals("login_app")) 
			response.sendRedirect("market");
			else
				response.sendRedirect("table");
		}
	}

}
