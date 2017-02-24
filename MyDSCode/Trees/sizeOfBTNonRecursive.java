package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class sizeOfBTNonRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		sizeOfBTNonRecursive obj = new sizeOfBTNonRecursive();
		int size = obj.sizeOfBTNR(node.root);
		System.out.println("SIZE OF THE BT IS : " + size);
		//To confirm the addition
	}
	int sizeOfBTNR(BinaryTreeNode root)
	{
		int count = 0;	
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			count++;
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());				
		}
		return count;
	}
	
}
