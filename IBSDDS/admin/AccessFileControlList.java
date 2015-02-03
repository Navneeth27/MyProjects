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


public class AccessFileControlList extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out=response.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		boolean result = false;
		
		try
		{
			String submit=request.getParameter("submit").trim();
			
			AdminDAO adminDAO = AdminDAO.getInstance();
			rs = adminDAO.getAccessControls();
			
			if(submit.equals("get"))
			{
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1");
				rd.forward(request, response);
			}
			if(submit.equals("Add"))
			{
				if(Utility.parse1(request.getParameter("add1")).equals("YES"))
				{
					int fileCode = Integer.parseInt(request.getParameter("file").toString());
					int deptCode = Integer.parseInt(request.getParameter("dept").toString());
					int designCode = Integer.parseInt(request.getParameter("designation").toString());
					result = adminDAO.addAcessControl(fileCode, deptCode, designCode);
					if(result)
					{
						rs = adminDAO.getAccessControls();
						request.setAttribute("rs", rs);
						rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=1");
						rd.forward(request, response);
					}
					else
					{
						rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=2&no1=2");
						rd.forward(request, response);
					}
							
				}
				else
				{
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=2");
					rd.forward(request, response);
				}
			}
			
			if(submit.equals("Edit"))
			{
				String []chk=request.getParameterValues("chk");
				if(Utility.parse1(request.getParameter("edit1")).equals("YES"))
				{
					int s[]=new int[4];
					s[0]=Integer.parseInt(request.getParameter("id").toString());
					s[1]=Integer.parseInt(request.getParameter("file").toString());
					s[2]=Integer.parseInt(request.getParameter("dept").toString());
					s[3]=Integer.parseInt(request.getParameter("designation").toString());
					adminDAO.editAccessControls(s);
					rs = adminDAO.getAccessControls();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=2");
					rd.forward(request, response);
				}
				else if(chk==null)
				{
					rs = adminDAO.getAccessControls();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=3");
					rd.forward(request, response);
				}
				else if(chk.length!=1)
				{
					rs = adminDAO.getAccessControls();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=4");
					rd.forward(request, response);
				}
				else if(chk.length==1)
				{
					rs = adminDAO.getFileAccessControlInfo(Integer.parseInt(chk[0]));
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=3");
					rd.forward(request, response);
				}
			}
			
			if(submit.equals("Delete"))
			{
				String []chk=request.getParameterValues("chk");
				if(chk==null)
				{
					rs = adminDAO.getAccessControls();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=3");
					rd.forward(request, response);
				}
				else
				{
					for(int i=0;i<chk.length;i++)
					{
						adminDAO.deleteAccessControls(Integer.parseInt(chk[i]));
					}
					rs = adminDAO.getAccessControls();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/AllFiles/JSP/Admin/accesscontrols.jsp?no=1&no1=5");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Admin AccessFileControlList Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Admin ServerList AccessFileControlList......"+e);
		}
	}
}
