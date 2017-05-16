package LLProblems;

public class ReverseLinkedList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SingleLLImplement obj = new SingleLLImplement();
		node res = reveraseLL(obj.head);
		obj.head = res;
		obj.display();
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
