package p1.sort;

import p1.comparator.CountingComparator;

import java.util.Comparator;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A hybrid sorting algorithm. It uses a combination of mergeSort and bubbleSort.
 * <p>
 * mergeSort is used for sorting the lists of size greater than or equal to k.
 * <p>
 * bubbleSort is used for sorting the lists of size less than k.
 *
 * @param <T> the type of the elements to be sorted.
 *
 * @see Sort
 */
public class HybridSort<T> implements Sort<T> {

    boolean logAllowed = false;

    /**
     * The threshold for switching from mergeSort to bubbleSort.
     */
    private int k;

    /**
     * The comparator used for comparing the sorted elements.
     */
    private final CountingComparator<T> comparator;

    /**
     * Creates a new {@link HybridSort} instance.
     *
     * @param k          the threshold for switching from mergeSort to bubbleSort.
     * @param comparator the comparator used for comparing the sorted elements.
     */
    public HybridSort(int k, Comparator<T> comparator) {
        this.k = k;
        this.comparator = new CountingComparator<>(comparator);
    }

    @Override
    public void sort(SortList<T> sortList) {
        comparator.reset();
        mergeSort(sortList, 0, sortList.getSize() - 1);
    }

    @Override
    public int getComparisonsCount() {
        return comparator.getComparisonsCount();
    }

    /**
     * Returns the current threshold for switching from mergeSort to bubbleSort.
     * @return the current threshold for switching from mergeSort to bubbleSort.
     */
    public int getK() {
        return k;
    }

    /**
     * Sets the threshold for switching from mergeSort to bubbleSort.
     * @param k the new threshold.
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * Sorts the given {@link SortList} using the mergeSort algorithm.
     * It will only consider the elements between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     * <p>
     * Once the amount of elements to sort is less than the threshold {@link #k}, the algorithm switches to bubbleSort.
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the list to be sorted. (inclusive)
     * @param right The rightmost index of the list to be sorted. (inclusive)
     */
    public void mergeSort(SortList<T> sortList, int left, int right) {
        if (logAllowed) {
            System.out.println("[H2b][01][01] MERGESORT MAIN : MERGE SORT HAS BEEN CALLED WITH left: " + left + " right: " + right);
            System.out.println(" ");
        }



        if( (right - left + 1) >= getK() ){
            if (logAllowed) {
                System.out.println("[H2b][01][02] MERGESORT MAIN : mergesort contiunes with normal iteration: WHY: ");
                System.out.println("[H2b][01][02] MERGESORT MAIN : right-left + 1 MORE OR EQUAL less than k, amount to be sorted : " + (right - left + 1) + " and: K: " + k);
                System.out.println(" ");
            }

            int mid = (int) Math.floor((left+right) /2 ) ;
            if(right - left + 1 != 1){
            mergeSort(sortList,left,mid);
            mergeSort(sortList,mid+1,right);
            merge(sortList,left,mid,right);}

        }else{
            if (logAllowed) {
                System.out.println("[H2b][01][03] MERGESORT MAIN : Switching to bubble sort with left: WHY: " + left + " right: " + right);
                System.out.println("[H2b][01][02] MERGESORT MAIN : right-left + 1 was LESS AND EQUAL than k, amount to be sorted : " + (right -left + 1) + " and: K: " + k);
                System.out.println(" ");
            }
            bubbleSort(sortList,left,right);
        }


    }

