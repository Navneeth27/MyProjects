/**
 * 
 */
package com.action.admin;

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

import com.dao.AdminDAO;
import com.helperclass.Utility;



public class UploadFileList extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out=response.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		
		try
		{
			String submit=request.getParameter("submit").trim();
			
			AdminDAO adminDAO = AdminDAO.getInstance();
			rs = adminDAO.getUploadFiles();
			
			if(submit.equals("get"))
			{
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/uploadfiles.jsp?no=1");
				rd.forward(request, response);
			}
		
			if(submit.equals("Delete"))
			{
				String []chk=request.getParameterValues("chk");
				if(chk==null)
				{
					rs = adminDAO.getUploadFiles();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/uploadfiles.jsp?no=1&no1=1");
					rd.forward(request, response);
				}
				else
				{
					for(int i=0;i<chk.length;i++)
					{
						adminDAO.deleteUploadedFiles(Integer.parseInt(chk[i]));
					}
					rs = adminDAO.getUploadFiles();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/uploadfiles.jsp?no=1&no1=3");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Admin UploadFileList Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Admin UploadFileList Servlet......"+e);
		}
	}
}
