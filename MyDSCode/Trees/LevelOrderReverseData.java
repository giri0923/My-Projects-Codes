package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LevelOrderReverseData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		LevelOrderReverseData obj = new LevelOrderReverseData();
		obj.LevelOrderReverse(node.root);
	}

	void LevelOrderReverse(BinaryTreeNode root)
	{
		Stack<BinaryTreeNode> s= new Stack<BinaryTreeNode>();
		//LinkedList<BinaryTreeNode> levelorderStack = new LinkedList<BinaryTreeNode>();
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();		
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			s.push(temp);			
		}		
		while(!s.isEmpty())
		{
			System.out.println((s.pop()).data);
		}	
	}
}
