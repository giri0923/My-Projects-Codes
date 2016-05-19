package com.giri.stack;

public class SingleLLImplement {

	public node head=null;
	
	public void insert_begin(int a)
	{
		
		node n=new node();
		n.data=a;
		n.next=head;
		head=n;
		
	}
	
	public void insert_end(int a)
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
	
	public void insert_position(int pos,int a)
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
	
	public void display()
	{
		node temp=head;
		while(temp!=null)
		{
			System.out.println(temp.data);
			temp=temp.next;
		}
	}
	
	
	public boolean find(int a)
	{
	node temp=head;	
	int count=1;
	while(temp!=null)
	{
		if(temp.data==a)
			{
			//System.out.println("found at "+count);
					return true;}
		
		else
		{
			
			temp=temp.next;
			count++;
		}
		}
	return false;
	}
	
	public void delete(int a)
	{
		node temp=head;
		node prev=head;
		
		while(temp.next!=null)
		{
			if(temp.data==a)
			{
				if(prev==temp)
					head=temp.next;
				else
				prev.next=temp.next;
				
			}//end if
			
			else
				prev=temp;
				temp=temp.next;
		}//end while
		
		
	}

}
