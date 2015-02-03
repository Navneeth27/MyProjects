
public class FindLargest {
  public static int large()
  {
	  int[] numbers={88,45,67,64,12,56,78,33,67};
		int largest=Integer.MIN_VALUE;
		for(int i=0;i<numbers.length;i++){
		if(numbers[i]>largest)
		{
			largest=numbers[i];
		}
	}
System.out.println("Largest number is : "+largest);
return largest;
  }
  

	public static void main(String[] args) {
		System.out.println(FindLargest.large());
}

}