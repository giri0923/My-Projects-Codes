package LLProblems;

public class LLDriverProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
SingleLLImplement obj = new SingleLLImplement();
obj.insert_begin(2);
obj.insert_begin(1);
obj.insert_end(3);
obj.display();
obj.insert_position(2, 4);
obj.display();
obj.deleteBegin();
obj.display();
obj.deleteEnd();
obj.display();
System.out.println(obj.deleteGivenNode(4));
obj.display();
	}

}
