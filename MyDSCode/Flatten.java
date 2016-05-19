package Ds;

public class Flatten {
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

		obj.insertBelow(10,1);
		obj.insertBelow(20,2);
		obj.insertBelow(30,2);
		obj.insertBelow(100,2);
		obj.insertBelow(40,4);
		obj.insertBelow(50,4);
		obj.insertBelow(60,5);
		obj.insertBelow(70,7);
		obj.insertBelow(80,7);

		obj.displayAll();
		

		node temp=obj.head;
		node temp1=obj.head;
		node current=obj.head;
		node down;
		while(temp1.next!=null)
		{
			temp1=temp1.next;
		}
		node t;
	while(temp.next!=null)
	{
		current=temp;
		while(current!=null && current.down!=null)
		{
			t=current.down.down;
			temp1.next=current.down;
			current.down.down=null;
			current.down=t;
			temp1=temp1.next;
		}
		temp=temp.next;
	}
		obj.display();
	}

}
