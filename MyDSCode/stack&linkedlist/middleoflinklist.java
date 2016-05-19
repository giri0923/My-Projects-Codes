package com.giri.stack;

public class middleoflinklist {

static node head=null;
	public static void main(String[] args) {
		insert_end(10);
		insert_end(100);
		insert_end(1000);
		insert_end(20);
		insert_end(200);
		insert_end(2000);
		insert_end(20000);
		//insert_end(100);
	
		//display();
		node temp=head;
		//System.out.println("gg "+head.data);
		
		while(temp!=null&&temp.next!=null)
		{
			if(temp.next.data==20000)
				temp.next=temp.next.next;
			
			else
				temp=temp.next;
				
				
	}
		display();
		
//findmiddle();
	
		
		
		
	}
public static void findmiddle()
{
	node slow=head,fast=head;
	
	int count=1;
	while(fast!=null&&fast.next!=null)
	{
		fast=fast.next.next;
		slow=slow.next;
	count++;
	}
	
	System.out.println("Middle of the linked list is node no."+count+" and the value is "+slow.data);
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
