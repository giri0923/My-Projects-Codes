package Ds;
public class ReverseLL {

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
node current=obj.head;
node prev=null;
node next=null;
while(current!=null)
{

	next=current.next;
	current.next=prev;
	
	prev=current;
	current=next;
}
obj.head=prev;
System.out.println( prev.data);
obj.display();
}
}
