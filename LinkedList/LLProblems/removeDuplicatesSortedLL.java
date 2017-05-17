package LLProblems;

public class removeDuplicatesSortedLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		obj.head = removeDupSortedLL(obj.head);
		obj.display();
	}
	public static node removeDupSortedLL(node head)
	{
		node temp = head;
		while(temp.next!=null)
		{
			if(temp.data==temp.next.data)
			{
				temp.next = temp.next.next;
			}
			else
				temp = temp.next;
		}
		return head;
	}
}
