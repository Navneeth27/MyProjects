/**
 @Author : Munna Kumar Singh
 Date : Sep 1, 2012
 File : Logout.java
 Package : com.kumar.action.admin
*/
package com.action.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		RequestDispatcher rd = null;
		String path = null;
		try
		{
			path = req.getContextPath();
			
			rd = req.getRequestDispatcher("/index.jsp?no=5");
			rd.forward(req,resp);
			
			//resp.sendRedirect(path + "/index1.jsp");
			
		} catch (Exception e) 
		{
			System.out.println("Opps Exception in Logout Servlet.");
			e.printStackTrace();
		}
	}
}
