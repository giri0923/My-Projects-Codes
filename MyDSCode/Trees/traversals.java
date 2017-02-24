package BinaryTree;
import java.util.*;
public class traversals {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		traversals obj = new traversals();
		System.out.println("#############PREORDER TRAVERSAL#################");
		obj.preorder(node.root);
		System.out.println("#############POSTORDER TRAVERSAL#################");
		obj.postorder(node.root);
		System.out.println("#############INORDER TRAVERSAL#################");
		obj.inorder(node.root);
		System.out.println("#############LEVEL ORDER TRAVERSAL#################");
		obj.levelorder(node.root);
	}
	public void preorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null)
		{
		System.out.println(root.data);
		preorder(root.getLeft());
		preorder(root.getRight());
		
		}
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
	
	public void inorder(BinaryTreeNode root)
	{
		//System.out.println(root.data);
		if(root!=null)
		{
		
		inorder(root.getLeft());
		System.out.println(root.data);
		inorder(root.getRight());
		
		}
	}
	
	public void levelorder(BinaryTreeNode root)
	{
		if(root==null)
			return;
		Queue<BinaryTreeNode> levelorderq = new LinkedList<BinaryTreeNode>();
		levelorderq.add(root);
		while(!levelorderq.isEmpty())
		{
			BinaryTreeNode temp = levelorderq.poll();
			System.out.println(temp.data);
			if(temp.getLeft() != null)
				levelorderq.add(temp.getLeft());
			if(temp.getRight() != null)
				levelorderq.add(temp.getRight());
			
			
		}
		
	}
	
	
	
}
