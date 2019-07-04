package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.Applicant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import position.Position;

/**
 * Servlet implementation class Applyinfoserv
 */
@WebServlet("/Applyinfoserv")
public class Applyinfoserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Applyinfoserv() {
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
		String pos_name=request.getParameter("pos_name");
		HttpSession session = request.getSession();
		String com_ID=(String) session.getAttribute("ID");
		Applicant[] app=null;
		Applicant temp=new Applicant();
		JSONArray jsona = new JSONArray();
		app=temp.getallapp(com_ID, pos_name);
		for(int i=0;i<app.length;i++) {
			JSONObject json=new JSONObject();
			json.put("ID", app[i].getID());
			json.put("name", app[i].getname());
			if(app[i].getgender().equals("male"))
			json.put("gender", "男");
			else
				json.put("gender", "女");
			json.put("country", app[i].getcountry());
			if(app[i].getphone()!=null&&app[i].getphone().length()!=0)
			json.put("phone", app[i].getphone());
			else
				json.put("phone", "无");
			if(app[i].getemail()!=null&&app[i].getemail().length()!=0)
			json.put("email", app[i].getemail());
			else
				json.put("email", "无");
				json.put("filepath", app[i].getfilepath());
				json.put("information", app[i].getinformation());
			jsona.put(json);
			}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
	}

}
