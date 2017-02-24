package BinaryTree;

public class binaryTreeImplement {
	static BinaryTreeNode root=null;
	binaryTreeImplement()
	{
		root = new BinaryTreeNode(1);
		BinaryTreeNode n1=new BinaryTreeNode(2);
		BinaryTreeNode n2=new BinaryTreeNode(3);
		BinaryTreeNode n3=new BinaryTreeNode(4);
		BinaryTreeNode n4=new BinaryTreeNode(5);
		BinaryTreeNode n5=new BinaryTreeNode(6);
		BinaryTreeNode n6=new BinaryTreeNode(7);	
		BinaryTreeNode n7=new BinaryTreeNode(8);
		BinaryTreeNode n8=new BinaryTreeNode(9);
		BinaryTreeNode n9=new BinaryTreeNode(10);
		root.setLeft(n1);
		root.setRight(n2);
		n1.setLeft(n3);
		n1.setRight(n4);
		n2.setLeft(n5);
		n2.setRight(n6);
		n3.setLeft(n7);
		n5.setLeft(n8);
		n6.setRight(n9);
	}

}


class BinaryTreeNode
{
	int data;
	BinaryTreeNode left;
	BinaryTreeNode right;

	BinaryTreeNode nextsibling;
	BinaryTreeNode(int data)
	{
		this.data = data;
		this.left = null;
		this.right = null;
		this.nextsibling = null;
	}
	
	public void setLeft(BinaryTreeNode left)
	{
		this.left = left;
	}
	
	public void setRight(BinaryTreeNode right)
	{
		this.right = right;
	}
	 public BinaryTreeNode getLeft()
	 {
		 return this.left;
	 }
	 public void setSibling(BinaryTreeNode sibling)
		{
			this.nextsibling = sibling;
		}
	 public BinaryTreeNode getRight()
	 {
		 return this.right;
	 }
	 public BinaryTreeNode getSibling()
		{
			return this.nextsibling;
		}
	 
	 public void setData(int data)
		{
			this.data = data;
		}
		
	 public int getData()
	 {
		 return this.data;
	 }
	  
}




