package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ZigZagTraversalBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		ZigZagTraversalBT obj = new ZigZagTraversalBT();
		obj.ZigZagTraversal(node.root);
	}
	void ZigZagTraversal(BinaryTreeNode root)
	{
		int level = 1;
		Stack<BinaryTreeNode> s= new Stack<BinaryTreeNode>();
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		levelorderq.add(null);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();	
			if(temp==null)
			{
				if(!levelorderq.isEmpty())
					levelorderq.add(null);
				while(!s.isEmpty())
					System.out.println(s.pop().getData());			
				level++;
			}					
			else
			{
				if(level%2!=0 )
				{
					System.out.println(temp.data);					
				}
				else
				{
					s.push(temp);
					}
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());				
			}
			}
			}	
		
		
	
	
}
