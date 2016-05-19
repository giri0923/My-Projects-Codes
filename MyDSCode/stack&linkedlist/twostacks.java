package com.giri.stack;
import java.io.*;
import java.lang.*;
public class twostacks {
	static int s[]=new int[5];
	static int top1=-1;
	static int top2=s.length;	
public static void push(int id,int a)
{
	try
	{
		if(top1>top2) throw new exceptionclass("hi");
		
	}
	catch(Exception e)
	{
		System.out.println("gg"+e);
	}


if(id==1)
	s[++top1]=a;
	
	else
		s[--top2]=a;

}


public static void pop(int id)
{

if(id==1)
	{
		
			try
		{
				if(top1==-1) throw new exceptionclass("hi");
				else
					System.out.println(s[top1--]);
		}
		catch(Exception e)
		{
			System.out.println("gg"+e);
		}
		
	}
	
else
{
	try
	{
		if(top2==s.length) throw new exceptionclass("hi");
			else
				System.out.println(s[top2++]);
	}
	catch(Exception e)
	{
		System.out.println("gg"+e);
	}
	


	
	
	
}
}
public static boolean isFull(int id)
{
	
	if(id==1)
	{
	if(top1==(s.length/2)-1)
		return true;
	
	else return false;
	
	}
	else
	{
		if(top2==(s.length/2))
			return true;
		else
			return false;
	}
}


public static boolean isEmpty(int id)
{
	if(id==1)
	{
		if(top1==-1)
			return true;
		else
			return false;
	}
	else
	{
		if(top2==s.length)
			return true;
		else
			return false;
	}
	
}
public static int peek(int id)
{
	
	if(id==1)
	return s[top1];
	
	else
		return s[top2];
}


public static void main(String a[])throws IOException
{boolean s3;
		push(1,10);
	push(1,20);
	push(2,5);
	push(2,9);
	
	System.out.println(peek(1));
	System.out.println(peek(2));
	s3=isEmpty(1);
	System.out.println(s3);
	pop(1);
	pop(2);
	pop(1);
	pop(2);
	s3=isEmpty(1);
	System.out.println(s3);
	s3=isEmpty(2);
	System.out.println(s3);
	
	
	
	
	
}
//{
//int i,j,k,l,m,n,o,p;
//String s1,s2,s3,s4,s5,s6;
//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//System.out.println("Enter stack id");
////System.out.println("select 1.push 2.pop 3.peep 4.isFull 5.isEmpty");
//s1=br.readLine();
//j=Integer.parseInt(s1);
//
//if(j==1)
//{
//	
//	System.out.println("select 1.push 2.pop 3.peep 4.isFull 5.isEmpty");
//	s2=br.readLine();
//	k=Integer.parseInt(s2);
//	switch(k){
//	
//	case 1:{
//		System.out.println("Enter data");
//		s3=br.readLine();
//		l=Integer.parseInt(s2);
//		push(1,l);
//		break;
//	}
//	case 2:
//	{
//		pop(1);
//		break;
//	}
//	case 3:
//	{
//		peek(1);
//		break;
//	}
//
//	case 4:
//	{
//		isFull(1);
//		break;
//	}
//	case 5:
//	{
//		isEmpty(1);
//		break;
//	}
//	
//}//end switch
//	
//	
//	
//}//end if
//
//
//
//else
//{
//	
//	
//	
//	System.out.println("select 1.push 2.pop 3.peep 4.isFull 5.isEmpty");
//	s4=br.readLine();
//	m=Integer.parseInt(s4);
//	switch(m){
//	
//	case 1:{
//		System.out.println("Enter data");
//		s5=br.readLine();
//		n=Integer.parseInt(s5);
//		push(2,n);
//		break;
//	}
//	case 2:
//	{
//		pop(2);
//		break;
//	}
//	case 3:
//	{
//		peek(2);
//		break;
//	}
//
//	case 4:
//	{
//		isFull(2);
//		break;
//	}
//	case 5:
//	{
//		isEmpty(2);
//		break;
//	}
//	
//}//end switch	
//	
//	
//	
//	
//	
//	
//	
//}//end else
//}
}


