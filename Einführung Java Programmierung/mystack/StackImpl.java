package mystack;

public class StackImpl implements Stack{
	private Elem topElem;
	
	StackImpl(){
		this.topElem = null;
	}
	
	public boolean isEmpty(){
		if(this.topElem == null){
			System.out.println("leer");
			return true;
		}
		System.out.println("nicht leer");
		return false;
	}
	
	public void push(int content){
		Elem elem = new Elem(content);
		elem.previous = this.topElem;
		this.topElem = elem;
	}
	
	public void printStack(){
		if (topElem == null){
			System.out.println("leer");
		}else{
			Elem temp = this.topElem;
			System.out.println(temp.content);
			while (temp.previous != null){
				System.out.println(temp.previous.content);
				temp = temp.previous;
			}
		}
	}
	
	public Elem top() throws EmptyStackException{
		if (this.topElem == null){
			throw new EmptyStackException("Fail top");
		}else{
			return this.topElem;
		}
	}
	
	public void pop() throws EmptyStackException{
		if (this.topElem == null){
			throw new EmptyStackException("Fail pop");
		}else{
			this.topElem = topElem.previous;
		}
	}
}

