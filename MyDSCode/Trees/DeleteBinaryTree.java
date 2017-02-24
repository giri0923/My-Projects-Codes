package BinaryTree;

public class DeleteBinaryTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		DeleteBinaryTree obj = new DeleteBinaryTree();
		if(node.root==null)
			{
			System.out.println("TREE ALREADY DELETED");
			return;
			}
		obj.DeleteBT(node.root);
		obj.postorder(node.root);
		//System.out.println (node.root.data);
	}

	void DeleteBT(BinaryTreeNode root)
	{
		if(root==null)
			return;
		DeleteBT(root.getLeft());
		DeleteBT(root.getRight());
		root=null;
	}
	
	public void postorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null)
		{
		
		postorder(root.getLeft());
		postorder(root.getRight());
		System.out.println(root.data);
		
		}
	}
}
