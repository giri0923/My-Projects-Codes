package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class DeepestNodeBT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		DeepestNodeBT obj = new DeepestNodeBT();
		BinaryTreeNode deepest = obj.DeepestNodeBTNR(node.root);
		System.out.println("Deepest ELEMENT IN TREE : "+deepest.data);
	}
	BinaryTreeNode DeepestNodeBTNR(BinaryTreeNode root)
	{
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		BinaryTreeNode temp=null;
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			temp = levelorderq.poll();			
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			
			
		}
		return temp;
	}

}
