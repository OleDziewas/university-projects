public class MyTimeTest{
	
	public static void main(String[] args){
		try{
			MyAmericanTime time = new MyAmericanTime(13, 56);
			MyGermanTime time2 = new MyGermanTime(13, 12);
			time.printTheTime();
			time2.printTheTime();
		}catch(Exception e){
			System.out.println("Dat hat nich jeklappt");
		}
		
	}
	
}