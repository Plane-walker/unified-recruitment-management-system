package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Applicant;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Applyserv
 */
@WebServlet("/Applyserv")
public class Applyserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Applyserv() {
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
		Applicant acc=new Applicant();
		String info="";
			info=acc.apply(request);
		JSONObject json=new JSONObject();
		json.put("info", info);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(json.toString());
	}

}
