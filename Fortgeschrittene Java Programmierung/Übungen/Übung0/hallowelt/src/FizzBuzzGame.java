public class FizzBuzzGame {

    public static void main(String[] args) {
        boolean ide = identical("kackkack",0 );
        System.out.println(ide);
    }

    public static boolean identical(String A, int i){
        boolean res = false;
        if (A.length()%2==1 || A.length() == 0){
            return false;
        }
        //exit-case
        if (i>=A.length()/2) {
            return true;
        }
        if (A.charAt(i) == A.charAt(i+A.length()/2)){
            res = identical(A, i+1);
        }
        return res;
    }
}
