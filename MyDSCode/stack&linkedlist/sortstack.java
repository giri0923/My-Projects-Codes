package com.giri.stack;

public class sortstack {

	/**
	 * @param args
	 */
	static stackmain obj=new stackmain();
	static stackmain obj1=new stackmain();
	
	public static void sort()
	{
		int i,temp;
		int af[]=new int[9];
		int t;
		if(!obj.isEmpty())
		{
			obj1.push(obj.pop());
			
			//System.out.println("obj1 "+obj1.peek());
		}
		
		for(i=0;!obj.isEmpty();i++)
		{
			//System.out.println("stack length "+ obj.s.length);
			temp=obj.pop();
			while(!obj1.isEmpty()&&(obj1.peek()>temp))
			{t=obj1.pop();
			System.out.println("original stack "+t);
				
				obj.push(t);
			}
				
			System.out.println("second stack "+temp);
				obj1.push(temp);
		}
		for(i=0;i<8;i++)
		{
		System.out.println(obj1.s[i]);
		}
	}
	public static void main(String[] args) {
	
		
		obj.push(10);
		obj.push(8);
		obj.push(9);
		obj.push(4);
		obj.push(2);
		obj.push(3);
		obj.push(1);
		obj.push(7);
		
		
//		for(int k=0;k<8;k++)
//			System.out.print(" "+obj.s[k]);
			
		sort();
	}
	
	

}
