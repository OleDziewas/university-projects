public class StaticIsForBeginners{
	
	public static void main(String[] args){
		char[] test = {'A','B','C','D','E', 'F'};
		char[] res = exchange(test);
		for (int i = 0; i < res.length; i++){
			System.out.println(res[i]);
		}
		
	}
	
	public static int sumOfNumbers(int max){
		if (max < 0){
			System.out.println("Error in sumOfNumbers - NegativeInput: "+ max);
			return -1;
		}
		int sum = 0;
		for (int i = 0; i<=max; i++){
			sum += i;
		}
		return sum;
	}
	public static int[] initializeArray(int elemCount){
		int[] res = new int[elemCount];
		for (int i = elemCount; i >0; i--){
			res[elemCount-i] = i;
		}
		return res;
	}
	public static char[] exchange(char[] text){
		char[] res = new char[text.length];
		for (int i = text.length; i> 0; i--){
			res[text.length-i] = text[i-1];
		}
		return res;
	}
	
}