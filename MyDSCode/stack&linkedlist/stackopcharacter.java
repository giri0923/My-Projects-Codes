package com.giri.stack;
import java.io.*;
import java.lang.*;
public class stackopcharacter {
	char s[]=new char[30];
	int top=-1;
	
public void push(char a)
{		s[++top]=a;

	
}
public char pop()
{
	if(top!=-1)

	return s[top--];

	else
	{
		System.out.println("Stack empty");
		return 0;
	}
}

public boolean isEmpty()
{
	if(top==-1)
		return true;
	else 
		return false;
}
public char peek()
{
	return isEmpty()?'\n':s[top];
}

}

