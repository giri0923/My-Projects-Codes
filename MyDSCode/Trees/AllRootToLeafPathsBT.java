package BinaryTree;

public class AllRootToLeafPathsBT {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		binaryTreeImplement node = new binaryTreeImplement();
		AllRootToLeafPathsBT obj = new AllRootToLeafPathsBT();
		int[] path = new int[256];
		obj.AllRootToLeafPaths(node.root,path,0);
		
	}

	void AllRootToLeafPaths(BinaryTreeNode root,int[] path,int path_len)
	{
	if(root == null)
		return;
	path[path_len] = root.data;
	path_len++;
	if(root.getLeft()==null && root.getRight()==null)
	{
	PrintArray(path,path_len);
	}
	AllRootToLeafPaths(root.getLeft(), path, path_len);
	AllRootToLeafPaths(root.getRight(), path, path_len);		
	}
	
	void PrintArray(int[] path,int path_len)
	{
		for(int i = 0;i<path_len;i++)
			{System.out.print(path[i]);	
			System.out.print("->");}
		System.out.println();
		System.out.println("##############");
	}
	
	
}
