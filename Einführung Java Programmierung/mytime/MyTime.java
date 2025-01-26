public class MyTime{
	protected int hrs, min; 
	
	public MyTime(int hrs, int min) throws MyIllegalTimeException{
		if (hrs<0||hrs>23||min<0||min>59){
			throw new MyIllegalTimeException("Kacke");
		}
		this.hrs = hrs;
		this.min = min;
	}
	
	public boolean amIEarlier(MyTime time){
		if (this.hrs < time.hrs){
			return true;
		}
		if (this.hrs== time.hrs && this.min< time.min){
			return true;
		}
		return false;
	}
}