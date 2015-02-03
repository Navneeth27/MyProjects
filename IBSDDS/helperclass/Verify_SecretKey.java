package com.helperclass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.action.user.ReadIdentyTocken;
import com.dao.UserDAO;

public class Verify_SecretKey {
	
	public static boolean getAccessCode( String decrypted, int fileId)
	{

		String domain_de="";
		String e_date="";
		
		String domain_code1="";
		String sdomain_code1="";
		
		String key1="";
		String key2="";
		String rns_des_key="";
		
		
		
		String domain_code="",sdomain_code="";
		
		int userId=0,deptCode1=0,deptCode2=0,designCode1=0,designCode2=0;
		UserDAO userDAO1 = UserDAO.getInstance();
		
		/*key1=userDAO1.getRNSKeyFromDB1();
		key2=userDAO1.getDESKeyFromDB1();
		
		// Decrypt The Secret Key File //
		
		String path = "C://SecretKeys/"+fileName;
		
		rns_des_key=""+key1+"#"+key2;
		String data=ReadIdentyTocken.readfile(path);
		
		DesEncryption decryption=new DesEncryption(rns_des_key);
	    
		String decrypted = decryption.decrypt(data);
		
		System.out.println("Decrypted File :"+decrypted);*/
		
		
		
		String[] d_file=decrypted.split("@");
		
		int length = d_file.length;
		System.out.println("Length File :"+length);
		
		for(int i=0;i<d_file.length;i++)
		{
			domain_code = d_file[0];
			 
			 sdomain_code=d_file[1];
			 e_date=d_file[2];
			// rns_des_key = d_file[3];
		}
		
		System.out.println("domain_code :"+domain_code+"sdomain_code :"+sdomain_code+"e_date :"+e_date+"rns_des_key :"+rns_des_key);
		
		boolean f = Verify_SecretKey.checkAccess(Integer.parseInt(domain_code),Integer.parseInt(sdomain_code),fileId,e_date);
		System.out.println("Status :"+f);
		
		return f;
		
	}
	
	public static boolean checkAccess(int d_code,int sd_code,int fileid,String edate)
	{
		boolean flg = false;
		UserDAO userDAO1 = UserDAO.getInstance();
		
		int acc_dcode=0;
		int acc_sdcode=0;
		
		acc_dcode=userDAO1.getAccess_Code(fileid);
		acc_sdcode=userDAO1.getAccess_DCode(fileid);
		
		System.out.println("Access Domain Code :"+acc_dcode);
		System.out.println("Access SubDomain Code :"+acc_sdcode);
		
		// Date and Time Function //
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		
		String date = formatter.format(currentDate.getTime());
		
		if((d_code == acc_dcode) && (sd_code == acc_sdcode))
		{
			System.out.println("User Can to Download File----");
			flg=true;
			
		}
		else
		{
			System.out.println("User Can not Download File----");
			//System.out.println("Domain Code Is Mismatch----");
		}
		return flg;
		
		
		
		
	}

}
