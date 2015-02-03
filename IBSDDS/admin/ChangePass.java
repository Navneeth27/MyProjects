/**
 * 
 */
package com.action.admin;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;



public class ChangePass extends HttpServlet 
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
		    
		    AdminDAO adminDAO = AdminDAO.getInstance();
			rs = AdminDAO.getProfile(username);
		    
			int no=Integer.parseInt(request.getParameter("no"));
			
			if(no==1)
			{
				rs = AdminDAO.getProfile(username);
				request.setAttribute("rs",rs);
				RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/profile.jsp?no=3");
				rd.forward(request, response);
			}
			if(no==2)
			{
				int id=Integer.parseInt(request.getParameter("id"));
				String pass=request.getParameter("pass");
				String npass=request.getParameter("npass");
				String cpass=request.getParameter("cpass");
				boolean result=adminDAO.checkAdmin(username,pass);
				if(result)
				{
					if(npass.equals(cpass))
					{
						result=adminDAO.chnagePass(id,cpass);
						
						if(result)
						{
							rs=adminDAO.getProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/profile.jsp?no=0&no1=2");
							rd.forward(request, response);
						}
						else
						{
							rs=adminDAO.getProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/profile.jsp?no=3&no1=3");
							rd.forward(request, response);
						}
					}
					else
					{
						rs=adminDAO.getProfile(username);
						request.setAttribute("rs",rs);
						RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/profile.jsp?no=3&no1=2");
						rd.forward(request, response);
					}
				}
				else
				{
					rs=adminDAO.getProfile(username);
					request.setAttribute("rs",rs);
					RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/profile.jsp?no=3&no1=1");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Admin ChangePass Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Admin ChangePass Servlet......"+e);
		}
	}
}
