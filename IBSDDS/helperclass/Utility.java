/**
 * 
 */
package com.helperclass;

import java.awt.image.BufferedImage;

import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.imageio.ImageIO;

//import org.apache.commons.net.ftp.FTPClient;

import sun.net.ftp.FtpClient;
import sun.net.www.URLConnection;

//import com.drw.util.RSA_Algorithm;


public class Utility
{
	public static int parse(String str) 
	{ 
		int p=0; 
		if(str==null) 
		{ 
			str="0"; 
		} 
		else if((str.trim()).equals("null")) 
		{ 
			str="0"; 
		} 
		else if(str.equals("")) 
		{ 
			str="0"; 
		} 
		try
		{ 
			p=Integer.parseInt(str); 
		} 
		catch(Exception e) 
		{
			System.out.println("Opp's error is in Utility parse(String str) "+e);
		} 
		return p; 
	}
	public static String parse1(Object str) 
	{ 
		String p=""; 
		if(str==null) 
		{ 
			str=""; 
		} 
		else if(str.equals("")) 
		{ 
			str=""; 
		} 
		try
		{ 
			p=(String)str; 
		} 
		catch(Exception e) 
		{
			System.out.println("Opp's error is in Utility parse1(String str) "+e);
		} 
		return p; 
	}
	public static String getPro(String str) 
	{
		String pro="";
		try
		{
			java.security.ProtectionDomain pd =	Utility.class.getProtectionDomain();
			java.security.CodeSource cs = pd.getCodeSource();
			java.net.URL url = cs.getLocation();
			java.io.File f = new File( url.getFile());
			String path=f.getParent();
			Properties property = new Properties();
			FileInputStream in=null;
			in = new FileInputStream(path+"\\Config.properties");
			System.out.println("9999999999999999 Propperty File Path : " + path+"\\Config.properties");
			property.load(in);
			pro=property.getProperty(str);
			System.out.println("Property to return : "+pro);
		}
		catch(Exception e)
		{
			System.out.println("Opp's error is in Utility getPro(String str) "+e);
		}
		return pro;
	}
	public static Object parse2(Object str) 
	{ 
		Object obj=null;
		if(str!=null) 
			return str;
		else
			return obj; 
	}
	
	
	public static String getDate()
	{
		String date="";
		try
		{
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");
			date = formatter1.format(currentDate.getTime());
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getDate( ): "+ e);
		}
		return date;
	}
	public static String getTime()
	{
		String time="";
		try
		{
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter1=new SimpleDateFormat("HH:mm:ss");
			time = formatter1.format(currentDate.getTime());
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getTime( ): "+ e);
		}
		return time;
	}
	public static String getDay()
	{
		String day="";
		try
		{
			Calendar currentDate = Calendar.getInstance();
			int no=currentDate.get(Calendar.DAY_OF_WEEK);
			if(no==1)
				day="Sunday";
			else if(no==2)
				day="Monday";
			else if(no==3)
				day="TUESDAY";
			else if(no==4)
				day="WEDNESDAY";
			else if(no==5)
				day="THURSDAY";
			else if(no==6)
				day="FRIDAY";
			else if(no==7)
				day="SATURDAY";
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getDay( ): "+ e);
		}
		return day;
	}
	public static BufferedImage readImage(String fileLocation)
	{
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return img;
    }
	public static int[] getARGBPixelData(int pixel)
	{
		int[] a = new int[4];
		try
		{
			int alpha = (pixel >> 24) & 0x000000FF;
			int red = (pixel >> 16) & 0x000000FF;
			int green = (pixel >>8 ) & 0x000000FF;
			int blue = (pixel) & 0x000000FF;
			a[0]=red;
        	a[1]=green;
        	a[2]=blue;
        	a[3]=alpha;
//        	for (int k = 0; k < a.length; k++)
//    			System.out.print("-"+a[k]);
//        	System.out.println();
		}
		catch(Exception e)
		{
			System.out.println("Error is in ImageProcess getARGBPixelData(int pixel) "+e);
		}
		return a;
	}
	public static String [] getBinary(String str)
	{
		String a[]=new String[str.length()];
		for(int i=0;i<str.length();i++)
		{
			a[i]=Integer.toBinaryString(str.charAt(i));
			if(a[i].length()==0 || a[i].length()==1 || a[i].length()==2 || a[i].length()==3 || a[i].length()==4 || a[i].length()==5 || a[i].length()==6 || a[i].length()==7)
			{
				String zero="";
				int how_many=8-a[i].length();
				for(int j=0;j<how_many;j++)
					zero+="0";
				a[i]=zero+a[i];
			}
//			System.out.println(str.charAt(i)+"------->"+a[i]);
		}
		return a;
	}
	public static String getBinary(int no)
	{
		String str=Integer.toBinaryString(no);
		for(int i=0;i<str.length();i++)
		{
			if(str.length()==0 || str.length()==1 || str.length()==2 || str.length()==3 || str.length()==4 || str.length()==5 || str.length()==6 || str.length()==7)
			{
				String zero="";
				int how_many=8-str.length();
				for(int j=0;j<how_many;j++)
					zero+="0";
				str=zero+str;
			}
		}
//		System.out.println("--->"+str);
		return str;
	}
	public static String replace(String str, int index, char replace)
	{     
	    char[] chars = str.toCharArray();
	    chars[index] = replace;
	    return String.valueOf(chars);       
	}
	public static int getOnes(int Imat[][],int width,int height)
	{
		int count=0;
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++) 
            {
				if(Imat[i][j]==1)
					count++;
			}
		}
		return count;
	}
	public static int[][] imageMatrix(BufferedImage image,String key_binary[],int msglength)
	{
		boolean process_status=false;
		boolean flag=false;
		int width = image.getWidth();
		int height = image.getHeight();
		int p_mat[][]=new int[4][4];
		int index=0;//key_binary [] index
		int v=0;//column of img_mat
		int img_mat[][]=new int[width][height];
		for (int i = 0; i < width; i++)
	    {
	         for (int j = 0; j < height; j++) 
	         {
	        	 img_mat[i][j]=0;
	         }
	    }
		for (int i = 0; i < width/2; i++)
		{
			for(int j=0;j<1;j++)
			{
				if(i%2==0)
				{
					p_mat[0][0]=Integer.parseInt(key_binary[index].substring(0,1));
					p_mat[0][1]=Integer.parseInt(key_binary[index].substring(1,2));
					p_mat[1][0]=Integer.parseInt(key_binary[index].substring(2,3));
					p_mat[1][1]=Integer.parseInt(key_binary[index].substring(3,4));
				}
				else
				{
					p_mat[0][0]=Integer.parseInt(key_binary[index-1].substring(4,5));
					p_mat[0][1]=Integer.parseInt(key_binary[index-1].substring(5,6));
					p_mat[1][0]=Integer.parseInt(key_binary[index-1].substring(6,7));
					p_mat[1][1]=Integer.parseInt(key_binary[index-1].substring(7,8));
				}
				img_mat[i][v]=p_mat[0][0];
				img_mat[i][v+1]=p_mat[0][1];
				img_mat[i+1][v]=p_mat[1][0];
				img_mat[i+1][v+1]=p_mat[1][1];
				if(index==key_binary.length)
					flag=true;
			}
			v++;v++;
			if(index!=key_binary.length && i%2==0)
				index++;
			if(flag)//Start reapting 
			{
				flag=false;
				index=0;
			}
			if((msglength*2)==getOnes(img_mat,width,height))//Add no of ones needed to hide message
			{
				process_status=true;
				break;
			}
		}
		if(process_status)
			return img_mat;
		else
			return null;
	}
	
	/* MD5 Algorithm(Starts) */
	
	public static String MD(File source1) throws NoSuchAlgorithmException, FileNotFoundException 
	{
		String output="";
		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream is = new FileInputStream(source1);				
		byte[] buffer = new byte[8192];
		int read = 0;
		try {
			while( (read = is.read(buffer)) > 0) 
			{
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
				System.out.print("Digets:"+md5sum);
			
			System.out.println();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);
			System.out.println("MD5: " + output);
		}
		catch(IOException e) 
		{
			System.out.println("Unable to process file for MD5"+e);
		}
		return output;
	}
	
	/* MD5 Algorithm(Ends) */
	
