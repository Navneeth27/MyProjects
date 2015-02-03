/**
 * 
 */
package com.action.user;

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


public class UserProfile extends HttpServlet 
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
			
		    UserDAO userDAO = UserDAO.getInstance();
			rs = userDAO.getProfile(username);
			if(rs.next())
			{   
				rs = userDAO.getProfile(username);
				request.setAttribute("rs",rs);
				RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=0");
				rd.forward(request, response);
			}
			else
				response.sendRedirect(request.getContextPath()+"/Files/JSP/User/profile.jsp?no=1");
			
		}
		catch(Exception e)
		{
			System.out.println("Oops's Error is in UserProfile Servlet :");
			e.printStackTrace();
			out.println("Oops's Error is in UserProfile Servlet......"+e);
		}
	}
}
