package com.giri.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
public class InfixEval {

	/**
	 * @param args
	 */
		
		static stackopcharacter obj=new stackopcharacter();
		static stackopcharacter obj1=new stackopcharacter();
		public static void main(String[] args)throws IOException {
			String s;int i;
			char c;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter String");
		s=br.readLine();
		
		for(i=0;i<s.length();i++)
		{
		c=s.charAt(i);
		switch(c)
		{
		case '+':
		case '*':
		case '-':
		case '/':obj1.push(c);break;
			
		case '(':obj.push(c);break;
		case ')':evaluate(c);break;
			
			default:obj.push(c);
				
		}//end switch
		}//end for
		System.out.println(obj.pop());
		
		}//end main
		
		public static void evaluate(char a)
		{
			int i,j,k,l;
			
					while((obj.peek()!='(')&&!(obj1.isEmpty()))
					{
						switch(obj1.pop())
						{
						case '+':{
							
							int a1=Character.getNumericValue(obj.pop());
							int b1=Character.getNumericValue(obj.pop());
						
							obj.push(Character.forDigit((a1+b1),10));
							
							
						break;}
						
						case '-':	{
							i=Character.getNumericValue(obj.pop());
							j=Character.getNumericValue(obj.pop());
							obj.push(Character.forDigit((j-i),10));
							break;}
						
						case '*':	{
							
							int a2=Character.getNumericValue(obj.pop());
							int b2=Character.getNumericValue(obj.pop());
						
							obj.push(Character.forDigit((a2*b2),10));
							break;}
						
						case '/':	{
							k=Character.getNumericValue(obj.pop());
							l=Character.getNumericValue(obj.pop());
							obj.push(Character.forDigit((l/k),10));
							break;}
						}
					}//end while
					
				}//end if
					
					
		
}//end class
	


