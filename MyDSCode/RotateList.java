package Ds;

public class RotateList {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		singleLLOperations obj=new singleLLOperations();
		
		obj.insertAtEnd(1);
		obj.insertAtEnd(6);
		obj.insertAtEnd(3);
		obj.insertAtEnd(4);
		obj.insertAtEnd(34);
		obj.insertAtEnd(6);
		obj.insertAtEnd(7);
		obj.insertAtEnd(8);

		node head=obj.head;
		node temp=obj.head;
		node temp1=obj.head;
		int k=4,count=0;
		while(temp.next!=null)
		{
			temp=temp.next;
		}
		while(count<k)
		{
			temp.next=temp1;
			count++;
			head=head.next;
			temp1.next=null;
			temp=temp.next;
			temp1=head;
		}
		obj.head=head;
		obj.display();
	}
	

}
