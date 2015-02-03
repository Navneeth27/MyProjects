/**
 * 
 */
package com.action.dataowner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;




public class DataOwnerLogin extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		
		try
		{
			String user=request.getParameter("username");
			String pass=request.getParameter("password");
			
			HttpSession session = request.getSession(true);
			
			
			AdminDAO adminDAO=AdminDAO.getInstance();
			boolean result=adminDAO.checkDataOwner(user,pass);
			if(result)
			{
				RequestDispatcher rd=null;
				session.setAttribute("username",user);
				rd=request.getRequestDispatcher("/AllFiles/JSP/DataOwner/dataownerhome.jsp");
				rd.forward(request,response);
			}
			else
			{
				response.sendRedirect("index.jsp?no=4");
			}
		}
		catch(Exception e)
		{
			System.out.println("********* Exception In DataOwnerLogin Servlet ********\n");
			e.printStackTrace();
		}
	}
}
