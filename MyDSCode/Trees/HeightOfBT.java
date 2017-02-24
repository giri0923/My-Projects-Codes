package BinaryTree;

public class HeightOfBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		HeightOfBT obj = new HeightOfBT();
		int result = obj.heightBT(node.root);
		System.out.println(result);
	}
	
	int heightBT(BinaryTreeNode root)
	{
		int left,right;
		if(root!=null)
		{
		left=heightBT(root.getLeft());
		right=heightBT(root.getRight());
		if(left>right)
			return left+1;
		else
			return right+1;
		}
		return 0;
	}

}
