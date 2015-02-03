/**
 * 
 */
package com.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;
import com.dao.UserDAO;
import com.helperclass.Utility;


public class UserTransactionList extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out=response.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		String username = "";
		int userId = 0;
		
		try
		{
			HttpSession session = request.getSession();  
		    username = (String) session.getAttribute("username");
			
			String submit=request.getParameter("submit").trim();
			
			UserDAO userDAO = UserDAO.getInstance();
			userId = userDAO.getID1(username);
			rs = userDAO.getDownloadTransaction(userId);
			
			if(submit.equals("get"))
			{
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/AllFiles/JSP/User/user_transaction.jsp");
				rd.forward(request, response);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Admin UserTransactionList Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Admin UserTransactionList Servlet......"+e);
		}
	}
}
