package Ds;



public class SingleLLTestClass {

	
	public static void main(String[] args) {
singleLLOperations obj=new singleLLOperations();
obj.insertAtBegin(10);
obj.insertAtBegin(100);
obj.insertAtBegin(1000);
obj.insertAtBegin(10000);
obj.insertAtBegin(20000);
obj.insertAtBegin(30000);

obj.insertAtPos(15, 3);
obj.display();
obj.deleteAtPos(3);

//obj.insert_position(4,20);
//obj.insert_begin(2);
//obj.insert_begin(20);
//obj.insert_end(30);
//obj.insert_end(300);	
	
	obj.display();
	
	boolean b=obj.find(10);
	
	//obj.delete(20);
	
	//obj.display();
	
	}

}
