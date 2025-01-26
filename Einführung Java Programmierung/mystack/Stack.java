package mystack;

public interface Stack{
	public boolean isEmpty();
	public void push(int content);
	public void printStack();
	public Elem top();
	public void pop();
}