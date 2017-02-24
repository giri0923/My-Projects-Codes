package BinaryTree;

import java.util.ArrayList;
import java.util.Collections;

public class LowestCommonAncestor {
	public static ArrayList<Integer> path = new ArrayList<Integer>();
	public static ArrayList<Integer> path1 = new ArrayList<Integer>();
	static int target1=4;
	static int target2=6;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		LowestCommonAncestor obj = new LowestCommonAncestor();
		boolean result = obj.pathRootToTarget(node.root,target1,path);
		boolean result1 = obj.pathRootToTarget(node.root,target2,path1);
		Collections.reverse(path);
		Collections.reverse(path1);
		int lca=0;
		for(int i=0;i<path.size();i++)
		{
			if(path.get(i)!=path1.get(i))
				break;
			lca = path.get(i);
		}
		System.out.println("LOWEST COMMON ANCESTOR "+lca);		
	}

	boolean pathRootToTarget(BinaryTreeNode root,int target,ArrayList<Integer> path)
	{
	if(root == null)
		return false;
	if(root.data == target)
	{
		return true;
	}
	if(pathRootToTarget(root.getLeft(),target,path)||pathRootToTarget(root.getRight(),target,path))
		{
		path.add(root.getData());
		return true;
		}
	return false;
	}
}
