package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class searchElementBTNonRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		searchElementBTNonRecursive obj = new searchElementBTNonRecursive();
		boolean result = obj.searchElementBTNR(node.root,9);

		boolean result1 = obj.searchElementBTNR(node.root,3);
		System.out.println("SEARCH RESULT 9: "+result);
		System.out.println("SEARCH RESULT 4: "+result1);
	}
	boolean searchElementBTNR(BinaryTreeNode root,int search_data)
	{
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			if(temp.data == search_data)
				return true;
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			
			
		}
		return false;
	}
	
	
}
