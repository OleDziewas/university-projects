public class QueueImpl{
	private Elem enq;
	private Elem deq;
	
	public QueueImpl(){
		this.enq = null;
		this.deq = null;
	}
	
	public boolean isEmpty(){
		return this.deq==null;
	}
	
	public void enqueue(int content){
		Elem el = new Elem(content, null);
		if (this.enq == null){
			this.enq = el;
			this.deq = el;
		}else{
			this.enq.next = el;
			this.enq = el;
		}
	}
	
	public int front() throws EmptyQueueException{
		if(this.deq == null){
			throw new EmptyQueueException("Failed front");
		}
		return this.deq.content;
	}
	
	public void dequeue() throws EmptyQueueException{
		if(this.deq == null){
			throw new EmptyQueueException("Failed front");
		}
		this.deq = this.deq.next;
	}
	
	public void printQueue(){
		Elem el = this.deq;
		while(el != null){
			System.out.println(el.content);
			el = el.next;
		}
	}
}