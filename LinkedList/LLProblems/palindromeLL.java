package LLProblems;

public class palindromeLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		boolean res = palindromeLL(obj.head);
		System.out.println(res);
	}
	
	public static boolean palindromeLL(node head)
	{
		int i = 0;
		node fast = head,slow = head;
		while(fast!=null && fast.next !=null)
		{
			if(i==0)
			{	fast = fast.next;
				i=1;
			}
			else
			{
				fast = fast.next;
				slow = slow.next;
				i=0;
			}
		}	
	   node temp = slow;
	   node head1 = slow.next;
	   slow = head;
	   temp.next =  reveraseLL(head1);
	   node next_pointer = temp.next;
	   while(next_pointer!=null)
	   {
		   if(next_pointer.data==slow.data)
		   {
			   next_pointer = next_pointer.next;
			   slow = slow.next;
		   }
		   else{ return false;}
	   }
	   return true;
	}
	public static node reveraseLL(node head)
	{
		node p1 = head;			//1->2->3->4->5
		node p2 = head.next;
		node temp = null;
		head.next = null;
	//	prev.next = null;
		while(p1!=null && p2!=null)
		{
			temp = p2.next;
			p2.next = p1;
			p1=p2;
			p2 = temp;			
		}
	return p1;
	}
}
