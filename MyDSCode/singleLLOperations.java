package Ds;

public class singleLLOperations {
node obj=new node();

node head=null;
public void insertAtEnd(int data)
{
	
	node newNode=new node();
	newNode.data=data;
if(head==null)
{
	head=newNode;
	newNode.next=null;
}
else
{
	node temp=head;
	while(temp.next!=null)
		temp=temp.next;
	

	
	temp.next=newNode;
	newNode.next=null;
}
	
}
public void insertBelow(int data,int pos)
{
	
	node newNode=new node();
	newNode.data=data;
if(head==null)
{
	head=newNode;
	newNode.down=null;
}
else
{
	int count=1;
	node temp=head;
	while(count<pos)
	{
	temp=temp.next;	
	count++;
	}
	while(temp.down!=null)
		temp=temp.down;
	temp.down=newNode;
	newNode.down=null;
	newNode.next=null;
}
	
}

public void insertAtBegin(int data)
{
	node newNode=new node();
	newNode.data=data;
	if(head==null)
	{
		
		head=newNode;
		newNode.next=null;
	}
	else
	{
		newNode.next=head;
		head=newNode;
	}
}

public void insertAtPos(int data,int pos)
{
	int c=1;
	node newNode=new node();
	newNode.data=data;
	node temp=head;
	int ct=0;
	while(temp.next!=null)
	{
		ct++;
		temp=temp.next;
	}
	temp=head;
	if(pos>ct)
	{
		System.out.println("Wrong Position Requested. LL is small");
		return;
	}
	
	else
	{
		while(c<pos)
	{
		c++;
		temp=temp.next;
		
	}
		
		newNode.next=temp.next;
		temp.next=newNode;
	}
}
public void display()
{
	node temp=head;
	while(temp!=null)
	{
		System.out.println(temp.data);
		temp=temp.next;
	}
}

public void displayAll()
{
	node temp=head;
	node down=head;
	while(temp!=null)
	{
		System.out.print("\n"+temp.data+" -> (");
		down=temp.down;
		while(down!=null)
		{
			System.out.print(down.data+",");
			down=down.down;
			
		}

		System.out.print(") \n");
		temp=temp.next;
	}
}
public boolean find(int a)
{
node temp=head;	
int count=1;
while(temp!=null)
{
	if(temp.data==a)
		{
		System.out.println("found ");
				return true;}
	
	else
	{
		
		temp=temp.next;
		count++;
	}
	}
return false;
}
public void deleteAtEnd()
{
	node temp=head;
	node temp1=head;
	while(temp.next!=null)
	{
		temp1=temp;
		temp=temp.next;
		
	}
	
	temp1.next=null;
	temp=temp1;
	
	
}
public void deleteAtBegin()
{
	node temp=head.next;
	head=temp;
	
}
public void deleteAtPos(int pos)
{
	node temp=head;
	
	node temp1=head;
	int c=1;
	while(c<pos)
	{
		c++;
		temp1=temp;
		temp=temp.next;
		
	}
	temp1.next=temp.next;
	
	
	
}

}
