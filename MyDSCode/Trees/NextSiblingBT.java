package BinaryTree;

public class NextSiblingBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		VerticalSumBT obj = new VerticalSumBT();
		obj.VerticalSum(node.root,0);
		
	}

	void VerticalSum(BinaryTreeNode root,int c)
	{
	if(root==null)
		return;
	VerticalSum(root.getLeft(),c-1);
	
	VerticalSum(root.getRight(),c+1);
	}

}
