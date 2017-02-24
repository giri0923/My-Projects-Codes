package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class SumOfAllElementsBTNR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		SumOfAllElementsBTNR obj = new SumOfAllElementsBTNR();
		int result = 	obj.SumElementsBTNR(node.root);
		System.out.println(result);
	}
	int SumElementsBTNR(BinaryTreeNode root)
	{
		int sum=0;
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			sum=sum+temp.data;
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			
			
		}
		return sum;
	}
}
