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
		if(request.getParameter("com_name")==null||request.getParameter("com_name").length()==0)
		for(int i=0;i<size;i++) {
			pos[i]=new Position();
			pos[i].gettop((page-1)*size+i+1);
			if(pos[i].getname()!=null) {
			JSONObject json=new JSONObject();
			json.put("com_name", pos[i].getcom_name());
			json.put("name", pos[i].getname());
			json.put("information", pos[i].getinformation());
			jsona.put(json);
			}
		}
		else {

		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
		
	}

}
