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
 * Servlet implementation class Regserv
 */
@WebServlet("/Regserv")
public class Regserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Regserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account acc=null;
		String url=request.getHeader("Referer");
		String[] index=url.split("/");
		url=index[index.length-1];
		if(url.equals("register_app")) 
			acc=new Applicant();
		else
			acc=new Company();
		String info=acc.register(request);
		if(info!=null) {
			session.setAttribute("regwarn", info);
			response.sendRedirect(url);
		}
		else {
			session.removeAttribute("regwarn");
			session.setAttribute("ID", request.getAttribute("ID"));
			session.setAttribute("name", request.getAttribute("name"));
			session.setAttribute("type", request.getAttribute(url));
		}
		
	}

}
