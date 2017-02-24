package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class NextSiblingBTNR {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		NextSiblingBTNR obj = new NextSiblingBTNR();
		obj.NextSiblingBT(node.root);

		//System.out.println("SEARCH RESULT 4: "+result1);
	}
	void NextSiblingBT(BinaryTreeNode root)
	{
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
				
			}
			else
			{
			root.setSibling(levelorderq.peek());
			if(root.getSibling()!=null)
			System.out.println(root.getSibling().getData());
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());				
			}
			}	
		NextSiblingBTNR obj = new NextSiblingBTNR();
		obj.preorder(root);
		}
	
	public void preorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null )
		{
			if(root.getSibling()!=null)
					System.out.println(root.getSibling().getData());
		preorder(root.getLeft());
		preorder(root.getRight());

		//preorder(root.getSibling());
		
		}
	}
	

}
