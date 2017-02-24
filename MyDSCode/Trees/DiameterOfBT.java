package BinaryTree;

public class DiameterOfBT {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		DiameterOfBT obj = new DiameterOfBT();
		int result = obj.diameterBT(node.root);
		System.out.println("Diameter is "+result);
	}
	
	 int diameterBT(BinaryTreeNode root)
	{
		 if(root==null)
			 return 0;
		 int left_height = heightBT(root.getLeft());
		 int right_height = heightBT(root.getRight());
		int root_dia = left_height + right_height + 1;
		int left_diameter=diameterBT(root.left);
		int right_diameter=diameterBT(root.right);
		return Math.max(root_dia, Math.max(left_diameter,right_diameter));
		
	}
	 
	 int heightBT(BinaryTreeNode root)
	 {
		 if(root==null)
			 return 0;
		 else
			 return Math.max(heightBT(root.left)+1,heightBT(root.right)+1);
	 }

}
