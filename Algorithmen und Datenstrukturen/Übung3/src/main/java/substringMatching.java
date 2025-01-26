class substringMatching{

   public static int substringMatching(char[] A, char[] B){
      boolean itsin;
      if (B.length>A.length){
         return -1;
      }
      if (B.length== 0 || A.length == 0){
         return -1;
      }

      for (int i = 0; i<=A.length-B.length; i++) {
         itsin = true;
         if (A[i] == B[0]) {
            for (int e = 1; e < B.length; e++) {
               if (B[e] != A[i+e]) {
                  itsin = false;
                  //i = i + e-1;
                  break;
               }
            }
            if (itsin == true) {
               return i+1;
            }
         }
      }
      return -1;
   }

}
