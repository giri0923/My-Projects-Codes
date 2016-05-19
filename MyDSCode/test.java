package Ds;
import java.io.*;
import java.lang.*;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String gg="sampletest";
		System.out.println(gg.substring(1,5));
	String g=noX("axxbxx");
	System.out.println(g);
	}
	public static String noX(String str)
	{
		 if(str.length()==0)
			   return str;
			  if(str.charAt(0)=='x')
			    return  noX(str.substring(1));
			  else
			    return str.charAt(0)+noX(str.substring(1));
	
	}

}
