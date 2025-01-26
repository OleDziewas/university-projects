public class MyGermanTime extends MyTime{
	
	public MyGermanTime(int hrs, int min) throws MyIllegalTimeException{
		super(hrs, min);
	}
	
	public void printTheTime(){
		System.out.println(this.hrs + " Uhr und " +this.min + " Minuten");
	}
}