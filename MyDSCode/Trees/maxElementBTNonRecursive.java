package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class maxElementBTNonRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		maxElementBTNonRecursive obj = new maxElementBTNonRecursive();
		int max = obj.maxElementBTNR(node.root);
		System.out.println("MAX ELEMENT IN TREE : "+max);
	}
	int maxElementBTNR(BinaryTreeNode root)
	{
		int max_element = -1111;
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			if(temp.data>max_element)
				max_element = temp.data;
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			
			
		}
		return max_element;
	}

}
