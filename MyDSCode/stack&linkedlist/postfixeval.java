package com.giri.stack;
import java.io.*;
import java.lang.*;
public class postfixeval {

	static stackmain obj=new stackmain();
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
		case '/':
		case '*':evaluate(c);
			break;
		
		default:obj.push(Character.getNumericValue(c));
			
		}	
			
		
		}//end for
	
	
while(!obj.isEmpty())
		System.out.println("output"+obj.pop());
		
	
		
		}//end main
		
		public static void evaluate(char a)
		{
			int q,w,e,r;
			switch(a)
			{
			case '+':{q=obj.pop()+obj.pop();
			obj.push(q);		
			break;	}

			case '-':{w=obj.pop()-obj.pop();
			obj.push(w);	
			break;}
			
			case '*':{e=obj.pop()*obj.pop();
				obj.push(e);
				break;}
			
			case '/':{r=obj.pop()/obj.pop();
				obj.push(r);
				break;}
			}
		}
		
		
		
		}//end class


