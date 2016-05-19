package com.giri.stack;

public class findloopnode {
static node head=null;
	
	public static void main(String[] args) {
		node fast=head,slow=head;
		if(fast==slow)
		{System.out.println("Loop exists ");
			slow=head;
			
			while(fast!=slow)
			{
				slow=slow.next;
				fast=fast.next;
			}
			System.out.println(" "+slow.data);
			
		}
	}

}
