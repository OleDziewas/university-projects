public class QueueTest{
	
	public static void main(String[] args){
		QueueImpl qu = new QueueImpl();
		try{
			System.out.println(qu.front());
		}catch(EmptyQueueException e){
			System.out.println("lel leer");
		}
		qu.enqueue(3);
		qu.enqueue(5);
		try{
			qu.dequeue();
		}catch(EmptyQueueException e){}
		
		qu.printQueue();
		try{
			qu.dequeue();
		}catch(EmptyQueueException e){}
		try{
			System.out.println(qu.front());
		}catch(EmptyQueueException e){
		}
		int[] ar = {1,2,3};
	}
}