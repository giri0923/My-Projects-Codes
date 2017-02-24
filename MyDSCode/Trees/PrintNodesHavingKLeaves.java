package BinaryTree;

public class PrintNodesHavingKLeaves {
	public static int count = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		PrintNodesHavingKLeaves obj = new PrintNodesHavingKLeaves();
		int res = obj.PrintNodesKLeaves(node.root , 1);
		
	}
	public int PrintNodesKLeaves(BinaryTreeNode root,int k)
	{
		if(root==null)
			return 0;
		if(root.getLeft()==null && root.getRight()==null)
		{
		return 1;
		}		
		
		int sum = PrintNodesKLeaves(root.getLeft(),k)+PrintNodesKLeaves(root.getRight(),k);
		if(sum==k)
			System.out.println(root.data);
		
		return sum;
		
	}

}
