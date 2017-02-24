package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// level with max nodes
public class WidthofBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		WidthofBT obj = new WidthofBT();
		int result = obj.WidthBT(node.root);
		System.out.println("Max width level = "+result);
	}
	int WidthBT(BinaryTreeNode root)
	{
		
		int count_nodes = 0,max_count_nodes=0;
		int level = 1,max_level = 0;
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
				if(count_nodes>max_count_nodes)
				{
					max_count_nodes = count_nodes;
					max_level = level;
				}
				count_nodes=0;
				System.out.println("Level "+level);
				level++;
			}
			else
			{
			count_nodes++;	
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());				
			
			}
			}	
		return max_level;
	}
}
