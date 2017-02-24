package BinaryTree;

public class SizeOfBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		SizeOfBT obj = new SizeOfBT();
		int size = obj.sizeofbinarytree(node.root);
		System.out.println("SIZE OF THE BT IS : " + size);
		//To confirm the addition
	}

	int sizeofbinarytree(BinaryTreeNode root)
	{
		if(root==null)
			return 0;
		else
			return 1 + sizeofbinarytree(root.getLeft()) + sizeofbinarytree(root.getRight());
	}
}
