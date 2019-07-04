package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Downloadserv
 */
@WebServlet("/Downloadserv")
public class Downloadserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Downloadserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				String filepath= request.getParameter("filepath");
				File serverFile=new File(filepath);
				System.out.println(filepath);
				String[] temp=filepath.split("\\\\");
				String filename=temp[temp.length-1];
				String fileName=java.net.URLEncoder.encode(filename,"utf-8");
				response.setHeader("Content-Disposition","attachment;filename="+fileName);
				long fileLength=serverFile.length();
				String length=String.valueOf(fileLength);
				response.setContentType("application/msword");
				response.setHeader("content_Length",length);
				OutputStream servletOutPutStream=response.getOutputStream();
				FileInputStream fileInputStream=new FileInputStream(serverFile);
				byte bytes[]=new byte[1024];
				int len=0;
				while((len=fileInputStream.read(bytes))!=-1)
				servletOutPutStream.write(bytes,0,len);
				servletOutPutStream.close();
				fileInputStream.close();
	}

}
