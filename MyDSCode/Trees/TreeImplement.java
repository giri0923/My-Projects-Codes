package Trees;
import java.util.Queue;
import java.util.Stack;
import java.util.*;
public class TreeImplement {
Queue<node> q=new LinkedList<node>();

node root=null;

void insertIntoTree(int data)
{
	node newnode=new node();
	newnode.data=data;
	newnode.left=null;
	newnode.right=null;
	node temp=null;
	if(root==null)
	{	
	root=newnode;
	}
else
{
	q.add(root );
	while(!q.isEmpty())
	{
		temp=q.remove();
		if(temp.left!=null)
		{q.add(temp.left);
			//enqueue(temp.left);
		}
		else
		{
			temp.left=newnode;
			
			break;
		}
		

			if(temp.right!=null)
			{
				q.add(temp.right);
			}
			else
			{
				temp.right=newnode;
				break;
			}
	}
}
}

void displaypre(node root)
{
	if(root!=null)
	{
	System.out.println(root.data);
	displaypre(root.left);
	displaypre(root.right);
}
}

int findMax(node root)
{
	int max=0,left,right,data;
	if(root!=null)
	{
		data=root.data;
		left = findMax(root.left);
		right = findMax(root.right);
		if(max<right)
			max=right;
		if(max<left)
			max=left;
		if(max<data)
			max=data;
	//	return max;
	}
	return max;
	
}

int sizeoftree(node root)
{
	if(root==null)
		return 0;
	return sizeoftree(root.left)+1+sizeoftree(root.right);
		
}

int SizeOfTreeNonrecursive(node root)
{
	int size=0;
	Queue<node> q=new LinkedList<node>();
	if(root==null)	
		return 0;
	q.add(root);
	node temp=null;
	while(!q.isEmpty())
	{
		temp=q.remove();
		size++;
		if(temp.left!=null)
			q.add(temp.left);
		if(temp.right!=null)
			q.add(temp.right);
	}
	return size;
}

boolean search(node root, int data)
{
	boolean flag=false;
	
	
	if(root==null)
		return false;
	
	else
	{
		if(root.data == data)//&& obj.count<1)
		{
			//obj.count++;
			flag=  true;
		}
		
		else
		{
		flag = search(root.left,data);
if(!flag)
		flag= search(root.right,data);
		}	
	}
	return flag;
	
}
node dequeue()
{
	return q.remove();
}

}