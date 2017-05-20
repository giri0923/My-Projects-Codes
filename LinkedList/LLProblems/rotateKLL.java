package LLProblems;

public class rotateKLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		obj.head = rotateLL(obj.head,4);
		obj.display();
	}
	public static node rotateLL(node head,int k)
	{
		node temp = head;
		int i=1;
		while(i<k)
		{
			temp = temp.next;
			i++;
		}
		node next_ptr = temp;
		while(temp.next!=null)
			temp = temp.next;
	    temp.next = head;
		head = next_ptr.next;
	    //temp.next = null;
		next_ptr.next = null;
		return head;
	}
}
