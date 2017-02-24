package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class HeightOfBTNonRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		HeightOfBTNonRecursive obj = new HeightOfBTNonRecursive();
		int result = obj.HeightOFBT(node.root);
		System.out.println("result "+result);
	}
	
	int HeightOFBT(BinaryTreeNode root)
	{
		int height = 0;
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
				height++;
			}
			else
			{
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());				
			}
			}	
		return height;
	}
}