    /**
     * Merges the two sorted sublists between the indices left and right (both inclusive) of the given {@link SortList}.
     * The middle index separates the two sublists and is the last index of the left sublist.
     *
     * <p>The left sublist ranges from left to middle (both inclusive) and the right sublist ranges from
     * middle + 1 to right (both inclusive). Bot sublists are sorted.
     *
     * <p>The algorithm uses a temporary {@link SortList} to store the merged elements. The results are copied back to
     * the original {@link SortList} at the same location. Elements with indices less than left or greater than right
     * will not be altered.
     *
     * <p>After merging the elements between left and right (both inclusive) will be sorted.
     *
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the two sublists to be merged. (inclusive)
     * @param middle The index that separates the two sublists. It is the last index that belongs to the left sublist.
     * @param right The rightmost index of the two sublists to be merged. (inclusive)
     */
    public void merge(SortList<T> sortList, int left, int middle, int right) {
        if (logAllowed) {
            System.out.println("[H2b][02][01] MERGE CALLED WITH: left: " + left + " middle: " + middle + " right: " + right);
            System.out.println(" THE LENGTH OF ARRAY B: (right-left+1)" + (right-left+1) );
        }

        ArraySortList<T> B = new ArraySortList<T>(right-left+1);

        int pl = left;
        int pr = middle + 1;


        for(int i = 0; i <= right -left; i++){
            if (logAllowed) {
                System.out.println("[H2b][02][02] MERGE: FOR LOOP: i(new location of element): " + i );
                System.out.println(" ");
            }
            if (pr>right || (pl <= middle && (
                comparator.compare(sortList.get(pl), sortList.get(pr)) <= 0
                //do we need to seperate that into two pieces?
                ))){

                if (logAllowed) {
                    System.out.println("[H2b][02][04] MERGE: first if (LEFTPOINTER) WHY:" );
                    if(pr>right){
                        System.out.println(" CAUSE RIGHT ARRAY WAS EMPTY" );
                        System.out.println(" ");
                    }else {
                        System.out.println("left key was smaller/equal than right key: " + sortList.get(pl) + "<=" + sortList.get(pr));
                        System.out.println(" ");
                    }


                    System.out.println("[H2b][02][05] MERGE: first if,in temp array we put LEFT-POINTER in location i: " + i + "last index: " + (right-left) );
                }
                B.set(i,sortList.get(pl));

                pl = pl +1;

            }else {
                if (logAllowed) {
                    System.out.println("[H2b][02][06] MERGE: secondif/else (RIGHTPOINTER) WHY: PR IS NOT EMPTY +");
                    System.out.println("left key was BIGGER than right key: " + sortList.get(pl) + ">" + sortList.get(pr));

                    System.out.println("[H2b][02][07] MERGE: secondif ,in temp array we put RIGHT-POINTER in location i: " + i );
                }
                B.set(i,sortList.get(pr));
                pr = pr + 1;
            }
        }

        if (logAllowed) {
            System.out.println("[H2b][02][07] MERGE: CREATING A TEMP ARRAY WAS SUCCESFULL,TEMP ARRAY:");
            System.out.println(B.toString());
        }

        for (int i1= 0; i1 < right-left + 1; i1++){
            sortList.set(i1+left, B.get(i1));
        }
    }

    /**
     * Sorts the given {@link SortList} using the bubbleSort algorithm.
     * It will only consider the elements between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     *
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the list to be sorted.
     * @param right The rightmost index of the list to be sorted.
     */
    public void bubbleSort(SortList<T> sortList, int left, int right) {

        //LOG
        // 05.05.24 16:28 functioning well with integers, disabling logging
        // 05.05.24 17:22 activated logging for following merge function iterations

        if (logAllowed) {
            System.out.println("[H2a][01] Bubble Sort has been called with parameters left:" + left + "right: " + right);
            System.out.println(" ");
        }

        for (int i = right; i >= left; i--) {
            if (logAllowed) {
                System.out.println("[H2a][02] Bubble Sort : First for loop, current i :" + i );
                System.out.println(" ");
            }
            //09.05 00:57 i to i-1, was false rectracted
            for (int j = left; j < i; j++) {
                if (logAllowed) {
                    System.out.println("[H2a][03] Bubble Sort : second for loop, current j :" + j );
                    System.out.println(" ");
                }
                if (comparator.compare(sortList.get(j), sortList.get(j + 1)) > 0) {
                    // 09.05.24 01:03 #todo: FATAL ERROR: COMMENTS CAUSE MORE READ COUNTS!
                    //System.out.println("[H2a][04] Bubble Sort : if triggered, swapping elements: A: " + sortList.get(j) + "and B: " + sortList.get(j+1) );
                    System.out.println(" ");
                    T tmp = sortList.get(j+1);
                    sortList.set(j+1, sortList.get(j));
                    sortList.set(j, tmp);
                }
            }
        }

    }




}
