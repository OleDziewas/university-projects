package debugging.counter;

public class Main {

	public static void main(String[] args) {
		Counter counter = new Counter();
		counter.count();
		System.out.printf("We have counted %d", counter.getResult());
	}
}
