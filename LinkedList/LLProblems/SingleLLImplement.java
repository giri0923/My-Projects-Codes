package LLProblems;

public class SingleLLImplement {
	node head = null;
	public void insert_begin(int d)
	{
		node newnode = new node();
		newnode.data = d;
		if(head==null)
		{
			newnode.next = null;
			head = newnode;
		}
		else
		{
			newnode.next = head;
			head = newnode;
		}			
	}
	
	public void insert_end(int d)
	{
		node newnode = new node();
		newnode.data = d;
		if(head==null)
		{
			newnode.next = null;
			head = newnode;
		}
		else
		{
			node temp = head;
			while(temp.next!=null)
				temp = temp.next;
			temp.next = newnode;
			newnode.next = null;
		}
	}
	
	public void insert_position(int pos,int d)
	{
		node newnode = new node();
		newnode.data = d;
		int count = 1;
		node temp = head;
		while(count < pos)
		{
			temp=temp.next;
			count++;
		}
		newnode.next = temp.next;
		temp.next = newnode;		
	}
	
	public node deleteBegin()
	{
		if(head == null)
			return null;
		node temp = head;
		head = temp.next;
		return temp;
	}
	
	public node deleteEnd()
	{
		if(head == null)
			return null;
		node temp = head,prev=null;
		while(temp.next!=null)
		{
			prev = temp;
			temp = temp.next;
		}
		prev.next = null;
		return temp;
	}
	
	public node deleteGivenNode(int d)
	{
		if(head == null)
			return null;
		
		node temp = head,prev=null;
		if(head.data == d)
		{
			temp = head;
			head = temp.next;
			return temp;
		}
		while(temp!=null )
		{
			if(temp.data == d)
			{
				prev.next = temp.next;
				return temp;
			}
			else
			{
				prev = temp;
				temp = temp.next;
			}
		}
		
		//Data not found
		return null;
		
	}
	public void display()
	{
		node temp=head;
		while(temp!=null)
		{
			System.out.print(temp.data+"->");
			temp=temp.next;
		}
		System.out.println("");
	}
}
