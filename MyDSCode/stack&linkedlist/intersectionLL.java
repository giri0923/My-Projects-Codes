package com.giri.stack;

public class intersectionLL {

	static node head=null;
	static node head1=null;
	public static void main(String[] args) {
		node temp=head,temp1=head1;
		
		
		int count=0;
//		intersectionLL obj=new intersectionLL();
//		intersectionLL obj1=new intersectionLL();
//		
		SingleLLImplement obj=new SingleLLImplement();
		SingleLLImplement obj1=new SingleLLImplement();
		
		obj.insert_end(10);
		obj.insert_end(100);
		obj.insert_end(1000);
		obj.insert_end(20);
		obj.insert_end(200);
		obj.insert_end(2000);
		obj.insert_end(20000);
		obj.insert_end(100);
	
		obj1.insert_end(10);
		obj1.insert_end(100);
		obj1.insert_end(1000);
		
		while(count<3)
		{
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
