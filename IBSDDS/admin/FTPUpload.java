/**
 * 
 */
package com.action.admin;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.action.dataowner.CloudSelection;
import com.action.dataowner.Process;
import com.action.dataowner.RNS;
import com.dao.AdminDAO;
import com.dao.DAO;
import com.dao.UserDAO;
import com.helperclass.*;

import com.sun.org.apache.xml.internal.serializer.SerializerTrace;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;



public class FTPUpload extends HttpServlet
{
	private static String username="";
	private static int userid=0;
	
	
	public void service(HttpServletRequest req,HttpServletResponse res)throws IOException
	{
		   RequestDispatcher rd = null;
		   String uploadSubject = "";
		   int cloudId =0,ownerId=0;
		   int totalNumberOfClouds = 0;
		   
		   int c_id=0;
		   
		   String srcFilePath = "";
		   String destFilePath = "";
		   String destFilePath1 = "";
		   
		   int numofCloud;
		   
		   
		   UserDAO userDAO = UserDAO.getInstance();
		   AdminDAO adminDAO = AdminDAO.getInstance();
		   
		   HttpSession session = req.getSession();  
		   
		   if(session != null)
			{
			  username = (String) session.getAttribute("username");
			  uploadSubject = session.getAttribute("subject").toString();
			}
		   
		   
	       if ( session.getAttribute( "waitPage" ) == null ) 
	       {  
	    	   session.setAttribute( "waitPage", Boolean.TRUE );  
	    	   PrintWriter out = res.getWriter();  
	    	   out.println( "<html><head>" );  
	    	   out.println( "<title>Please Wait...</title>" );  
	       	   out.println( "<meta http-equiv=\"Refresh\" content=\"0\">" );  
	    	   out.println( "</head><body bgcolor=''>" );  
	    	   out.println( "<br><br><br>" );
	    	   out.print( "<center><img src='AllFiles/Images/status_processing.gif'></img><br><br>");
	    	   out.println( "Please Do not press Back or Refresh button.......<br>  " );
	    	   out.println("<font color='white' size='5'>");
	    	   out.println( "File Uploading is in Process......  " );
	    	   out.println("</font><br>");
	    	   out.println( "Please wait....</h1></center");  
	    	   out.close();  
	       }  
	       else 
	       { 
	    	    session.removeAttribute( "waitPage" );  
				try
				{
					PrintWriter out=res.getWriter();
					res.setContentType("text/html");
					
					String path=req.getParameter("path");
					System.out.println("Source ::> "+path);
					String filename=req.getParameter("fname");
					System.out.println("File Name ::> "+filename);
					
					String localFilePath = req.getRealPath("") +"\\Files\\Upload\\"+filename;
				    
					byte databy[]=ConversionProcess.toByteArray(new File(localFilePath));
					
					String FilePath = req.getRealPath("") +"\\Files\\text.txt";
					
					FileOutputStream fileOut3 = new FileOutputStream(FilePath);
					fileOut3.write(databy);
					fileOut3.flush();
					fileOut3.close();
					
					
					/* Uploading File On Cloud (Starts)*/
					
					ArrayList list= UserDAO.getCloud();
					
					
					String ftpserver = list.get(0).toString();
			        String ftpusername = list.get(1).toString();
			        String ftppassword = list.get(2).toString();
			        
			      /*  //Uploading Encrypted File To Cloud 
			        File file1=new File(localFilePath);
			        totalNumberOfClouds = adminDAO.getTotalNumberOfClouds();
			        
			        // Cloud Selection Process //
			        
			        Connection con=null;
					Statement st=null;
					ResultSet rs=null;
					
					String c_url="";
					String c_username="";
					String c_Password="";
					
					
					  int val=1;
					
					 DAO dao=DAO.getInstance();
					 con=dao.connector();
					
					numofCloud=adminDAO.getTotalNumberOfClouds();
					System.out.println("No Of Cloud :"+numofCloud);
					
					String sql="update m_cloud set c_flag=1 where c_id=1";
					
						st=con.createStatement();
						st.executeUpdate(sql);
						
						String sql1="select *from m_cloud where c_flag=1";
						Statement st1=con.createStatement();
						rs=st1.executeQuery(sql1);
						while(rs.next())
						{
							c_id=rs.getInt(1);
							c_url=rs.getString(2);
							c_username=rs.getString(3);
							c_Password=rs.getString(4);
						}

						System.out.println("C ID 1 -------------- "+c_id);
						System.out.println("C URL 1 -------------- "+c_url);
						System.out.println("C Name 1 -------------- "+c_username);
						System.out.println("C Password 1 -------------- "+c_Password);
						
						
							String sql="update m_cloud set c_flag=0 where c_id=1";
							st=con.createStatement();
							st.executeUpdate(sql);
							
							
								val = val+c_id;
								
								if(val > numofCloud)
								{
									val=1;
								}
								else{
									
									System.out.println("Val ---------- "+val);
								
								Statement st2=con.createStatement();
								String sql2="update m_cloud set c_flag=0 where c_id='"+c_id+"'";
								st2.executeUpdate(sql2);
								System.out.println("SQL 1 -------------- "+sql2);
								Statement st3=con.createStatement();
								String sql3 = "update m_cloud set c_flag=1 where c_id='"+val+"'";
								
								st3.executeUpdate(sql3);
								System.out.println("SQL 2 ------"+sql3);
								
								}*/
			        
			        			String od = session.getAttribute("username").toString();
								String dirToUploadFile="Cloud_IdentityBased/"+od;
								
						  // Cloud Selection Process End //
			        	
			        
			      /* Encrypting the file (Starts) */
			        
								 c_id =UserDAO.getCloud1();

								System.out.println("C ID 1 -------------- "+c_id);
								
			        UserDAO userDao=UserDAO.getInstance();
			        String key1="",key2="";
			           byte[] input_DES=null;
			        	srcFilePath = localFilePath;
			        	destFilePath = req.getRealPath("") +"\\Files\\Upload\\enc_"+filename;
			        	
			        	
			        	// RNS Encryption Details //
			        	
			        	String RNSKey=userDAO.getupRNSKeyFromDB(username);
			        	
			        	String[] rnsKey1=RNSKey.split("~");
			        	
			        	for(int s=0;s<rnsKey1.length;s++)
			        	{
			        		key1=rnsKey1[0];
			        		key2=rnsKey1[1];
			        	}
			        	
			        	
			    		byte[] d=ConversionProcess.toByteArray(new File(FilePath));
			    		
			    		
			        	
			        	String RNS_EncryptedData=Process.encryptMessage1(d, key1, key2);
			        	
			        	System.out.println("  Rns EncryptedData : "+RNS_EncryptedData);
			        	
			      
						FileOutputStream fileOut1 = new FileOutputStream(destFilePath);
						fileOut1.write(RNS_EncryptedData.trim().getBytes());
						fileOut1.flush();
						fileOut1.close();
			        	
			        	// DES Encryption Details //
						
						System.out.println("User Name "+username);
						
						String DESKey=userDAO.getupDESKeyFromDB(username);
						DesEncryption encryption=new DesEncryption(DESKey);
					    
						String encrypted = encryption.encrypt(RNS_EncryptedData.trim());
						System.out.println("-----------------------------"+encrypted);
						String final_encry = req.getRealPath("") +"\\Files\\Upload\\enc1_"+filename;
						System.out.println("  ------------- final_encry :"+final_encry);
						
					
						 
			        /* Encrypting the file (Ends) */
						DesEncryption encrypter = new DesEncryption(DESKey);
						System.out.println(encrypted);
						String decrypted = encrypter.decrypt(encrypted);
						
						System.out.println(" DecEncrypted  : "+decrypted);
			       
			        File file=new File(final_encry);
			     //   byte des_by[]=ConversionProcess.toByteArray(file);
			        FileOutputStream fileOut2 = new FileOutputStream(final_encry);
			        
					fileOut2.write(encrypted.trim().getBytes());
					fileOut2.flush();
					fileOut2.close();
			        
    				FileUpload.upload(ftpserver,ftpusername,ftppassword,filename,file,dirToUploadFile);
    				
					/* Uploading File On Cloud (Ends)*/
	        		/*String s= sb.toString();
	        		String a[] =s.split(":");
	        		String uploadedClouds = a[1];
	        		String clouds[] = uploadedClouds.split(",");
	        		
	        		System.out.println("7777777777777777777777777777777");
	        		System.out.println("Uploaded Clouds :");
	        		System.out.println(Arrays.toString(clouds));*/
	        		
	        		//String url="ftp://dhsinformatics.com/";
	        		
					
					/* Adding the upload Transaction details (Start)*/
    				
    				
					
					boolean flag = false;
					int dotPos = filename.lastIndexOf(".");
				    String extension = filename.substring(dotPos);
				    String fileType=extension;
					
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat formatter1=new SimpleDateFormat("HH:mm:ss");
					String date = formatter.format(currentDate.getTime());
					String time = formatter1.format(currentDate.getTime());
					
					date = date + "  " + time;
					ownerId = adminDAO.getDataOwnerId(username);
					
					flag = adminDAO.addUploadTransaction(filename,fileType,date,uploadSubject,c_id,ownerId);
					
					flag = true;
					
					/* Adding the upload Transaction details (End)*/
					
					/* Displaying Success Message  */
					if(flag)
					{
						//rd=req.getRequestDispatcher("/Resources/JSP/User/uploadSupport_file.jsp?no=2&file_name="+filename+"");
						rd=req.getRequestDispatcher("/AllFiles/JSP/Admin/select_file.jsp?no=1&file_name="+filename+"&cloud="+c_id+"");
						rd.forward(req,res);
					}
					else
					{
						//rd=req.getRequestDispatcher("/Resources/JSP/User/uploadSupport_file.jsp?no=3");
						rd=req.getRequestDispatcher("/AllFiles/JSP/Admin/select_file.jsp?no=0&no1=1");
						rd.forward(req,res);
					}
					
				}
				catch(Exception e)
				{
					System.out.println("\n ******** Exception In FTPUpload Servlet : \n");
					e.printStackTrace();
				}
		
	}}
}
