package LLProblems;

public class addDigitsLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLImplement obj = new SingleLLImplement();
		SingleLLImplement obj1 = new SingleLLImplement(2);
		obj.head = addDigits(obj.head,obj1.head);
		obj.display();
	}
	public static node addDigits(node head1,node head2)
	{//Create result list and make sure it is empty
		SingleLLImplement obj2 = new SingleLLImplement();
		obj2.head.next=null;
		obj2.deleteBegin();
		node p1 = head1,p2=head2;
		int carry=0;
		while(p1!=null && p2!=null)
		{
			int sum = p1.data+p2.data+carry;
			if(sum<10)
			{
			carry = 0;
			}
			else
			{
				carry = sum/10;
				sum = sum%10;
			}
			obj2.insert_begin(sum);
			p1= p1.next;
			p2=p2.next;
		}
		if(p1==null&&p2==null)
		{
			if(carry!=0)
				obj2.insert_begin(carry);
		}
		while(p1!=null)
		{ 
			obj2.insert_begin(p1.data+carry);
			carry=0;
			p1=p1.next;
		}
		while(p2!=null)
		{
			obj2.insert_begin(p2.data+carry);
			carry=0;
			p2=p2.next;
		}
		return obj2.head;
	}

}
