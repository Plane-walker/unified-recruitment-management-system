package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Applicant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import position.Position;

/**
 * Servlet implementation class Employeeserv
 */
@WebServlet("/Employeeserv")
public class Employeeserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Employeeserv() {
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
		int size=Integer.valueOf(request.getParameter("size"));
		int page=Integer.valueOf(request.getParameter("page"));
		Applicant[] app=new Applicant[size];
		JSONArray jsona = new JSONArray();
		for(int i=0;i<size;i++) {
			app[i]=new Applicant();
				app[i].getemployee(request,(page-1)*size+i+1);
			if(app[i].getname()!=null) {
			JSONObject json=new JSONObject();
			json.put("ID", app[i].getID());
			json.put("name", app[i].getname());
			json.put("country", app[i].getcountry());
			if(app[i].getgender().equals("male"))
			json.put("gender", "男");
			else
				json.put("gender", "女");
			if(app[i].getphone()!=null&&app[i].getphone().length()!=0)
			json.put("phone", app[i].getphone());
			else
				json.put("phone", "无");
			if(app[i].getemail()!=null&&app[i].getemail().length()!=0)
				json.put("email", app[i].getemail());
				else
					json.put("email", "无");
			if(app[i].getpos_name()!=null&&app[i].getpos_name().length()!=0)
				json.put("pos_name", app[i].getpos_name());
				else
					json.put("pos_name", "无");
			json.put("avator", app[i].getavator());
			jsona.put(json);
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
	}

}
