/**
 @Author : Munna Kumar Singh
 Date : Sep 4, 2012
 File : GenerateRandomNubberBetween1ton.java
 Package : com.kumar
*/
package com.helperclass;

import java.util.ArrayList;
import java.util.Random;

public class GenerateRandomNubberBetween1ton 
{
	
	public static ArrayList gererateRandomNumberBetween1_to_n(int Start,int End)
	{
		ArrayList list = new ArrayList();
		Random random = new Random();
	    int randomValue = 0;
		 for (int i = 1; i <= End;i++)
		    {
		    	randomValue = showRandomInteger(Start,End,random);
		    	list.add(randomValue);
		    }
		return list;
	}
	
	 private static int showRandomInteger(int Start, int End, Random aRandom)
	 {
	    if ( Start > End ) 
	    {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)End - (long)Start + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + Start);    
	    return randomNumber;
    }
	 
	 /* Testing the Development */
	 
	 public static void main(String[] args) 
		{
			int START = 1;
		    int END = 4;
		    
		    ArrayList list = new ArrayList();
		    
		    list = GenerateRandomNubberBetween1ton.gererateRandomNumberBetween1_to_n(START, END);
		    
		    System.out.println(list);
		}
		
}