/* MD5 Algorithm(Starts) */
	
	public static String MD(InputStream inputStream) throws NoSuchAlgorithmException, FileNotFoundException 
	{
		String output="";
		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream is = inputStream;				
		byte[] buffer = new byte[8192];
		int read = 0;
		try {
			while( (read = is.read(buffer)) > 0) 
			{
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
				System.out.print("Digets:"+md5sum);
			
			System.out.println();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);
			System.out.println("MD5: " + output);
		}
		catch(IOException e) 
		{
			System.out.println("Unable to process file for MD5"+e);
		}
		return output;
	}
	
	/* MD5 Algorithm(Ends) */
	
	public static byte[] DS(byte md[], byte pvtkey[])throws Exception
	{
		byte DS[]=null;
		try
		{
			//DS=RSA_Algorithm.createDS(pvtkey,md);
		}
		catch(Exception e)
		{
			System.out.println("Unable to process file for DS :"+e);
			
		}
		return DS;
	}
	public static boolean checkDigitalSignature(byte md[], byte pubkey[], byte[] ds)throws Exception
	{
		boolean flag = false;
		try
		{
			//flag=RSA_Algorithm.checkDS(pubkey,md,ds); 
		}
		catch(Exception e)
		{
			System.out.println("Unable to process file for DS Verification : "+e);
		}
		return flag;
	}
	public static StringBuffer getRC4Enc(StringBuffer inp,StringBuffer key)
	{
		int Sbox[];
		int Sbox2[];
		Sbox=new int[257];
		Sbox2=new int[257];
		int i, j, t, x;
		char temp , k;
		i = 0;j = 0;k = 0;t = 0;x = 0;temp = 0;
		//initialize sbox i
		for( i = 0; i < 256; i++)
		{
			Sbox[i] = i;
		}
		j = 0;
		if(key.length() >0)
		{
			for(i = 0; i < 256 ; i++)
			{
				if(j == key.length())
					j = 0;
				Sbox2[i] = key.charAt(j++);
			}
		}
		j = 0 ;
		for(i = 0; i < 256; i++)
		{
			j = (j + Sbox[i] + Sbox2[i]) % 256;
			temp = (char)Sbox[i];
			Sbox[i] = Sbox[j];
			Sbox[j] = temp;
		}
		i = j = 0;
		for(x = 0; x < inp.length() ; x++)
		{
			i = (i + 1) % 256;
			j = (j + Sbox[i]) % 256;
			temp = (char)Sbox[i];
			Sbox[i] = Sbox[j] ;
			Sbox[j] = temp;
			t = ( Sbox[i] + Sbox[j]) % 256 ;
			k = (char)Sbox[t];
			inp.setCharAt(x, (char)(inp.charAt(x) ^ k));
		}
		return inp;
	 }
	
	public static boolean deleteFile(String ip,String user,String pass,String fname)
	{
		//FTPClient client = new FTPClient();
		boolean flag=false;
		try
		{
			//client.connect(ip);
           //client.login(user, pass);
            String filename = "/Nitin/"+fname;
           // boolean result = client.deleteFile(filename);
            //if (result)
            {
            	System.out.println("File Deleted...");
            	flag=true;
            }
           // else
            	System.out.println("Error While File Delete Process...");
        }
		catch(Exception e)
		{
			System.out.println("OPP's Error is in deleteFile "+e);
		}
		return flag;
	}
	
	public static boolean uploadFile(String ip,String user,String pass,String fileName,String root)
	{
		boolean flag=false;
		try
		{
			StringBuffer sb = new StringBuffer( "ftp://" );
			sb.append( user );
            sb.append( ':' );
            sb.append( pass );
            sb.append( '@' );
            sb.append( ip );
            sb.append( '/' );
            String a="Nitin";
            sb.append( a );
            sb.append( '/' );
            sb.append(fileName );
            sb.append( ";type=i" );
            System.out.println("URL   :"+sb);
            System.out.println("filename in url 1-----"+fileName);
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
           
               URL url = new URL( sb.toString() );
               URLConnection urlc = (URLConnection) url.openConnection();
               System.out.println("-----"+urlc);
               bos = new BufferedOutputStream( urlc.getOutputStream() );
               bis = new BufferedInputStream( new FileInputStream( new File(root+fileName) ) );
               int i;
               while ((i = bis.read()) != -1)
               {
                  bos.write( i );
                  flag=true;
               }
               bis.close();
               bos.close();
        }
		catch(Exception e)
		{
			System.out.println("OPP's Error is in UploadFile "+e);
		}
		return flag;
	}
	public static boolean downloadFile(String ip,String user,String pass,String fileName,String root)
	{
		boolean flag=false;
		try
		{
			File f=new File(root+"/Nitin_Temp");
			if(!f.exists())
				f.mkdir();
			StringBuffer sb = new StringBuffer( "ftp://" );
			sb.append( user );
            sb.append( ':' );
            sb.append( pass );
            sb.append( '@' );
            sb.append( ip );
	        sb.append( '/' );
	        String a="Nitin";
	        sb.append( a );
	        sb.append( '/' );
	        sb.append( fileName );
	        sb.append( ";type=i" );
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
	        URL url = new URL( sb.toString() );
            URLConnection urlc = (URLConnection) url.openConnection();
            bis = new BufferedInputStream( urlc.getInputStream());
            bos = new BufferedOutputStream( new FileOutputStream(root+"/Nitin_Temp/"+fileName) );
            int i;
            while ((i = bis.read()) != -1)
            {
               bos.write( i );
               flag=true;
            }
            bis.close();
            bos.close();
        }
		catch(Exception e)
		{
			System.out.println("OPP's Error is in downloadFile "+e);
		}
		return flag;
	}
	public static boolean writeOnFile(String filename,String msg,String root)
	{
		boolean flag=false;
		try
		{
			PrintWriter pw = null;
			File f=new File(root+"\\Files\\Transactios\\"+filename);
			if(!f.exists())
				flag =f.createNewFile();
			pw = new PrintWriter(new FileWriter(f, true));
			pw.println("");
			pw.println(msg);
			pw.flush();
			pw.close();
			flag=true;
			System.out.println(msg);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return flag;
	}
	
	/* MD5 Algorithm(Starts) */
	public static void main(String asdf[])
	{
		BufferedImage image=Utility.readImage("secretImage.jpg");
		String key="kumar";
		String msg="This is testing...............";
		
//		hideProcess(image,key,msg);
		
//		extractProcess(image,key,msg.length());
		
	}
}
