package easy;
import java.util.*;
public class IntersectionArrays_349 {

public int[] intersection(int[] nums1, int[] nums2) {
		int res = nums1.length>nums2.length?nums1.length:nums2.length;
       // int[] result = new int[res];
        HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
        ArrayList<Integer> lis = new ArrayList<Integer>();
        for(int each:nums1)
        	hm.put(each,1);
        int i=0;
        for(int each:nums2)
        {
        	if(hm.containsKey(each))
        		{//result[i]=each;
        		lis.add(each);
        		hm.remove(each);
        	i++;}
        	
        
        }
        int[] result = new int[lis.size()];
        System.out.println(lis);
        for( i=0;i<lis.size();i++)
        	result[i] = lis.get(i);
        return result;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntersectionArrays_349 obj = new IntersectionArrays_349();
		
		int[] nums1={1,2,2,1},nums2={2,2};
		int[] res = obj.intersection(nums1,nums2);
		for(int each:res)
			System.out.println(each);
		
	}


}
