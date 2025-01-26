package mystack;

public class Elem{
	public int content;
	public Elem previous;
	
	Elem(int content){
		this.content = content;
		this.previous = null;
	}
}