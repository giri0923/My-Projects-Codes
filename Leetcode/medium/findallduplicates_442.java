package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easy.IntersectionArrays_349;

/*
  Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements that appear twice in this array.

Could you do it without extra space and in O(n) runtime?

Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]
 */
public class findallduplicates_442 {
	  public List<Integer> findDuplicates(int[] nums) {

	        HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	        ArrayList<Integer> lis = new ArrayList<Integer>();
	        for(int each:nums)
	        {
	        	if(hm.containsKey(each))
	        	{
	        		hm.put(each, hm.get(each)+1);
	        	}
	        	else
	        	{
	        		hm.put(each,1);
	        	}
	        }
	        for(int each:nums)
	        {
	        	if(hm.get(each)==2)
	        	{
	        		if(!lis.contains(each))
	        		lis.add(each);
	        		
	        	}
	        }
	        return lis;
	    }
	  
	  public List<Integer> findDuplicatesWithoutExtraSpace(int[] nums) {
		  	ArrayList<Integer> lis = new ArrayList<Integer>();
		  	int ind = 0;
	        for(int i=0;i<nums.length;i++)
	        {
	        	ind = Math.abs(nums[i])-1;
	        	if(nums[ind]>0)
	        	{
	        		nums[ind] = -nums[ind];
	        	}
	        	else
	        		lis.add(Math.abs(nums[i]));
	        		
	        }
	        
	        return lis;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		findallduplicates_442 obj = new findallduplicates_442();
		int[] nums1={4,3,2,7,8,2,3,1};
		List<Integer> lis = obj.findDuplicates(nums1);

		List<Integer> lis1 = obj.findDuplicatesWithoutExtraSpace(nums1);
		System.out.println(lis1);
	}

}
