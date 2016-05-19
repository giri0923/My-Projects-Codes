package com.giri.stack;
import java.io.*;
import java.lang.*;
import java.util.*;

public class stringreverse
{
public static void main(String a[])throws IOException
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
stackopcharacter obj=new stackopcharacter();
System.out.println("enter the string to be reversed");
String s=br.readLine();
String s1="";
int i;
char c;
for(i=0;i<s.length();i++)
{
obj.push(s.charAt(i));
}

while(obj.isEmpty())
{

s1+=obj.pop();

}
System.out.println("reverse is "+s1);
}
}
