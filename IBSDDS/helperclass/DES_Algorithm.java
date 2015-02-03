
package com.helperclass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.action.dataowner.RandomValue;

public class DES_Algorithm 
{
    static //byte[] buf = new byte[1024];
    byte[] buf = new byte[2048];
    static Cipher ecipher;
    static Cipher dcipher;
    
    public static String DESKeyGeneration()
    {
    	RandomValue rv=new RandomValue();
    	String key=rv.DESKeyValue();
    	
		return key;
    	
    }
    
    public DES_Algorithm(SecretKey key) throws Exception
    {
	    byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
	    ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	    dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	
	    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    }


  public static void encrypt(byte[] in, OutputStream out)  throws Exception
  {
	    out = new CipherOutputStream(out, ecipher);
	
	    int numRead = 0;
	    while ((numRead = in.length) >= 0) 
	    {
	      out.write(buf, 0, numRead);
	    }
	      out.close();
  }

  public static void decrypt(InputStream in, OutputStream out)  throws Exception
  {
	    in = new CipherInputStream(in, dcipher);
	
	    int numRead = 0;
	    while ((numRead = in.read(buf)) >= 0) 
	    {
	       out.write(buf, 0, numRead);
	    }
	       out.close();
  }

  /* Testing The Algorithm */
  
  public static void main(String[] argv) throws Exception 
  {
	  
	 
  	
	    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
	    
	    System.out.println("Key :"+key);
	    
	    DES_Algorithm encrypter = new DES_Algorithm(key);
	    
	    //encrypter.encrypt(new FileInputStream("C:\\a.txt"), new FileOutputStream("C:\\b.txt"));
	   // encrypter.decrypt(new FileInputStream("C:\\b.txt"), new FileOutputStream("C:\\c.txt"));
	    
	    System.out.println("Encription Decreption Process Done Sucessfully.....");
  }

}


