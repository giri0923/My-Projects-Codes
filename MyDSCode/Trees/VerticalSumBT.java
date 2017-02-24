package BinaryTree;

import java.util.HashMap;

public class VerticalSumBT {

	public static HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		VerticalSumBT obj = new VerticalSumBT();
		obj.VerticalSum(node.root,0);
		for(int key:hm.keySet())
			System.out.println("Column "+key + " , sum "+hm.get(key));
	}
	
	void VerticalSum(BinaryTreeNode root,int c)
	{
	if(root==null)
		return;
	VerticalSum(root.getLeft(),c-1);
	if(hm.containsKey(c))
	hm.put(c, hm.get(c)+root.getData());
	else
		hm.put(c, root.getData());
	VerticalSum(root.getRight(),c+1);
	}

}
