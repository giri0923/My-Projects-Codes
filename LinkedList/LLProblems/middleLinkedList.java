package LLProblems;

public class middleLinkedList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		obj.insert_end(6);
		obj.insert_end(7);
		node res = middleNodeLL(obj.head);
		System.out.println("the middle node of LL is "+res.data);
	}
	public static node middleNodeLL(node head)
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
		return slow;
	}
}
