package com.giri.stack;

public class AlternateSwap {
	static SingleLLImplement obj1=new SingleLLImplement();
	
	static node head=null;
	public static void main(String[] args) {
		obj1.insert_end(1);
		obj1.insert_end(2);
		obj1.insert_end(3);
		obj1.insert_end(4);
		obj1.insert_end(5);
		obj1.insert_end(10);
		
		//System.out.println("head.data" + obj1.head.data);
	obj1.head=swap(obj1.head);
	
	obj1.display();
		
		
	}
public static node swap(node head)
{
	node temp=head,curr=null,new_head=null;
	
	
	while(true)
	{
		
		if(temp!=null&&temp.next!=null)
		{
			curr=temp.next;
			temp.next=temp.next.next;
			curr.next=temp;
			if(new_head==null)
			new_head=curr;
			
		}
		else
			break;
		
		temp=temp.next;
	}

	return new_head;
}
}
