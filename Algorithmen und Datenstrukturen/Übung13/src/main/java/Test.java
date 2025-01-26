public class Test {
    public static void main(String[] args) {
        int[] arr = {2,5,4,8,7,31,9};
        mystery(arr, 0, arr.length-1);
        for (int i = 0; i< arr.length; i++){
            System.out.println(arr[i]);
        }
    }
    //Mystery ist ein Sortieralgorithmus, der das Array aufteilt bis es immer 2 teilige Probleme sind bei einem Array [2,5, 1] wären es [2,5] und [5,1]
    //Der erste rekursive Aufruf würde nichts verändern weil 2<5 ist. Der zweite rekursive Aufruf tauscht die Element 5 und 1, da 5>1.
    //Da sich dadurch aber das zu vergleichende Element im ersten Teil ändert, muss dieser erneut aufgerufen werden(der 3. rekursive Aufruf).
    //Mit dem 3. Aufruf werden die Element 2 und 1 getauscht und das gesamte Array ist nun sortiert. [1,2,5]
    public static void mystery(int[] A, int l , int r){
        int range = r-l+1;
        int subrange = (int)Math.ceil(2.0*range/3.0);
        if (range == 2 && A[l] > A[r]){
            int a = A[l];
            A[l] = A[r];
            A[r] = a;
        }else if (range >=3){
            mystery(A, l, l+subrange-1);
            mystery(A, r-(subrange-1), r);
            mystery(A, l, l+subrange-1);
        }
    }
}
