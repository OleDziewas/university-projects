public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack(5);
        System.out.println("IsEmpty: "+stack.isEmpty());
        System.out.println("IsFull: "+stack.isFull());
        stack.push('b');
        System.out.println("IsEmpty: "+stack.isEmpty());
        System.out.println("Size: "+stack.size());
        System.out.println(stack.pop());
        System.out.println("IsEmpty: "+stack.isEmpty());
    }
}
