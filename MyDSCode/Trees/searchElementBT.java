package BinaryTree;

public class searchElementBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		searchElementBT obj = new searchElementBT();
		boolean result = obj.searchelementBT(node.root,9);

		boolean result1 = obj.searchelementBT(node.root,4);
		System.out.println("SEARCH RESULT 9: "+result);
		System.out.println("SEARCH RESULT 4: "+result1);
	}

	boolean searchelementBT(BinaryTreeNode root,int search_data)
	{
		boolean temp;
		if(root==null)
			return false;
		else
		{
			if(root.data==search_data)
				return true;
			else
			{
				 temp = searchelementBT(root.left, search_data);
				if(temp==true)
					return true;
					temp =  searchelementBT(root.right, search_data);
			}
		}
		return temp;
		
	}
}
