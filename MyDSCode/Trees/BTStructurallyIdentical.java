package BinaryTree;

public class BTStructurallyIdentical {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		binaryTreeImplement node1 = new binaryTreeImplement();
		BTStructurallyIdentical obj = new BTStructurallyIdentical();
		boolean result = obj.BTStructurallyEqual(node.root,node1.root);
		System.out.println(result);
	}
	
	boolean BTStructurallyEqual(BinaryTreeNode root,BinaryTreeNode root1)
	{
		if(root==null && root1==null)
			return true;
		if((root==null && root1!=null)|| (root!=null && root1==null))
				return false;		
		return BTStructurallyEqual(root.getLeft(),root1.getLeft())&&BTStructurallyEqual(root.getRight(),root1.getRight());
		
	}
}
