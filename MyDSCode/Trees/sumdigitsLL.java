package BinaryTree;

public class sumdigitsLL {
	static SingleLLImplement l1=new SingleLLImplement();

	static SingleLLImplement l2=new SingleLLImplement();
	static SingleLLImplement l3=new SingleLLImplement();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		l1.insert_end(1);
		l1.insert_end(2);
		l1.insert_end(3);
		
		l2.insert_end(4);
		l2.insert_end(5);
		l2.insert_end(8);
		l2.insert_end(2);
	addTwoNumbers(l1.head,l2.head);
	display(l3.head);
	}
	public static void display(node n)
	{
		node temp=n;
		while(temp!=null)
		{
			System.out.println(temp.data);
			temp=temp.next;
		}
	}
public static void addTwoNumbers(node l1, node l2) {
        
	int k=0,n1=0,n2=0,carry=0;
	node temp1=l1,temp2=l2;
	while(temp1!=null &&temp2!=null)
	{
		if(temp1==null )
			temp1.data=0;
		if(temp2==null)
			temp2.data = 0;
		n1=temp1.data;
		n2=temp2.data;
		if(n1==0&&n2==0){
			l3.insert_end(carry);
			break;}
		int sum=(n1+n2)+carry;
		if(sum >=10)
		{
			sum = sum%10;
			carry = 1;
		}
		else
			carry = 0;
	//	System.out.println(sum);
		l3.insert_end(sum);
		temp1=temp1.next;temp2=temp2.next;
		//carry=0;	
		
	}
	if(carry!=0)
		l3.insert_end(carry);
	
	
	//return l3.head;
    }

}
