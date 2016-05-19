package Ds;

public class AlternateSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		singleLLOperations obj=new singleLLOperations();

		singleLLOperations obj1=new singleLLOperations();
		singleLLOperations obj2=new singleLLOperations();
		
		obj.insertAtEnd(1);
		obj.insertAtEnd(2);
		obj.insertAtEnd(3);
		obj.insertAtEnd(4);
		obj.insertAtEnd(5);
		obj.insertAtEnd(6);
		obj.insertAtEnd(7);
		obj.insertAtEnd(8);
		node head=obj.head;
		node head1=obj1.head;
		node head2=obj2.head;
		node curr=obj.head;
		int count=0;
		while(curr!=null)
		{
			if(count%2==0)
			{
				obj1.insertAtEnd(curr.data);
			}
			else
			{
				obj2.insertAtEnd(curr.data);
			}
			count++;
			curr=curr.next;
		}

		System.out.println("FIRST LIST");
		obj1.display();
		System.out.println("SECOND LIST");
		obj2.display();
		
	}

}
