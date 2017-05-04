package easy;
/*
Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

Example 1:
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
*/
public class reverseWords_557 {
	 public String reverseWords(String s) {
		 StringBuffer sb = new StringBuffer();
		 String reversed_word = "";
		 //System.out.println("PADMA");
		 String[] split_words = new String[s.length()];
		 split_words = s.split(" ");
		 for(String each:split_words)
		 {
			 reversed_word = reverseWord(each);
			 sb.append(reversed_word);
			 sb.append(" ");
		 }
	     return sb.toString().trim();
	    }
	public static String reverseWord(String each)
	{
		char[] temp_array = each.toCharArray();
		char temp='\0';
		int i=0,j=temp_array.length-1;
		while(i<j)
		{   
			temp = temp_array[i];
			temp_array[i] = temp_array[j];
			temp_array[j] = temp;
			i++;
			j--;
		}
		return new String(temp_array);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		reverseWords_557 obj = new reverseWords_557();
		String res = obj.reverseWords("I am Girish");
		System.out.println(res.trim());
	}

}
