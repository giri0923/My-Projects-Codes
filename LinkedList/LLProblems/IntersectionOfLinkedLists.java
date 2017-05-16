package LLProblems;

public class IntersectionOfLinkedLists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		SingleLLImplement obj1 = new SingleLLImplement();
		node temp = obj1.head;
		while(temp.next!=null)
			temp = temp.next;
		temp.next = obj.head.next.next;	
		node res = intersectLL(obj.head, obj1.head);
		System.out.println("lists intersect at "+res.data);
	}

	public static node intersectLL(node head1,node head2)
	{
		
		int lenLL1=0;
		int lenLL2=0;
		int diff=0;
		node temp1= head1,temp2 = head2;
		while(temp1!=null)
		{
			temp1 = temp1.next;
			lenLL1++;
		}
		while(temp2!=null)
		{
			temp2 = temp2.next;
			lenLL2++;
		}
		diff = Math.abs(lenLL2-lenLL1);
		temp1= head1;temp2 = head2;
		node big = null,small = null;
		// Determine which list is big
		if(lenLL1>lenLL2)
			{big = head1;small = head2;}
		else 
			{big = head2;small = head1;}
		
		for(int i=0;i<diff;i++)
		{
			big = big.next;
		}
		while(big!=null&&big.next!=null)
		{
			if(big.next==small.next)
			{
				return big;
			}
			else
			{
				big = big.next;
				small= small.next;
			}
		}
		return null;
	}
}
