package mystack;

public class StackTest{
	
	public static void main(String[] args){
		StackImpl sta = new StackImpl();
		sta.pop();
		sta.push(7);
		sta.push(5);
		sta.push(4);
		sta.pop();
		sta.pop();
		sta.pop();
		sta.pop();
		sta.printStack();
	}
}