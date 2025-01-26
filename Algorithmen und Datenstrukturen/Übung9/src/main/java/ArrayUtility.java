class ArrayUtility {

   public static int returnPosMax(int[] A, int i, int j) {
      int max = Integer.MIN_VALUE;
      int maxPos = 0;
      for (int e = i; e<= j; e++){
         if (A[e] > max){
            max = A[e];
            maxPos = e;
         }
      }
      return maxPos;
   }

   public static void swap(int[] A, int i, int j) {
      int b = A[i];
      A[i] = A[j];
      A[j] = b;
   }

   /* Answer 2: Die Worst-Time-Complexity, als auch die Best-Time-Complexity
   von MaxSort ist O(n^2), da sowohl in MaxSort(), als auch in returnPosMax über das Array iteriert wird.
    */
   public static void MaxSort(int[] A) {
      for (int i = A.length-1; i >= 0; i--){
         int posMax = returnPosMax(A,0,i);
         swap(A, posMax, i);
      }
   }

   public static int returnRotationDistance(int[] A) {
      int start = 0;
      int end = A.length-1;
      int mid = 0, left = 0, right = 0;

      while (end>= start){
         mid = start + (end - start) / 2;
         left = mid-1;
         right = mid+1;
         if (left<0){
            left = A.length-1;
         }
         if (right > A.length-1){
            right = 0;
         }
         if (A[mid] > A[left] && A[mid] > A[right]) {
            break;
         }
         if (A[mid] >= A[start]){
            start = mid+1;
            continue;
         }
         end = mid-1;

      }
      if (mid == A.length-1){
         mid = -1;
      }
      mid++;
      return mid;
   }
   /* Answer 4: a) BubbleSortX und BobbleSort gehören den selben Komplexitätsklassen an.
   Jedoch geht BubbleSort, das Array beim Sortieren nur von vorne und nicht von hinten durch,
    weshalb BubbleSortX in den meisten Fällen etwas schneller ist.
    b) nicht gelöst
    */
   public static void BubbleSort(int[] A) {
      boolean flag = true;
      while(flag) {
         flag = false;
         for (int i = 1; i <= A.length - 1; i++) {
            if (A[i - 1] > A[i]) {
               swap(A, i - 1, i);
               flag = true;
            }
         }
      }
   }

   /*Answer 3: Best-Case-Complexity von BubbleSortX ist O(n),
    wenn das Array schon sortiert ist und es nur noch durchlaufen werden muss.
    Im Worst-Case ist es O(n^2) da jedes Element nahezu n mal verschoben werden muss.
    */
   public static void BubbleSortX(int[] A) {
      boolean flag = true;
      while(flag){
         flag = false;
         for (int i = 1; i <= A.length-1; i++) {
            if (A[i - 1] > A[i]) {
               swap(A, i - 1, i);
               flag = true;
            }
         }
         if (flag == false){
            break;
         }
         for (int i = A.length-1; i >=1; i--){
            if (A[i-1] > A[i]){
               swap(A, i-1, i);
               flag = true;
            }
         }
      }
   }

}
