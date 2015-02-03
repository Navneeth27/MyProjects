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

import com.dao.UserDAO;



public class ChangeUserPassword extends HttpServlet 
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
		    
			int no=Integer.parseInt(request.getParameter("no"));
			
			if(no==1)
			{
				rs = userDAO.getProfile(username);
				request.setAttribute("rs",rs);
				RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=3");
				rd.forward(request, response);
			}
			if(no==2)
			{
				int id=Integer.parseInt(request.getParameter("id"));
				String pass=request.getParameter("pass");
				String npass=request.getParameter("npass");
				String cpass=request.getParameter("cpass");
				boolean result=userDAO.checkUser(username,pass);
				if(result)
				{
					if(npass.equals(cpass))
					{
						result=userDAO.chnagePass(id,cpass);
						
						if(result)
						{
							rs=userDAO.getProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=0&no1=2");
							rd.forward(request, response);
						}
						else
						{
							rs=userDAO.getProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=3&no1=3");
							rd.forward(request, response);
						}
					}
					else
					{
						rs=userDAO.getProfile(username);
						request.setAttribute("rs",rs);
						RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=3&no1=2");
						rd.forward(request, response);
					}
				}
				else
				{
					rs=userDAO.getProfile(username);
					request.setAttribute("rs",rs);
					RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/User/profile.jsp?no=3&no1=1");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in User ChangeUserPassword Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in User ChangeUserPassword Servlet......"+e);
		}
	}
}
