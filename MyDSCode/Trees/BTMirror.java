package BinaryTree;

public class BTMirror {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		BTMirror obj = new BTMirror();
		System.out.println("BEFORE MIRROR");
		obj.inorder(node.root);
		BinaryTreeNode root  = obj.MirrorBT(node.root);
		System.out.println("#######");
		System.out.println("AFTER MIRROR");
		obj.inorder(root);
		//System.out.println("MIRROR OT TREE : "+max);
	}
	BinaryTreeNode MirrorBT(BinaryTreeNode root)
	{
		BinaryTreeNode temp;
	if(root != null)
	{
	MirrorBT(root.left);
	MirrorBT(root.right);
	temp = root.getLeft();
	root.setLeft(root.getRight());
	root.setRight(temp);
	}
	return root;
	}

	public void inorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null)
		{
		
		inorder(root.getLeft());
		System.out.println(root.data);
		inorder(root.getRight());
		
		}
	}
}
