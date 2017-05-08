package LLProblems;

public class nthnodefromend {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		node result = nthnodelast(obj.head,3);
		System.out.println(result.data);
	}
	public static node nthnodelast(node head,int n)
	{
		node fast = head,slow = head;
		int count = 1;
		while(count < n)
		{
			fast = fast.next;
			count++;
		}
		while(fast.next!=null)
		{
			fast = fast.next;
			slow = slow.next;
		}
		return slow;
		
	}

}
