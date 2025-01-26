public class HalloWeltPlusPlus {

    public static void main(String[] args) {
        String A = "bbba";
        String B = "bba";
        boolean itsin;
        if (B.length()>A.length()){
            //return -1
        }
        if (B.length()== 0 || A.length() == 0){
            //return -1
        }

        for (int i = 0; i<=A.length()-B.length(); i++) {
            itsin = true;
            if (A.charAt(i) == B.charAt(0)) {
                for (int e = 1; e < B.length(); e++) {
                    if (B.charAt(e) != A.charAt(i + e)) {
                        itsin = false;
                        break;
                    }
                }
                if (itsin == true) {
                    System.out.println("Es ist drin "+(i+1));
                    //return i+1
                    break;
                }
            }
        }
        //return -1
    }
}
