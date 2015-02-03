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
		   
			rs = AdminDAO.getOwnerProfile(username);
		    
			int no=Integer.parseInt(request.getParameter("no"));
			
			if(no==1)
			{
				rs = AdminDAO.getOwnerProfile(username);
				request.setAttribute("rs",rs);
				RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=3");
				rd.forward(request, response);
			}
			if(no==2)
			{
				int id=Integer.parseInt(request.getParameter("id"));
				System.out.println("ID ----------------- :"+id);
				String pass=request.getParameter("pass");
				String npass=request.getParameter("npass");
				String cpass=request.getParameter("cpass");
				boolean result=adminDAO.checkOwner(username,pass);
				if(result)
				{
					if(npass.equals(cpass))
					{
						result=adminDAO.chnageOwnerPass(id,cpass);
						
						if(result)
						{
							rs = AdminDAO.getOwnerProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=0&no1=2");
							rd.forward(request, response);
						}
						else
						{
							rs = AdminDAO.getOwnerProfile(username);
							request.setAttribute("rs",rs);
							RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=3&no1=3");
							rd.forward(request, response);
						}
					}
					else
					{
						rs = AdminDAO.getOwnerProfile(username);
						request.setAttribute("rs",rs);
						RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=3&no1=2");
						rd.forward(request, response);
					}
				}
				else
				{
					rs = AdminDAO.getOwnerProfile(username);
					request.setAttribute("rs",rs);
					RequestDispatcher rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/profile.jsp?no=3&no1=1");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Data Owner==>ChangePass Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Data Owner==>ChangePass Servlet......"+e);
		}
	}
}
