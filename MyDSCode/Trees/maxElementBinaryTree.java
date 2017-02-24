package BinaryTree;

public class maxElementBinaryTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		maxElementBinaryTree obj = new maxElementBinaryTree();
		int max = obj.maxElementBT(node.root);
		System.out.println("MAX ELEMENT IN TREE : "+max);
	}
	
	int maxElementBT(BinaryTreeNode root)
	{
		int max_value=-1111,left_max,right_max;
		
		if(root!=null)
		{
		//find the max element in left subtree
		left_max=maxElementBT(root.getLeft());
		//find the max element in right subtree
		right_max=maxElementBT(root.getRight());
		if(left_max>right_max)
			max_value = left_max;
			else
				max_value = right_max;
		
		if(root.data>max_value)
			max_value = root.data;
		}
		return max_value;
	}
	
	
}
