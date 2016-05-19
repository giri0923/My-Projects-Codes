package Ds;

public class LoopInLinkedList {
static node head=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		singleLLOperations obj=new singleLLOperations();
		obj.insertAtEnd(10);
		obj.insertAtEnd(100);
		obj.insertAtEnd(1000);
		obj.insertAtEnd(20);
		obj.insertAtEnd(200);
		obj.insertAtEnd(2000);
		obj.insertAtEnd(20000);
		obj.insertAtEnd(100);
		head=obj.head;
		node temp=head;
		boolean f;
		while(temp.next!=null)
		{
			temp=temp.next;
		}
		
		temp.next=head.next.next;

f=findloop();
if(f==true)
	System.out.println("loop in the linked list");
	
	

	}

	public static boolean findloop()
	{
		node slow=head,fast=head;
boolean f=false;
		
		if(head.next==null)
			return false;
		
		else
		{
			
			while(fast!=null && fast.next!=null)
			{
				slow=slow.next;
				fast=fast.next.next;
				if(slow==fast)
					{
					f=true;
					System.out.println("hell");
					break;
					}
				
				
					}
			if(f==true)
				return true;
			else
				return false;
		}
		
		
		
	}
}
