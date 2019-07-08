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

import account.Account;
import account.Applicant;
import account.Company;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Uploadavatorserv
 */
@WebServlet("/Uploadavatorserv")
public class Uploadavatorserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Uploadavatorserv() {
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
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		 String filePath ="";
		 String storepath="";
	        dfif.setSizeThreshold(1024*1024*8);
	        dfif.setRepository(new File("java.io.tmpdir"));
	        ServletFileUpload upload = new ServletFileUpload(dfif);
	        upload.setSizeMax(1024*1024*50);
	        String uploadPath = this.getServletContext().getRealPath("");
	        HttpSession session = request.getSession();
	        uploadPath = uploadPath+"images\\"+(String)session.getAttribute("ID");
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
	                        Applicant app=new Applicant();
	                        String fname=app.getmd5(""+System.currentTimeMillis())+"_"+fileName;
	                        filePath = uploadPath+"\\"+fname;
	                        storepath="images/"+(String)session.getAttribute("ID")+"\\"+fname;
	                        session.setAttribute("avator", storepath);
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
	        Account acc=null;
	        if(((String)session.getAttribute("type")).equals("login_app")||((String)session.getAttribute("type")).equals("register_app"))
				acc=new Applicant();
			else
				acc=new Company();
	        String delfile=acc.updateavator(request,storepath);	        
	        if(!delfile.equals("images/origin.jpg")) {
	        	delfile=this.getServletContext().getRealPath("")+delfile;
	        	File file = new File(delfile);  
		        if (file.exists() && file.isFile())
		        	file.delete();
	        }
	        JSONObject json=new JSONObject();
	        json.put("message","success");
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
	        response.getWriter().print(json.toString());
	}

}
