package com.giri.stack;

public class oddorevennumberofnodes {
	static node head=null;
	public static void main(String[] args) {
		
		insert_end(10);
		insert_end(100);
		insert_end(1000);
		insert_end(20);
		insert_end(200);
		insert_end(2000);
		insert_end(20000);
		insert_end(100);
	
		display();
oddeven();
	}
	
	public static void oddeven()
	{
		node temp=head;
		
		while(temp!=null&&temp.next!=null)
		{
			temp=temp.next.next;
		}
		
		if(temp==null)
			System.out.println("even no of nodes");
		else
			System.out.println("odd no of nodes");
		
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
