package com.giri.stack;

public class insertsortedLL {
	static node head=null;
	static SingleLLImplement obj=new SingleLLImplement();
	public static void main(String[] args) {
		

		insert_end(10);
		insert_end(100);
		insert_end(1000);
		insert_end(10000);
		insert_end(20000);
		insert_end(30000);
		insert_end(40000);
		//obj.insert_position(4,20);
		
		
		insert_value(500);
		
	display();
	}
	
	public static void insert_value(int a)
	{
		int count=0;
		node temp=head;
		while(a>temp.data)
		{
			count++;
			temp=temp.next;
		}
		insert_position(count,a);
		
	}
	public static void insert_position(int pos,int a)
	{
		node n=new node();	
		node temp=head;
		n.data=a;
		int count=1;
		while(count!=pos)
		{
			//System.out.println(temp.data);
			temp=temp.next;
			count++;
		}
		n.next=temp.next;
		temp.next=n;
		
		
	}
	
	public static void display()
	{
		node temp=head;
		while(temp!=null)
		{
			System.out.println(temp.data);
			temp=temp.next;
		}
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
