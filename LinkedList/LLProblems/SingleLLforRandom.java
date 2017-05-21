package LLProblems;

public class SingleLLforRandom {
	node2 head = null;
	public SingleLLforRandom() {
		// TODO Auto-generated constructor stub
		insert_end(1);
		insert_end(2);
		
		insert_end(3);
		insert_end(4);
		insert_end(5);
		
		node2 temp = head;
		temp.random = temp.next;
		temp.next.random = temp.next.next;
		temp.next.next.random = temp.next.next.next;
		temp.next.next.next.random =temp.next.next.next.next;
		temp.next.next.next.next.random =temp;
		temp.next = head.next;	
	}
	public SingleLLforRandom(int d)
	{
		
	}
	public void insert_end(int d)
	{
		
		node2 newnode = new node2();
		newnode.data = d;
		if(head==null)
		{
			newnode.next = null;		
			head = newnode;
		
		}
		else
		{
			node2 temp = head;
			while(temp.next!=null)
				temp = temp.next;
			temp.next = newnode;
			newnode.next = null;
		}
	}
	public void display()
	{
		node2 temp=head;
		while(temp!=null)
		{
			System.out.print("data "+temp.data);
			System.out.print("random node data " +temp.random.data);
			System.out.println("___________________________");
			temp=temp.next;
		}
		System.out.println("");
	}
}
