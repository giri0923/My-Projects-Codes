package BinaryTree;

public class CheckIfTreesAreMirrors {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		binaryTreeImplement node1 = new binaryTreeImplement();
		CheckIfTreesAreMirrors obj = new CheckIfTreesAreMirrors();
		boolean result = obj.AreMirrorBT(node.root,node1.root);
		
		System.out.println("Are they mirrors ?: "+result);
	}
	boolean AreMirrorBT(BinaryTreeNode root,BinaryTreeNode root1)
	{
		if(root==null && root1==null)
			return true;
		if((root==null && root1!=null)|| (root!=null && root1==null))
			return false;	
		return AreMirrorBT(root.getLeft(),root1.getRight())&&AreMirrorBT(root.getRight(),root1.getLeft()) &&root.getData()==root1.getData();
				
	}
}
