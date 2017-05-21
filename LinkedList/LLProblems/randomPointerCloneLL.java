package LLProblems;

import java.util.HashMap;

public class randomPointerCloneLL {
	static node2 head = null;
	static node2 head1 = null;
	int glob = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleLLforRandom obj = new SingleLLforRandom();		
		System.out.println("ORIGINAL LIST");
		obj.display();
		System.out.println("CLONED LIST");		
		SingleLLforRandom obj_result = new SingleLLforRandom(2);
		obj_result.head = randomCloneLL(obj.head);
		obj_result.display();
	}

	public static node2 randomCloneLL(node2 head)
	{
	HashMap<node2,node2> hm = new HashMap<node2,node2>();
	SingleLLforRandom obj1 = new SingleLLforRandom(2);	
	node2 temp = head;
	while(temp!=null)
	{
	obj1.insert_end(temp.data);	
	temp = temp.next;
	}
	temp = head;
	node2 temp1 = obj1.head;
	while(temp!=null)
	{
		hm.put(temp, temp1);
		temp=temp.next;
		temp1=temp1.next;	
	}
	temp1 = obj1.head;
	temp = head;
	while(temp1!=null)
	{
		temp1.random = hm.get(temp.random);
		temp = temp.next;
		temp1=temp1.next;
	}
	return obj1.head;
	}
}
