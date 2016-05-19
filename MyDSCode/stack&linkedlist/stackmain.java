package com.giri.stack;
import java.io.*;
import java.lang.*;
public class stackmain {
	int s[]=new int[8];
	int top=-1;
	
public void push(int a)
{
	
	if(top!=s.length-1)
s[++top]=a;
	else
		System.out.println("stack full");

}
public int pop()
{
	if(top!=-1)
	return s[top--];

	else
	{
		System.out.println("Stack empty");
		return 0;
	}
}
public boolean isFull()
{
	if(top==s.length)
		return true;
	else 
		return false;
}


public boolean isEmpty()
{
	if(top==-1)
		return true;
	else 
		return false;
}


public int peek()
{
	return s[top];
}

}

