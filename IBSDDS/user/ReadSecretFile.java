/**
 * 
 */
package com.action.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;

import com.dao.UserDAO;
import com.helperclass.DesEncryption;
import com.helperclass.Utility;
import com.helperclass.Verify_SecretKey;





public class ReadSecretFile extends HttpServlet
{
	int fid=0;
	javax.swing.Timer access_timer;
	String userid="";
	String filename="", server="",user="",pass="",dirToCreate="",delete_ouput_path="";
	String dwn_file_path="",decrpt_file_path="";
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
			{
		
		
		
		String dest ="", dest1="";
		RequestDispatcher rd = null;
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		HttpSession hs = request.getSession();

		
		userid = hs.getAttribute("UserId").toString();
		fid = Integer.parseInt(hs.getAttribute("fileId").toString());
		

		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
		try {

			List fileItems = servletFileUpload.parseRequest(request);

			FileItem file = (FileItem) fileItems.get(0);
			
						
			// Write input File into Upload_Files //
			
			String fileName = request.getRealPath("") + "/Files/ReadKey/"+ file.getName();
			
			OutputStream outputStream = new FileOutputStream(fileName);
			InputStream inputStream = file.getInputStream();

			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1)
			{
				outputStream.write(buffer, 0, readBytes);
			}
			outputStream.close();
			inputStream.close();
						
			
			if (file.getName() != null )
			{
				
				// Read file //
				
				
				String data1 = ReadIdentyTocken.readfile(fileName);
				
				System.out.println("Secret file :"+data1);
				
				String key1="";
				String key2="";
				String rns_des_key="";
				
				
				
				String domain_code="",sdomain_code="";
				
				int userId=0,deptCode1=0,deptCode2=0,designCode1=0,designCode2=0;
				UserDAO userDAO1 = UserDAO.getInstance();
				
				key1=userDAO1.getRNSKeyFromDB1();
				key2=userDAO1.getDESKeyFromDB1();
				
				// Decrypt The Secret Key File //
				
				
				
				rns_des_key=""+key1+"#"+key2;
				
				DesEncryption decryption=new DesEncryption(rns_des_key);
			    
				String decrypted = decryption.decrypt(data1);
				
				System.out.println("Decrypted File :"+decrypted+"File Id :"+fid);
				
				boolean f = Verify_SecretKey.getAccessCode(decrypted,fid);
				
				 if(f)
				    {
				    	  response.sendRedirect(request.getContextPath()+"/Download?fileid="+fid);
				    }
				    else
				    {
				    	//request.setAttribute("rs", rs);
						rd=request.getRequestDispatcher("/AllFiles/JSP/User/download_file.jsp?no=0&no1=1");
						rd.forward(request,response);
						
				    	/*rs = adminDAO.getUploadFiles();
				    	
				    	 Adding Download Transaction To Database(Starts)
		            	
		            	transactionStatus = "Denied";
		            	userDAO.addDownloadTransaction(Utility.getDate(),Utility.getTime(),u_code,fileId,dcode,sdcode, transactionStatus);
		        	
		                Adding Download Transaction To Database(Ends)	
						
						if(rs != null)
						{
							request.setAttribute("rs", rs);
							rd=request.getRequestDispatcher("/AllFiles/JSP/User/download_file.jsp?no=0&no1=1");
							rd.forward(request,response);
						}*/
				    }
				    
				
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	

	
}
