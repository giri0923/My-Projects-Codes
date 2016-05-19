package com.giri.stack;

public class MergeList {

	
	public static void main(String[] args) {
		 node head1=null,head2=null;
		 
		 SingleLLImplement obj1=new SingleLLImplement();
		 SingleLLImplement obj2=new SingleLLImplement();
		 SingleLLImplement obj3=new SingleLLImplement();
		 
		 
		 obj1.insert_end(10);
			obj1.insert_end(20);
			obj1.insert_end(30);
			obj1.insert_end(40);
			obj1.insert_end(50);
			
			obj2.insert_end(7);
			obj2.insert_end(12);
			obj2.insert_end(25);
			obj2.insert_end(29);
			obj2.insert_end(45);
			head1=obj1.head;
			head2=obj2.head;
			node temp1=head1,temp2=head2;
		 while(temp1!=null||temp2!=null)
		 {
			 if(temp1==null||temp2==null)		
				 break;
			 
			 
			 if(temp1.data<temp2.data)
			 {
				 obj3.insert_end(temp1.data);
				 temp1=temp1.next;
			 }
			 
			 else
			 {
				 obj3.insert_end(temp2.data);
				 temp2=temp2.next;
			 }
			 
		 }
		 
		 
		 while(temp1!=null)
		 {
			 obj3.insert_end(temp1.data);
			 temp1=temp1.next;
		 }
		 
		 while(temp2!=null)
		 {
			 obj3.insert_end(temp2.data);
			 temp2=temp2.next;
		 }
		 
		 obj3.display();
		 

	}

}
