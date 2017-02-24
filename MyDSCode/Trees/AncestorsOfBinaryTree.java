package BinaryTree;

public class AncestorsOfBinaryTree {
	public static int target = 8;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		AncestorsOfBinaryTree obj = new AncestorsOfBinaryTree();
		boolean result = obj.AncestorsOfBinaryTree(node.root);
		System.out.println(result);
	}
	
	
	boolean AncestorsOfBinaryTree(BinaryTreeNode root)
	{
		if(root==null)
			return false;
	
		if(root.data==target || root.data==target)
			return true;
		
		if(AncestorsOfBinaryTree(root.getLeft())||AncestorsOfBinaryTree(root.getRight()))
		{
			System.out.println(root.data);
			return true;
		}
		return false;
	}

}
