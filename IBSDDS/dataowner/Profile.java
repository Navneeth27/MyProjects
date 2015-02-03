/**
 * 
 */
package com.action.dataowner;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;
import com.dao.UserDAO;


public class Profile extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException 
	{
		PrintWriter out=response.getWriter();
		ResultSet rs = null;
		String username = "";
		HttpSession session = null;
		try
		{
			session = request.getSession();

		    if(session != null)
		    {
		    	username = session.getAttribute("username").toString();
		    	
		    }
		    
		    System.out.println("User :"+username);
			
		    AdminDAO adminDAO = AdminDAO.getInstance();
			rs = AdminDAO.getOwnerProfile(username);
			if(rs.next())
			{   
				rs = AdminDAO.getOwnerProfile(username);
				request.setAttribute("rs",rs);
				RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=0");
				rd.forward(request, response);
			}
			else
				response.sendRedirect(request.getContextPath()+"/AllFiles/JSP/DataOwner/profile.jsp?no=1");
			
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Data Owner==>Profile Servlet :");
			e.printStackTrace();
			out.println("Opps's Error is in Data Owner==>Profile Servlet......"+e);
		}
	}
}
