package com.action.dataowner;

import java.math.BigInteger;

import com.property.PropertyBag;
import com.dao.DAO;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class RNS 
{
	static BigInteger m1;
	static  BigInteger m2;
	static String RNSkey;
	
	static BigInteger M = new BigInteger("1", 10);
	
	static BigInteger a1;
	static BigInteger a2;
	
	
	static BigInteger one = new BigInteger("1", 10);
	
	static BigInteger t1;
	static BigInteger t2;
	
	RNS()
	{
		Generation();
	}
	  
	public static String Generation()
	{
		RandomValue r = new RandomValue();

		m1 = r.KeyValue();
		 m2= r.KeyValue();
		 
		 RNSkey=""+m1+"~"+m2;
		
		/*System.out.println("M1 :"+m1);
		System.out.println("M2 :"+m2);
		//System.out.println("RNSKey :"+RNSkey);
		
		 M=M.multiply(m1);
		 M=M.multiply(m2);
		
		 
		 a1=M.divide(m1);
		 a2=M.divide(m2);
		
		 
		 BigInteger T;
		 System.out.println(" a1 :"+a1+" a2 :"+a2);
		 for (int i = 0; true; i++)
         {
			 T = new BigInteger(Integer.toString(i), 10);
             if (a1.multiply(T).mod(m1).compareTo(one) == 0) 
             {
              t1 = T;
               System.out.println(" T1:"+t1);
               break;
             }
          
         }
		 
		 for (int i = 0; true; i++)
         {
			 T = new BigInteger(Integer.toString(i), 10);
             if (a2.multiply(T).mod(m2).compareTo(one) == 0) 
             {
              t2 = T;
               System.out.println(" T1:"+t2);
               break;
             }
          
         }*/
		 
		 return RNSkey;
	}
		 
	public static void main(String[] args)
	{
		RNS rns=new RNS();
		
		String s = rns.Generation();
		
		System.out.println(s);
		
		
		
	}
	
}
