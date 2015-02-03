
package com.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;
import com.dao.UserDAO;

public class DownloadFiles extends HttpServlet 
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		PrintWriter out=resp.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		
		UserDAO userDAO = UserDAO.getInstance();
		AdminDAO adminDAO = AdminDAO.getInstance();
		
		String username = "";
		String userid = "";
		HttpSession session = null;
	
		session = req.getSession();

	    if(session != null)
	    {
	    	username = session.getAttribute("username").toString();
	    }
		
	    System.out.println("User Name :"+username);
	    userid = userDAO.getID(username);
	    
	    HttpSession hs=req.getSession();
	    hs.setAttribute("userid", username);
		
		try 
		{
			rs = adminDAO.getUploadFiles();
			
			if(rs != null)
			{
				req.setAttribute("rs", rs);
				rd=req.getRequestDispatcher("/AllFiles/JSP/User/download_file.jsp?no=0 ");
				rd.forward(req, resp);
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Opps,Error in DownloadFiles Servlet : ");
			e.printStackTrace();
		}
		
		
		
	}
}
