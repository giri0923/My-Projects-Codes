package com.giri.stack;

public class tortoiseandhare {

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
		
		node temp=head;
		
		boolean f;
		while(temp.next!=null)
		{
			temp=temp.next;
		}
		
		temp.next=head.next.next;

f=findloop();
if(!f)
	System.out.println("no loop in the linked list");
	
	

	}
	
	public static boolean findloop()
	{
		
		node fast=head;
		node slow=head;
		while(fast!=null && fast.next!=null)
		{
				fast=fast.next.next;
				slow=slow.next;
			
				//System.out.println(slow.data);
			if(fast==slow)
			{
				int count=1;
				slow=head;
				
				while(fast!=slow)
				{
					slow=slow.next;
					fast=fast.next;
					count++;
				}
				System.out.println("loop exists at position "+count+" having value "+slow.data);
				return true;
			}
		}
		return false;
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
