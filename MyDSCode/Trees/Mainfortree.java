package Trees;

public class Mainfortree {
	public static int count=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
TreeImplement obj=new TreeImplement();
obj.insertIntoTree(10);
obj.insertIntoTree(9);
obj.insertIntoTree(8);
obj.insertIntoTree(7);
obj.insertIntoTree(16);
obj.insertIntoTree(5);
obj.insertIntoTree(4);
obj.insertIntoTree(3);
//obj.displaypre(obj.root);
//boolean t=obj.search(obj.root, 16);
//if(t)
//System.out.println("Found");
//else
	//System.out.println("lost");
	int size=obj.SizeOfTreeNonrecursive(obj.root);
//int size=obj.sizeoftree(obj.root);
System.out.println("size"+size);
	}

}
