import java.util.Arrays;
public class Duplicate {

	public static void main(String[] args) {
		int numbers[]={22,44,45,56,78,44,89,45,78,56};
		Arrays.sort(numbers);
		for(int i=1;i<numbers.length;i++){
			if(numbers[i]==numbers[i-1]){
				System.out.println("Duplicate number is: " +numbers[i]);
			}
			
		}
		

	}

}
