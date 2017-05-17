package LLProblems;

public class merge2sortedlists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//2 different constructors to create 2 separate lists
		SingleLLImplement obj = new SingleLLImplement();		
		SingleLLImplement obj1 = new SingleLLImplement(2);
		obj.head = merge2sortedLL(obj.head,obj1.head);
		obj.display();
	}
	public static node merge2sortedLL(node head1,node head2)
	{
		SingleLLImplement obj2 = new SingleLLImplement();
		obj2.head.next=null;
		obj2.deleteBegin();
		node p1 = head1,p2=head2;
		while(p1!=null && p2!=null)
		{
			if(p1.data<p2.data)
			{
				obj2.insert_end(p1.data);
				p1=p1.next;
			}
			else{
				obj2.insert_end(p2.data);
				p2=p2.next;}
		}
		
		while(p1!=null)
		{
		obj2.insert_end(p1.data);
		p1 = p1.next;
		}
		while(p2!=null)
		{
		obj2.insert_end(p2.data);
		p2 = p2.next;
		}
		return obj2.head;
	}
}
