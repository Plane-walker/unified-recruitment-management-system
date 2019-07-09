package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.Applicant;
import apply.Apply;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Bellserv
 */
@WebServlet("/Bellserv")
public class Bellserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bellserv() {
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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String app_ID=(String) session.getAttribute("ID");
		Apply[] app=null;
		Apply temp=new Apply();
		JSONArray jsona = new JSONArray();
		app=temp.getallinfo(app_ID);
		for(int i=0;i<app.length;i++) {
			JSONObject json=new JSONObject();
			json.put("information", app[i].getinformation());
			json.put("com_name", app[i].getcom_name());
				json.put("avator", app[i].getavator());
			jsona.put(json);
			}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
	}

}
