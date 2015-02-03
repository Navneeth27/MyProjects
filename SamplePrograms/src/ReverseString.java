
public class ReverseString {

	public static void main(String[] args) {
		String str1="I want to intern at Okta";
		System.out.println("Original string is :" +str1);
		str1=new StringBuffer(str1).reverse().toString();
		//String str2=Reverse(str1);
		System.out.println("Reversed string is :" +str1);
		
	}
}
