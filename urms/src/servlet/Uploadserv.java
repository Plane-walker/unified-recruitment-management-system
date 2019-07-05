package servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import account.Applicant;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Uploadserv
 */
@WebServlet("/Uploadserv")
public class Uploadserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Uploadserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("com_ID")); 
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		 String filePath ="";
	        dfif.setSizeThreshold(1024*1024*8);
	        dfif.setRepository(new File("java.io.tmpdir"));
	        ServletFileUpload upload = new ServletFileUpload(dfif);
	        upload.setSizeMax(1024*1024*50);
	        String uploadPath = this.getServletContext().getRealPath("");
	        HttpSession session = request.getSession();
	        uploadPath = uploadPath+"files\\"+(String)session.getAttribute("ID");
	        File uploadDir = new File(uploadPath);
	        if(!uploadDir.exists()){
	            uploadDir.mkdir();
	        }
	        try {
	            List<FileItem> formItem = upload.parseRequest(request);
	            if(formItem != null && formItem.size() >0){
	                for(FileItem item : formItem){
	                    if(!item.isFormField()){
	                        String fileName = new File(item.getName()).getName();
	                        Random rand=new Random();
	                        Applicant app=new Applicant();
	                        filePath = uploadPath+"\\"+app.getmd5(""+System.currentTimeMillis())+"_"+fileName;
	                        File storeFile = new File(filePath);
	                        item.write(storeFile);
	                    }
	                }
	            }
	        } catch (FileUploadException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        Applicant acc=new Applicant();
	        acc.applyupdate(request,filePath);
	        JSONObject json=new JSONObject();
	        json.put("message","success");
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
	        response.getWriter().print(json.toString());
	}

}
