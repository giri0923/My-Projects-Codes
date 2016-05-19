package Ds;

public class PalindromeLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		singleLLOperations obj=new singleLLOperations();
		obj.insertAtEnd(1);
		obj.insertAtEnd(2);
		obj.insertAtEnd(3);
		obj.insertAtEnd(4);
		obj.insertAtEnd(5);
		
		obj.insertAtEnd(4);
		obj.insertAtEnd(3);
		obj.insertAtEnd(2);
		obj.insertAtEnd(1);
		node current=obj.head;
		
		node next=null;
		node temp=obj.head;
		int count=1;
		
		while(temp.next!=null)
		{
			count++;
			temp=temp.next;
		}
		int loop=count/2;
		temp=obj.head;
		int c=0;
		while(c<loop)
		{
		temp=temp.next;	
		c++;
		}
		node prev=temp;
		//System.out.println("Data"+prev.data);
		int loo=0;
		while(loo<count/2)
		{
			next=current.next;
			current.next=prev;
			prev=current;
			current=next;
			loo++;
		}
		
	//	System.out.println(prev.data);
		obj.head=prev;

		//System.out.println("data"+prev.data);
		obj.display();
		temp=obj.head;
		
		 c=0;
		while(c<loop)
		{
		temp=temp.next;	
		c++;
		}
		
		boolean flag=true;
		node compare=obj.head;
		if(count%2==1)
			temp=temp.next;
		while(temp.next!=null)
		{
			//System.out.println("data1"+compare.data);
		//	System.out.println("data2"+temp.data);
			if(!(compare.data==temp.data))
			{
				flag=false;
			}
			temp=temp.next;
			compare=compare.next;
		}
		
		if(!flag)
			System.out.println("Not a palindrome");
		else
			System.out.println("It is a palindrome");
		
		
	}

}
