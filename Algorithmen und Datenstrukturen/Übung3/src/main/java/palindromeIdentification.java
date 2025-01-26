class palindromeIdentification{

   public static boolean palindromeIdentification(char[] A) {
      Stack stack = new Stack(A.length);
      for (int i = 0; i < A.length; i++) {
         stack.push(A[i]);
      }
      char[] arr = new char[A.length];
      for (int i = 0; i < A.length; i++) {
         arr[i] = stack.pop();
      }
      for (int i = 0; i< A.length; i++){
         if (arr[i] != A[i]){
            return false;
         }
      }
      return true;
   }

}
