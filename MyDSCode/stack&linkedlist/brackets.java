package com.giri.stack;

import java.io.*;
import java.lang.*;
import java.util.*;

public class brackets {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter the sequence string:Accepted formats - (,[,{,},],)");	
String s=br.readLine();
int i,f;
stackopcharacter obj=new stackopcharacter();
char ch,ch1;
if(s.length()>0)
{
	
for(i=0;i<s.length();i++)
{
	ch=s.charAt(i);
	System.out.println(ch);
	switch(ch)
	{
	case '(':{obj.push(ch);
	break;}
		
	case '[':{obj.push(ch);
	break;}
		
	case '{':{obj.push(ch);
	break;}
		
	case ')':
	
	case ']':
	
	case '}':{if(!obj.isEmpty())
	{
		
		if(obj.pop()=='(' && !(ch==')')||obj.pop()=='{' && !(ch=='}')||obj.pop()=='[' && !(ch==']'))
		{
			System.out.println("wrong sequence");
			
		}
		
		
		
		break;	
	}
		//break;	
	}
	
	default:break;
	}
	
	}}
	}}





	
	


