
package com.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AdminDAO;



public class ViewAdminTransaction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String transactionType = "";
		
		RequestDispatcher rd=null;
		ResultSet rs = null;
		
		AdminDAO adminDAO = AdminDAO.getInstance();
		
		transactionType = req.getParameter("tType");
		System.out.println("Transaction Type : " + transactionType);
		
		try 
		{
			
			if(transactionType.equals("Upload"))
			{
				rs = adminDAO.getAdminUploadTransaction();
				req.setAttribute("rs",rs);
				rd=req.getRequestDispatcher("/AllFiles/JSP/Admin/upload_transactions.jsp");
				rd.forward(req, resp);
			}
			if(transactionType.equals("Download"))
			{
				rs = adminDAO.getAdminDownloadTransaction();
				req.setAttribute("rs",rs);
				rd=req.getRequestDispatcher("/AllFiles/JSP/Admin/download_transactions.jsp");
				rd.forward(req, resp);
			}
		} 
		catch (Exception e)
		{
		     System.out.println("Opps, Exception in ViewAdminTransaction Servlet :");
		     e.printStackTrace();
		}
		
		
	}
}
