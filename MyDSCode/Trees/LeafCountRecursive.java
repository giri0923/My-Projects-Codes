package BinaryTree;

public class LeafCountRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		LeafCountRecursive obj = new LeafCountRecursive();
		int leaf_count = obj.LeafCount(node.root);
		int half_leaf_count = obj.HalfLeafCount(node.root);
		int full_leaf_count = obj.FullNodeCount(node.root);
		System.out.println("Leaf count " + leaf_count);
		System.out.println("Half node count " + half_leaf_count);
		System.out.println("Full node count " + full_leaf_count);
		
	}

	int LeafCount(BinaryTreeNode root)
	{
		int count =0;
		if(root==null)
			return 0;
		if(root.left==null && root.right==null)
			return 1;
		else
		{
			return LeafCount(root.getLeft())+LeafCount(root.getRight());
			
		}
		
	}
	
	int HalfLeafCount(BinaryTreeNode root)
	{
		int count =0;
		if(root==null)
			return 0;
		if(((root.getLeft()!=null) && (root.getRight()==null)) || ((root.getLeft()==null) && (root.getRight()!=null)))
			return 1+HalfLeafCount(root.getLeft())+HalfLeafCount(root.getRight());
		else
			return HalfLeafCount(root.getLeft())+HalfLeafCount(root.getRight());
		//		else
//		{
//			count+= LeafCount(root.getLeft());
//			count+= LeafCount(root.getRight());
//			
//		}
//		return count;
		//return 0;
	}
	
	int FullNodeCount(BinaryTreeNode root)
	{
		int count =0;
		if(root==null)
			return 0;
		if(root.getLeft()!=null && root.getRight()!=null)
			return 1+FullNodeCount(root.getLeft())+FullNodeCount(root.getRight());
		else
			
		 	return FullNodeCount(root.getLeft())+FullNodeCount(root.getRight());
		//return 0;
		//		else
//		{
//			count+= LeafCount(root.getLeft());
//			count+= LeafCount(root.getRight());
//			
//		}
//		return count;
		//return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
