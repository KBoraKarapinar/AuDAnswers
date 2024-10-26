package p1;

import p1.sort.*;
import p1.sort.radix.IntegerIndexExtractor;
import p1.sort.radix.LatinStringIndexExtractor;
import p1.sort.radix.RadixSort;

import java.awt.dnd.DragSourceMotionListener;
import java.util.List;
import java.util.Random;

/**
 * Main entry point in executing the program.
 */
public class Main {

    private static final Random random = new Random();

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {


        //test for hybridsort
        //Passed with K:1,2,32 now 05.05.24 at 16:32
        //FAILEd, actually
        //Passed with k:1,2,3,52 05.05.24 at 18:36
        //PROBS DOESNT WORK FOR K=0
        //NOW at 050524 at 18:51 WITH  anchoring the k=0 with direct trigger of bubble sort it works with k:0 aswell.
        /*SortList<Integer> list = createRandomList();
        //ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{10,6,8,5,7,3,4});
        System.out.println(list.toString());
        HybridSort<Integer> hybridSort = new HybridSort<>(0,Integer::compareTo);
        hybridSort.sort(list);
        System.out.println(list.toString());*/

        //test for bubblesort
        //passed, functioning well with integers now 05.05.24 at 16:29
        //SortList<Integer> list = createRandomList();
        /*ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{3,2,1});
        System.out.println(list.toString());
        HybridSort<Integer> hybridSort = new HybridSort<>(1,Integer::compareTo);
        hybridSort.bubbleSort(list,0, list.getSize()-1);
        System.out.println(list.toString());*/

        //test for hybrid-optimizer
        //09.05.24 04.30 Update: we are still not sure.
        //SortList<Integer> list = createRandomList();
        /*ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{5, 3, 8, 2, 7});
        System.out.println(list.toString());
        HybridSort<Integer> hybridSort = new HybridSort<>(32,Integer::compareTo);

        int k = HybridOptimizer.optimize(hybridSort,list.toArray());
        //.sort(list);
        System.out.println(k);*/

        /*IntegerIndexExtractor myIndexExtractor = new IntegerIndexExtractor(10);
        System.out.println(myIndexExtractor.extractIndex(545,2));
        System.out.println(Math.pow(10,2));*/

        //test for integer radix sort

        //now works with single digit numbers at 09.05.24 04:49
        /*ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{5, 3, 8, 2, 7});
        RadixSort<Integer> radixSort = new RadixSort<Integer>(10,new IntegerIndexExtractor(10));
        radixSort.setMaxInputLength(1);
        radixSort.sort(list);
        System.out.println(list.toString());*/

        //now works with two digit numbers at 09.05.24 04:50
        /*ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{15, 23, 48, 62, 17});
        RadixSort<Integer> radixSort = new RadixSort<Integer>(10,new IntegerIndexExtractor(10));
        radixSort.setMaxInputLength(2);
        radixSort.sort(list);
        System.out.println(list.toString());*/

        //now works with complex integers
        /*ArraySortList<Integer> list = new ArraySortList<Integer>(new Integer[]{1235, 22323, 48, 2, 14327});
        RadixSort<Integer> radixSort = new RadixSort<Integer>(10,new IntegerIndexExtractor(10));
        radixSort.setMaxInputLength(5);
        radixSort.sort(list);
        System.out.println(list.toString());
         */

        //latin extractor test

        String test = "t"; //97-122

        LatinStringIndexExtractor myExtractor = new LatinStringIndexExtractor();
        int result = myExtractor.extractIndex(test,0);
        int value = test.charAt(0);
        System.out.println(value);
        System.out.println(result);









        //hybridSort();
        //radixSort();
    }

    private static void hybridSort() {

        SortList<Integer> list = createRandomList();

        HybridSort<Integer> hybridSort = new HybridSort<>(1, Integer::compareTo);


        int k = HybridOptimizer.optimize(hybridSort, list.toArray());
        System.out.println("first local minimum: " + k);

        hybridSort.setK(k);
        hybridSort.sort(list);

        System.out.println("hybridSort comparisons: " + hybridSort.getComparisonsCount());
    }

    private static void radixSort() {

        List<String> strings = List.of("FOP", "Mathe1", "DT", "AFE", "AUD", "Mathe2", "RO", "APL");
        SortList<String> list = new ArraySortList<>(strings);

        RadixSort<String> radixSort = new RadixSort<>(26, new LatinStringIndexExtractor());
        radixSort.setMaxInputLength(strings.stream().mapToInt(String::length).max().orElse(0));

        radixSort.sort(list);

        System.out.println("radixSort: " + list);
    }

    private static SortList<Integer> createRandomList() {
        int size = 100;
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }

        return new ArraySortList<>(array);
    }
}
