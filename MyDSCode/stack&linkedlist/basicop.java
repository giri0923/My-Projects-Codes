package com.giri.stack;
import java.io.*;
import java.lang.*;
import java.util.*;
public class basicop {

	public static void main(String ar[])throws IOException
	{
		BufferedReader Dis = new BufferedReader (new InputStreamReader(System.in));
	    
		
		System.out.println("Please enter the maxize of array");
		String s=Dis.readLine();
		int len=Integer.parseInt(s);
		stackmain obj=new stackmain();
		String s1,s2,s3;
		int j,k,l,y;
		do
		{
			
			System.out.println("select 1.push 2.pop 3.peep 4.isFull 5.isEmpty");
		s1=Dis.readLine();
		j=Integer.parseInt(s1);
			switch(j){	
		case 1:{System.out.println("enter number");
		s3=Dis.readLine();
		k=Integer.parseInt(s3);
		obj.push(k);
			break;}
			case 2:{
				l=obj.pop();
				System.out.println("popped element is"+l);
				
				break;
			}
			case 3:{
				System.out.println("peep element is"+obj.peek());
				
				break;}
			case 4:{
				System.out.println("is full??"+obj.isFull());
				
				break;}
			case 5:{
				System.out.println("is empty??"+obj.isEmpty());
				break;}
			
			default:System.out.println("wrong option");
					break;
		}
			
		System.out.println("continue??1.press any key other than 1 else press 1");

		s2=Dis.readLine();
		y=Integer.parseInt(s2);
		}while(y==1);	
			
			
			
			
	}
	
	
}
