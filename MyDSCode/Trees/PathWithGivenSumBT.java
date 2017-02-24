package BinaryTree;

public class PathWithGivenSumBT {
	public static int target = 8;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		PathWithGivenSumBT obj = new PathWithGivenSumBT();
		boolean result = 	obj.PathWithGivenSum(node.root,target);
		System.out.println(result);
	}

	boolean PathWithGivenSum(BinaryTreeNode root,int target)
	{
	if(root == null)
		return false;
	if(target - root.data == 0 )
		return true;
	return PathWithGivenSum(root.getLeft(),target) ||	PathWithGivenSum(root.getRight(),target);		
	}
	
}
