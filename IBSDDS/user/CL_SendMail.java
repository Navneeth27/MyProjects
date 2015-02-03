package com.action.user;

import java.util.Properties;


import javax.mail.*;

import java.util.*;

import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


public class CL_SendMail
{
	
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	 	 
	 public static final boolean sendMail(String emailaddress, final String fromemailid,final String password,String subject,String message, String attachfilepath,String attachfilepath1,String hostname,String portnumber)  
		{
	    	
			System.out.println("Subject "+subject.trim());
			System.out.println("Message "+message.trim());
			System.out.println("From "+fromemailid.trim());
		
			boolean debug = true;
			boolean flag=true;
			
					
			Properties props = new Properties();
			props.put("mail.smtp.host", hostname);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", portnumber);
			props.put("mail.smtp.socketFactory.port", portnumber);
			props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			
			

			Session session = Session.getInstance(props,new javax.mail.Authenticator() 
			{
				protected PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(fromemailid,password);
				}
			});

			session.setDebug(debug);
			
			Message msg = new MimeMessage(session);
			InternetAddress addressFrom=null;
			
			
			
			try 
			{
				addressFrom = new InternetAddress(fromemailid);
				
				msg.setFrom(addressFrom);
				
				InternetAddress addressTo = new InternetAddress();

				
				addressTo.setAddress(emailaddress);
				
				
				msg.setRecipient(Message.RecipientType.BCC, addressTo);
				
				msg.setSubject(subject);
				
				msg.setText(message);
				
				msg.setContent(message, "text/html");
				String [] attachments={attachfilepath,attachfilepath1};
				
				if(attachfilepath!="" && attachfilepath1!="")
				{
					
					System.out.println("********************************************************Filename is "+attachfilepath);
					System.out.println("**********************************************************Filename1 is "+attachfilepath1);
					
				
				 Multipart multipart = new MimeMultipart();
				    BodyPart part1 = new MimeBodyPart();
				    part1.setText(message);
				    multipart.addBodyPart(part1);

				    BodyPart attachment = null; 
				    for (String filename : attachments)
				    {
				      attachment = new MimeBodyPart();
				      DataSource source = new FileDataSource(filename);
				      attachment.setDataHandler(new DataHandler(source));
				      attachment.setFileName(filename);
				      multipart.addBodyPart(attachment);
				    }

				    msg.setContent(multipart);
			
			
			Transport.send(msg);
				}
			}
			catch (AddressException e1) 
			{
						
				flag=false;
			}
		
			catch (MessagingException e1) 
			{
				
					flag=false;
					//e2.printStackTrace();
					//TestLog.writeException(e2);
				
			}
			return flag;
			
			
		}
	 
	 public static final boolean sendPersonalizedMail(String emailid,final String fromemailid,final String password,String subject,String message, String attachfilepath,String hostname,String portnumber,String filename)  
		{
		 
		 			
			System.out.println("Subject ssss"+subject.trim());
			System.out.println("Message mmm"+message.trim());
			System.out.println("From ffff"+fromemailid.trim());
			
			System.out.println("To tttt"+emailid.trim());
			System.out.println("Attached File Path mmm"+attachfilepath.trim());
			System.out.println("Password ffff"+password.trim());
			
		
			boolean debug = true;
			boolean flag=true;
			
					
			Properties props = new Properties();
			props.put("mail.smtp.host", hostname);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", portnumber);
			props.put("mail.smtp.socketFactory.port", portnumber);
			props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			
			Session session = Session.getInstance(props,new javax.mail.Authenticator() 
			{
				protected PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(fromemailid,password);
				}
			});

			session.setDebug(debug);
			Message msg = new MimeMessage(session);
			InternetAddress addressFrom=null;
				
			
		 		
		 		try 
				{
					addressFrom = new InternetAddress(fromemailid);
					
					msg.setFrom(addressFrom);
					
					InternetAddress addressTo=new InternetAddress();
					
					addressTo.setAddress(emailid);
					
					msg.setRecipient(Message.RecipientType.BCC, addressTo);
								
					msg.setSubject(subject);
					
										
					msg.setText(message);
							
					msg.setContent(message, "text/html");
									  
					if(attachfilepath!="")
					{
									
						//System.out.println("Filename is "+attachfilepath);
						
						BodyPart messageBodyPart = new MimeBodyPart();
						//messageBodyPart.setText(message);
						messageBodyPart.setContent(message, "text/html");
					    Multipart multipart = new MimeMultipart();
					    multipart.addBodyPart(messageBodyPart);
					    messageBodyPart = new MimeBodyPart();
					    
					    DataSource source = new FileDataSource(attachfilepath);
					    messageBodyPart.setDataHandler(new DataHandler(source));
					    
					    messageBodyPart.setFileName(filename);
					    multipart.addBodyPart(messageBodyPart);
					    msg.setContent(multipart);
					    
						
					}	
				
				Transport.send(msg);
				
				}
				catch (AddressException e1) 
				{
							
					flag=false;
				}
			
				catch(SendFailedException sendingfailed)
				{
					flag=false;
					//Address[] invalidaddress=sendingfailed.getInvalidAddresses();
					
					//System.out.println("Total Number of Invalid Address "+invalidaddress);
					
				}
				catch (MessagingException e1) 
				{
					
						flag=false;
						//e2.printStackTrace();
						//TestLog.writeException(e2);
					
				}
				
		 	
			return flag;
			
			
		}
	 

}
