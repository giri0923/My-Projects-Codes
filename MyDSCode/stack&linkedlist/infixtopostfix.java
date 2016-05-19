package com.giri.stack;
import java.io.*;
import java.lang.*;
public class infixtopostfix {
	static stackopcharacter obj=new stackopcharacter();
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
	case '-':
	case '*':
	case '/':
	case ')':evaluate(c);break;
	
	case '(':obj.push(c);break;			
	default:System.out.print(c);				
	}
	
	}
	
	while(!obj.isEmpty())
	{
		System.out.print(obj.pop());
	}
		}//end main

public static void evaluate(char a)
{
	int p=0,pstack=0;
if(a=='+'||a=='-')	
p=1;

if(a=='*'||a=='/')
p=2;




if(obj.isEmpty()||obj.peek()=='(')
{
	obj.push(a);
}
else
		if(a==')')
			{
			while(obj.peek()!='(')
				System.out.print(obj.pop());
			
			obj.pop();
			
}
		else
		{
			
			if(obj.peek()=='(')
				obj.push(a);
			else
			{
				if(obj.peek()=='+'||obj.peek()=='-')	
					pstack=1;

					if(obj.peek()=='*'||obj.peek()=='/')
					pstack=2;
					
					
					if(p>pstack)
						obj.push(a);
					else
					{
						
						if(p<pstack)
						{
			while(!obj.isEmpty()||obj.peek()=='(')
			{
				
				if(p<pstack)
			{
				System.out.print(obj.pop());
				
				
			}
		}obj.push(a);
					}
						else
						{
							System.out.print(obj.pop());
						obj.push(a);
						}
			}
		}


		}}
}//end class



