package Ds;

public class DeleteAlternateNodes {

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
		obj.insertAtEnd(9);
		node head=obj.head;
		node prev=obj.head;
		node current=prev.next;
		int count=1;
		node temp=obj.head;
		while(temp.next!=null)
		{
			count++;
			temp=temp.next;
		}
		System.out.println(count);
		
		
		while(current!=null)
		{
			prev.next=current.next;
			prev=current.next;
			//if(prev.next!=null)
			current=prev.next;			
			if(count%2==0)
			{
				if(current.next==null)
				{
					prev.next=null;
					break;
				}
			}
		}
		obj.display();
		
	}

}
