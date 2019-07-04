package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import position.Position;

/**
 * Servlet implementation class Refreshserv
 */
@WebServlet("/Refreshserv")
public class Refreshserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Refreshserv() {
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
		Position[] pos=new Position[size];
		JSONArray jsona = new JSONArray();
		if(request.getParameter("com_ID").length()==0&&request.getParameter("com_name").length()==0&&request.getParameter("pos_name").length()==0)
		for(int i=0;i<size;i++) {
			pos[i]=new Position();
			pos[i].gettop((page-1)*size+i+1);
			if(pos[i].getname()!=null) {
			JSONObject json=new JSONObject();
			json.put("com_ID", pos[i].getcom_ID());
			json.put("com_name", pos[i].getcom_name());
			json.put("name", pos[i].getname());
			if(pos[i].getinformation()!=null&&pos[i].getinformation().length()!=0)
			json.put("information", pos[i].getinformation());
			else
				json.put("information", "无");
			json.put("city", pos[i].getcity());
			if(pos[i].getacademic()!=null&&pos[i].getacademic().length()!=0)
			json.put("academic", pos[i].getacademic());
			else
				json.put("academic", "无");
			json.put("salary", pos[i].getsalary());
			json.put("number", pos[i].getnumber());
			json.put("type", pos[i].gettype());
			jsona.put(json);
			}
		}
		else {
			pos[0]=new Position();
			pos=pos[0].getspec(request);
			for(int i=0;i<pos.length;i++) {
				JSONObject json=new JSONObject();
				json.put("com_ID", pos[i].getcom_ID());
				json.put("com_name", pos[i].getcom_name());
				json.put("name", pos[i].getname());
				if(pos[i].getinformation()!=null&&pos[i].getinformation().length()!=0)
				json.put("information", pos[i].getinformation());
				else
					json.put("information", "无");
				json.put("city", pos[i].getcity());
				if(pos[i].getacademic()!=null&&pos[i].getacademic().length()!=0)
				json.put("academic", pos[i].getacademic());
				else
					json.put("academic", "无");
				json.put("salary", pos[i].getsalary());
				json.put("number", pos[i].getnumber());
				json.put("type",pos[i].gettype());
				jsona.put(json);
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
	}

}
