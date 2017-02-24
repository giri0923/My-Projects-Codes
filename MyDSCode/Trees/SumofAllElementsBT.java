package BinaryTree;

public class SumofAllElementsBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		SumofAllElementsBT obj = new SumofAllElementsBT();
		int result = 	obj.SumofAllElements(node.root);
		System.out.println(result);
	}
	int SumofAllElements(BinaryTreeNode root)
	{
	if(root == null)
		return 0;
	
	return root.data + SumofAllElements(root.getLeft()) + SumofAllElements(root.getRight());		
	}
}
