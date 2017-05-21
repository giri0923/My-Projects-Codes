package LLProblems;

public class JosephusCircle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		node temp = obj.head;
		while(temp.next!=null)
			temp = temp.next;
		temp.next = obj.head;		
	
		System.out.println(JosephusCircleLL(obj.head,3));
	}
	public static int JosephusCircleLL(node head,int k)
	{
		node temp = head,prev=null;int i;
		while(head.next!=null && head.next!=head)
		{	
			
			i = 1;
			while(i<k)
			{
				prev = temp;
				temp = temp.next;
				i++;
			}
			if(head == temp)
			{
				head = head.next;
				prev.next = head;
				temp = prev.next;
			}
			else{
			//node store = temp;
			prev.next = temp.next;
			temp = prev.next;}
		}
		return head.data;
		
	}
}
