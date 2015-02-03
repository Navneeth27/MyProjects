package com.action.dataowner;

import java.math.BigInteger;
import java.util.Random;

public class RandomValue {
	
	               /*Random Value Generation*/
	
	public BigInteger KeyValue()
	{
		
		int values[] = new int[] { 467,479,487,491,499,503,509,521,523,541};
		Random random = new Random();
		
		int num=random.nextInt(values.length);
		//int n=random(values);
		//int m=random(values);
		//System.out.println("Ramdom Position Of Array :" +num +" Ramdom Array Value Of prime  : "+values[num]);
		return BigInteger.valueOf(values[num]);
		
	}
	
	public String  DESKeyValue()
	{
		
		String values[] = new String[] { "123abc45","678abc91","222aes55","999sea77","888lll55","444ccc45","333kkk45","449jjj45"};
		Random random = new Random();
		
		int num=random.nextInt(values.length);
		//int n=random(values);
		//int m=random(values);
		//System.out.println("Ramdom Position Of Array :" +num +" Ramdom Array Value Of prime  : "+values[num]);
		return values[num];
		
	}
	
}
