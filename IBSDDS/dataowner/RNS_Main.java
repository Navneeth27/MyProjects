package com.action.dataowner;

import java.util.ArrayList;




public class RNS_Main 
{
	 static int r1=0;
	  static int r2=0;
	  static int r3=0;
	 
	
	
	 public static String Encry(int a,String key1,String key2)
	  {
		 
		  int m1=Integer.parseInt(key1);
		  int m2=Integer.parseInt(key2);
		
		 		  
		 int r1=a%m1;
		 int r2=a%m2;
		
		 System.out.println("str1"+r1);
		 System.out.println("str2"+r2);
		
		   String str=r1+"_"+r2;
		   System.out.println("str"+str);
		   
		return str;
		
		
	  }
	 
	 public static Integer Decrypt(String a,String key1,String key2)
	  {
		  String n[]=a.split("_");
		
		  r1=Integer.parseInt(n[0]);
		  r2=Integer.parseInt(n[1]);
		
		 
		  
		  int m1=Integer.parseInt(key1);
		  int m2=Integer.parseInt(key2);
		  
		  
		  int N=m1*m2;
		  int A1=N/m1;
		  int A2=N/m2;
		 
		  
		  int t1=0;
		  int t2=0;
		 
		  
		  
          for (int i = 0; i<i+1; i++)
          {
             int t=i;
              if ((A1*(t))%(m1) == 1) 
              {
                t1 = t;
              
                break;
              }
          }
       
          for (int i = 0; i<i+1; i++)
          {
             int t=i;
              if ((A2*(t))%(m2) == 1) 
              {
               t2 = t;
              
                break;
              }
          }
          
          System.out.println("00000000000"+A1+" "+t1+" "+r1+" "+A2+" "+t2+" "+r2);
          int e=(A1*t1*r1)+(A2*t2*r2);
          System.out.println("00000000000"+e);
		  int e1=e%N;
		  //String e2=""+e1;
		  System.out.println("00000000000"+e1);
		  
		return e1;
		  
	  }
}
