package Ds;

public class UnionAndIntersection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		singleLLOperations obj=new singleLLOperations();

		singleLLOperations obj1=new singleLLOperations();
		singleLLOperations obj2=new singleLLOperations();
		singleLLOperations obj3=new singleLLOperations();
		
		obj.insertAtEnd(1);
		obj.insertAtEnd(6);
		obj.insertAtEnd(3);
		obj.insertAtEnd(4);
		obj.insertAtEnd(34);
		obj.insertAtEnd(6);
		obj.insertAtEnd(7);
		obj.insertAtEnd(8);
		

		obj1.insertAtEnd(8);
		obj1.insertAtEnd(11);
		obj1.insertAtEnd(32);
		obj1.insertAtEnd(45);
		obj1.insertAtEnd(51);
		obj1.insertAtEnd(6);
		obj1.insertAtEnd(2);
		obj1.insertAtEnd(84);
		node temp=obj.head;
		node temp1=obj1.head;
		node current=obj.head;
		int swap;
		
		
		sort(obj);
		sort(obj1);
		obj.display();

		obj1.display();
		while(true)
		{
			if(temp.next==null || temp1.next==null)
			{
			break;
			}
			if(temp.data<temp1.data)
			{
				obj2.insertAtEnd(temp.data);
				temp=temp.next;
			}
			else
				if(temp1.data<temp.data)
				{
					obj2.insertAtEnd(temp1.data);
					temp1=temp1.next;
						
				}
				else
				{
					obj3.insertAtEnd(temp.data);

					obj2.insertAtEnd(temp.data);
					temp=temp.next;
					temp1=temp1.next;
				}
			
			
		}
		while(temp.next!=null)
		{
			obj2.insertAtEnd(temp.data);
			temp=temp.next;
		}
		while(temp1.next!=null)
		{
			obj2.insertAtEnd(temp1.data);
			temp1=temp1.next;
		}
		System.out.println("UNION LIST");
		obj2.display();
		System.out.println("INTERSECTION LIST");
		obj3.display();
		
	}
	public static void sort(singleLLOperations obj)

	{
		node head=obj.head;
		node current=obj.head;
		int swap=0;
		node temp=obj.head;
		while(temp!=null)
		{
			while(current!=null)
			{
				if(current.data>temp.data)
				{
					swap=temp.data;
					temp.data=current.data;
					current.data=swap;
			
				}
				current=current.next;
			}
			temp=temp.next;

			current=obj.head;
		}
	}
	
}
