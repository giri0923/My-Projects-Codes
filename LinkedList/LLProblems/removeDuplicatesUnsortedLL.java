package LLProblems;
import java.util.*;
public class removeDuplicatesUnsortedLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		obj.head = removeDupunsortedLL(obj.head);
		obj.display();
	}
	public static node removeDupunsortedLL(node head)
	{
		node prev=head;
		node temp = head;
		HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
		while(temp!=null)
		{
			if(hm.containsKey(temp.data))
				{
				prev.next = temp.next;
				temp = prev.next;
				}
			else 
			{
				hm.put(temp.data,1);
				prev= temp;
			temp = temp.next;
			}
		
		}
		return head;
	}
	

}
