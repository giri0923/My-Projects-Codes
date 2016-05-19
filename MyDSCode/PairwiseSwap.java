package Ds;

public class PairwiseSwap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		singleLLOperations obj=new singleLLOperations();
		obj.insertAtEnd(1);
		obj.insertAtEnd(2);
		obj.insertAtEnd(3);
		obj.insertAtEnd(4);
		obj.insertAtEnd(5);
		obj.insertAtEnd(6);
		obj.insertAtEnd(7);
		obj.insertAtEnd(8);
		node head=obj.head;
		node current=head.next;
		node prev=head;
		int count=0;
		node t=null;
		boolean flag=true;
		while( current!=null)
		{
			prev.next=current.next;
			current.next=prev;
			//System.out.println("prev"+prev.data+"curr"+current.data );
			if(count==0)
			{
				obj.head=current;
				count++;
			}
		//	System.out.println(t.next.data +"t-next");

			t=prev;
		//	System.out.println(t.data +"t");
			prev=current.next;
			if(prev==null)
				break;
			
			current=prev.next;

			t.next=current;
			
		}
		obj.display();
	}

}
