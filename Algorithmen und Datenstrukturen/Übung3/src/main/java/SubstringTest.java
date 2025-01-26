public class SubstringTest {
    public static void main(String[] args) {
        substringMatching sub = new substringMatching();
        char[] A = "Gesellschaft".toCharArray();
        char[] B = "lscha".toCharArray();
        int i = sub.substringMatching(A, B);
        System.out.println(i);
    }
}
