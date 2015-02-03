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



public class EditUserProfile extends HttpServlet 
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
		    
			int no=Integer.parseInt(request.getParameter("no"));
			
			UserDAO userDAO = UserDAO.getInstance();
			
			if(no==1)
			{
				rs=userDAO.getProfile(username);
				if(rs.next())
				{
					rs=userDAO.getProfile(username);
					request.setAttribute("rs",rs);
					RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=2");
					rd.forward(request, response);
				}
				else
					response.sendRedirect(request.getContextPath()+"/AllFiles/JSP/User/profile.jsp?no=1");

			}
			if(no==2)
			{
				String [] s=new String [3];
				s[0] =request.getParameter("id");
				s[1]=request.getParameter("name");
				s[2]=request.getParameter("email");
				//s[3]=request.getParameter("phone");
				//s[4]=request.getParameter("add");
				boolean result=userDAO.editProfile(s);
				if(result)
				{
					rs=userDAO.getProfile(username);
					request.setAttribute("rs",rs);
					RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=0&no1=1");
					rd.forward(request, response);
				}
				else
					response.sendRedirect(request.getContextPath()+"/AllFiles/JSP/User/profile.jsp?no=1");
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in User EditUserProfile Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in User EditUserProfile Servlet......"+e);
		}
	}
}
