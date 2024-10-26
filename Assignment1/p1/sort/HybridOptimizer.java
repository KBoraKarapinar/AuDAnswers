package p1.sort;

import java.lang.reflect.Array;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Optimizes the {@link HybridSort} by trying to find the k-value with the lowest number of read and write operations..
 */
public class HybridOptimizer {

    static boolean logAllowed = false;

    /**
     * Optimizes the {@link HybridSort} by trying to find the k-value with the lowest number of read and write operations.
     * The method will try out all k-values starting from 0 and return the k-value with the lowest number of read and write operations.
     * It will stop once if found the first local minimum or reaches the maximum possible k-value for the size of the given array.
     *
     * @param hybridSort the {@link HybridSort} to optimize.
     * @param array the array to sort.
     * @return the k-value with the lowest number of read and write operations.
     * @param <T> the type of the elements to be sorted.
     */
    public static <T> int optimize(HybridSort<T> hybridSort, T[] array) {
        System.out.println("[H2c][01] Optimizer : i'm called" );
       //We have an array with data of type T
//We are searching for the necessary value of k to sort this array, starting from 0
       
         

        int k = 0;
        int currentTotal = 0;
        int prevTotal = 0;
        boolean runWhile = true ;

        boolean currentWasBiggerThanPrev = false;

     //For length 5: 0, 1, 2, 3, 4, 5, 6, let it perform the final check so that if it's 5, it returns 5.
//Comparison is with 7.
        while(k < array.length + 2 && !currentWasBiggerThanPrev){
            ArraySortList<T> ourList = new ArraySortList<T>(array);
            hybridSort.setK(k);
            hybridSort.sort(ourList);
            prevTotal = currentTotal;
            System.out.println("prevTotal: " + prevTotal );
            currentTotal = ourList.getReadCount() +ourList.getWriteCount();
            System.out.println("currentTotal: " + currentTotal );

            if(k != 0 && currentTotal > prevTotal){
            currentWasBiggerThanPrev = true;
            return k -1;
            }

            if(k == array.length + 1){
                return k -1;
            }

            k = k + 1;


        }

        //todo: make 1;
        System.err.println("ops" );
        return Integer.MAX_VALUE;










    }



}
//following code doesnt work well:
        /*do {
            k++;
            System.out.println("[H2c][02] Optimizer : while runs, current k: (initial -1, first legal:0)  " + k  );
            ArraySortList<T> ourList = new ArraySortList<T>(array);
            hybridSort.setK(k);
            hybridSort.sort(ourList);
            System.out.println("[H2c][03] Optimizer : list sorted:)  " );
            System.out.println(ourList.toString());

            int newTotal = ourList.getReadCount() + ourList.getWriteCount();
           System.out.println("[H2c][04] Optimizer : new total: " + newTotal);

            runWhile = newTotal < currentTotal;
            if (runWhile) {
                System.out.println("[H2c][05] Optimizer : new total was smaller than current total, updating current total");
                currentTotal = newTotal;
            }
        } while (runWhile);*/
