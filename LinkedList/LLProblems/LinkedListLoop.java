package LLProblems;

public class LinkedListLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		obj.insert_begin(5);
		obj.insert_begin(7);
		obj.insert_begin(9);
		obj.display();
		
		node head = obj.head;
		node temp = obj.head;
		while(temp.next!=null)
			temp = temp.next;
		temp.next = obj.head;
		//obj.display();
		System.out.println(checkLoop(obj.head));
		
	}
	
	public static boolean checkLoop(node head)
	{
		node fast = head,slow=head;
		boolean flag=true;
		while(fast!=null && fast.next!=null)
		{
			if(fast==slow && !flag)
			{
				return true;
			}
			else
			{
				fast = fast.next;
				fast = fast.next;
				slow=slow.next;
				flag = false;
			}
		}
		return false;
		
		
	}
	
	

}
