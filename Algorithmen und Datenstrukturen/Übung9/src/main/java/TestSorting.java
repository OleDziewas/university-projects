import java.util.Arrays;
import java.util.Random;

public class TestSorting {
    public static void main(String[] args) {
        calculateAvgTime(100000);
    }

    public static void calculateAvgTime(int am){
        int randInt;

        Random rd = new Random();

        int arr[] = new int[am];
        int arr2[];
        int arr3[];
        int arr4[];

        double avgTimeMaxSort =0;
        double avgTimeBubbleSort=0;
        double avgTimeBubbleSortX=0;
        double avgTimeSort=0;

        for (int e = 0; e<100; e++){
            for (int i = 0; i< am; i++){
                arr[i] = rd.nextInt();
            }
            arr2 = arr.clone();
            arr3 = arr.clone();
            arr4 = arr.clone();
            avgTimeMaxSort += timeForMaxSort(arr)/am;
            avgTimeBubbleSort += timeForBubbleSort(arr2)/am;
            avgTimeBubbleSortX += timeForBubbleSortX(arr3)/am;
            avgTimeSort += timeForSort(arr4)/am;
            System.out.println(e+"%");
        }

        System.out.println("MaxSort "+am+": " + avgTimeMaxSort +"ns");
        System.out.println("BubbleSort " +am+": "+ avgTimeBubbleSort +"ns");
        System.out.println("BubbleSortX " +am+": "+avgTimeBubbleSortX +"ns");
        System.out.println("Sort " +am+": "+ avgTimeSort +"ns");
    }
    public static long timeForMaxSort(int[] A){
        long startTime;
        long cpuTime;
        double time;

        startTime = System.nanoTime();
        ArrayUtility.MaxSort(A);
        cpuTime = System.nanoTime()-startTime;

        return cpuTime;
    }
    public static long timeForBubbleSort(int[] A){
        long startTime;
        long cpuTime;
        double time;

        startTime = System.nanoTime();
        ArrayUtility.BubbleSort(A);
        cpuTime = System.nanoTime()-startTime;
        return cpuTime;
    }
    public static long timeForBubbleSortX(int[] A){
        long startTime;
        long cpuTime;
        double time;

        startTime = System.nanoTime();
        ArrayUtility.BubbleSortX(A);
        cpuTime = System.nanoTime()-startTime;
        return cpuTime;
    }
    public static long timeForSort(int[] A){
        long startTime;
        long cpuTime;
        double time;

        startTime = System.nanoTime();
        Arrays.sort(A);
        cpuTime = System.nanoTime()-startTime;
        return cpuTime;
    }
}
