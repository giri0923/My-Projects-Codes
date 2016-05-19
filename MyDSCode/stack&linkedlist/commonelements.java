package com.giri.stack;

public class commonelements {

	/**
	 * @param args
	 */
	static node head1=null,head2=null;
	static node temp1,temp2;
	static SingleLLImplement obj1=new SingleLLImplement();
	static SingleLLImplement obj2=new SingleLLImplement();
	static SingleLLImplement obj3=new SingleLLImplement();
	static SingleLLImplement obj4=new SingleLLImplement();
	public static void main(String[] args) {
		obj1.insert_end(10);
		obj1.insert_end(100);
		obj1.insert_end(1000);
		obj1.insert_end(20);
		obj1.insert_end(20000);
		
	
		obj2.insert_end(10);
		obj2.insert_end(25);
		obj2.insert_end(105);
		obj2.insert_end(1000);
		obj2.insert_end(34);
		
		findintersect();
		
		System.out.println("Intersection elements :");
		obj3.display();
		
		findunion();
		
		
		System.out.println("Union elements :");
		obj4.display();
		
	}
	
	public static void findintersect()
	{
		head1=obj1.head;
		head2=obj2.head;
		
		
		for(temp1=head1;temp1!=null;temp1=temp1.next)
		{
		for(temp2=head2;temp2!=null;temp2=temp2.next)
		{
			
			if(temp1.data==temp2.data)
			{
				
				obj3.insert_end(temp1.data);
			}
			
		}
	}
	}
	
	public static void findunion()
	{
		head1=obj1.head;
		head2=obj2.head;
		
		for(temp1=head1;temp1!=null;temp1=temp1.next)
			obj4.insert_end(temp1.data);
		
		
		for(temp2=head2;temp2!=null;temp2=temp2.next)
			{
			if(!(obj4.find(temp2.data)))
				{
						obj4.insert_end(temp2.data);
				}
			}		

	}
	
	

}
