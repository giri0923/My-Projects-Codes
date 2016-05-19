package com.giri.stack;
import java.io.*;

public class nthnodefromlast {

	static node head=null;
	public static void main(String[] args)throws IOException {
		
String s;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter the number");
s=br.readLine();
int x=Integer.parseInt(s);
		
insert_end(10);
insert_end(100);
insert_end(1000);
insert_end(20);
insert_end(200);
insert_end(2000);
insert_end(20000);
insert_end(30);
findn(x);
	}
	
	public static void findn(int x)
	{
		
		node prev=head;
		node temp=head;
		
int count=1;
while(count!=x)
{
	count++;
	temp=temp.next;
}
	
while(temp.next!=null)
{
	temp=temp.next;
	prev=prev.next;
}

System.out.println(x+" node from last has "+prev.data+" as value");
	}

	
	public static void insert_end(int a)
	{
		node n=new node();
		n.data=a;
		if(head==null)
		{
			n.next=null;
			head=n;
		}//end if
		else
		{
node temp=head;
while(temp.next!=null)
{
	temp=temp.next;
}//end while

temp.next=n;
n.next=null;
		}//end else
		
		
	}

	

}


