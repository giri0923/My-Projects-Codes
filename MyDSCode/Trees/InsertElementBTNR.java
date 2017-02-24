package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class InsertElementBTNR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				binaryTreeImplement node = new binaryTreeImplement();
				InsertElementBTNR obj = new InsertElementBTNR();
				obj.InsertElementBT(node.root,55);
				//To confirm the addition
				obj.postorder(node.root);
	}
	public void postorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null)
		{
		
		postorder(root.getLeft());
		postorder(root.getRight());
		System.out.println(root.data);
		
		}
	}
	void InsertElementBT(BinaryTreeNode root,int insert_data)
	{
		BinaryTreeNode new_node = new BinaryTreeNode(insert_data);		
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			
			if(temp.getLeft() == null)
			{
				temp.setLeft(new_node);
				levelorderq.clear();
				return;
			}
			else
				levelorderq.add(temp.getLeft());
			
			if(temp.getRight() == null)
			{
				temp.setRight(new_node);
				levelorderq.clear();
				return;
			}
			else
				levelorderq.add(temp.getRight());		
				
		}
	}
	
}
