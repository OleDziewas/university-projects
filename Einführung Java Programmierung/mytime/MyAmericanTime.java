public class MyAmericanTime extends MyTime{
	
	public MyAmericanTime(int hrs, int min) throws MyIllegalTimeException{
		super(hrs, min);
	}
	
	public void printTheTime(){
		if (this.hrs > 12){
			int temp = 12 -(24-this.hrs);
			System.out.println(temp + " hours and " + this.min + " PM");
		}else{
			System.out.println(this.hrs + " hours and " + this.min + " AM");
		}
	}
}