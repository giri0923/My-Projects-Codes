package LLProblems;

public class oddorevennodesLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		//obj.deleteEnd();
		obj.display();
		oddoreven(obj.head);
		
	}
	public static void oddoreven(node head)
	{
		int i = 0;
		node fast = head;
		while(fast!=null && fast.next !=null)
		{
			fast=fast.next.next;
		}
		if(fast==null)
			System.out.println("The LL has even number of nodes");
		else

			System.out.println("The LL has odd number of nodes");
	}
}
