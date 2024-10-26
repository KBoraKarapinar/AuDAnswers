package p2;

import javafx.application.Application;
import p2.binarytree.*;
import p2.gui.MyApplication;
import p2.gui.TreeStyle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * <h2>GUI Guide:</h2>
     * <p>
     * After starting the gui you can load an empty red-black or simple binary search tree by clicking the respective
     * buttons. When entering a string representation of a tree used in the test, that tree will be loaded instead.
     * <p>
     * When a tree is loaded, you can view it in the center. On the top right you can enter input values and execute
     * the respective operation by clicking the buttons below.
     * <ul>
     *     <li>Inserts: Invokes insert with the value given in "Value".</li>
     *     <li>In Order: Invokes inOrder with the value given in "Value" as the start node, "Max" as the maximum amount
     *     of values to return and {@code x -> x <= "Limit"} as the predicate.</li>
     *     <li>Find Next: Invokes findNext with the value given in "Value" as the start node, "Max" as the maximum amount
     *      of values to return and {@code x -> x <= "Limit"} as the predicate.
     *      <li>Join: Invokes join with the value given in "Value" as the join key, and "Join Tree" as other tree to
     *      join with.
     * </ul>
     * <p>
     * When the "Animate" checkbox at the bottom left is selected, the program will stop after each invocation of
     * {@code {get,set}{Left,Right,Parent}} and {@code setColor} and highlight the respective nodes. You can then continue the
     * animation by clicking the "Next Step" button at the bottom left. When stopped, the current stack trace and the
     * last performed operation is shown at the top right.
     * <p>
     * You can change the appearance and colors of the tree in the class {@link TreeStyle}.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {

        // Uncomment the following line to run the AutoComplete example for task H3 d)
        //autoCompleteExample();

         /*RBTree<String> myTree = new RBTree<>();
       String[] wordlist = new String[]{"a", "b","c","ca","cde","cze","d","e","f","g","ha","i","j","k","l","m","n","z"};
       for(String i: wordlist){
           myTree.insert(i);
       }
        ArrayList<String> result = new ArrayList<>();
        ArrayList<BinaryNode<String>> result3 = new ArrayList<>();
       System.out.println( myTree.toString());
        myTree.upgradedPredicateInOrder(myTree.getRoot(),result,300,x-> x.startsWith("c"));
        System.out.println(result);

        myTree.upgradedPredicateInOrderNode(myTree.getRoot(),result3,300,x-> x.startsWith("c"));
        System.out.println(result3);
        System.out.println(result3.get(0).getParent());*/





        //System.out.println(result.stream().filter(n -> n.startsWith("h")).findFirst().get());
       //result.clear();
       // myTree.inOrder(myTree.getRoot(),result,300,(n) -> n.length() == 1 );



    boolean callme = false;

        if(callme) {
            RBTree<Integer> myIntTree = new RBTree<>();
            int[] intlist = new int[]{1, 2, 3, 5, 7, 9, 11, 14, 16, 17, 9, 22, 25, 27, 28, 29, 31, 32, 45, 34, 35, 56, 78, 64, 35, 43, 24, 2, 1, 45, 6, 5, 43, 2, 24, 54, 64, 234, 54, 32, 34, 56, 7, 54,};
            for (int i : intlist) {
                myIntTree.insert(i);
            }
            /*ArrayList<Integer> result2 = new ArrayList<>();
            System.out.println(myIntTree.toString());
            myIntTree.inOrder(myIntTree.getRoot(), result2, 300, x -> true);
            System.out.println(result2);
            result2.clear();
            //todo: WHEN 2, IT DOESNT WORK, MISSES 3??
            //x'in sağına inmiyor
            myIntTree.upgradedPredicateInOrder(myIntTree.getRoot(), result2, 300, x -> x > 2);
            System.out.println(result2);*/

            ArrayList<RBNode<Integer>> result3 = new ArrayList<>();


            int totalBlackHeight = myIntTree.blackHeight();
            System.out.println("black height =" + totalBlackHeight);
            int targetBlackHeight = 3;
            int targetValueTranslator = totalBlackHeight + 1 - targetBlackHeight;


            myIntTree.preorderRule4WithLimitAndPredicateReturnsNode(myIntTree.getRoot(), 0, result3, Integer.MAX_VALUE, n -> n.isBlack(), targetValueTranslator, true);
            System.out.println(result3);
        }

        RBTree<Integer> valueSmallBlackDepthHigh = new RBTree<>();
        TreeParser.parseRBTree("[[[,2,B,],3,B,[,4,B,]],5,B,[[[,6,B,],12,R,[,14,B,]],18,B,[[,22,B,],27,R,[[,54,R,],66,B,[,99,R,]]]]]", Integer::parseInt, valueSmallBlackDepthHigh);

        RBTree<Integer> valueHighBlackDepthLowLarge = new RBTree<>();
        TreeParser.parseRBTree("[[,210,B,],232,B,[[,297,R,],303,B,[,392,R,]]]", Integer::parseInt, valueHighBlackDepthLowLarge);

        RBTree<Integer> valueHighBlackDepthLowOneNode = new RBTree<>();
        TreeParser.parseRBTree("[,232,B,]", Integer::parseInt, valueHighBlackDepthLowOneNode);

        System.out.println(makeGreen("Previous") + valueSmallBlackDepthHigh.toString());
        valueSmallBlackDepthHigh.join(valueHighBlackDepthLowLarge,200);
        System.out.println(makeGreen("After Join") + valueSmallBlackDepthHigh.toString());



        Application.launch(MyApplication.class, args);


    }

    private static void autoCompleteExample() {
        String fileName = "words_alpha.txt";
        String prefix = "z";
        int max = 10;

        System.out.println(makeGreen("\n=== AutoComplete Example ==="));

        long lineCount;
        try (Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(Main.class.getResource(fileName)).toURI()))) {
            lineCount = stream.count();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println(makeGreen("->") + " Input file: " + makeYellow(fileName)  + " with " + makeYellow(Long.toString(lineCount)) + " words");
        System.out.println(makeGreen("->") + " Prefix: " + makeYellow(prefix));
        System.out.println(makeGreen("->") + " Max: " + makeYellow(Integer.toString(max)));

        System.out.println(makeGreen("->") + " Running using " + makeYellow("Red-Black Tree") + ":");
        runAutoComplete(fileName, prefix, max, true);

        System.out.println(makeGreen("->") + " Running using " + makeYellow("Simple Binary Tree") + ":");
        runAutoComplete(fileName, prefix, max, false);

        System.out.println(makeGreen("============================"));
    }

    private static void runAutoComplete(String fileName, String prefix, int max, boolean useRBTree) {

        String running = "<Runnning>";

        System.out.print(makeGreen("    ->") + " Initialization time: ");
        System.out.print(makeRed(running));

        AutoComplete acRBTree = new AutoComplete(fileName, useRBTree);

        for (int i = 0; i < running.length(); i++) {
            System.out.print("\b");
        }

        System.out.printf(makeYellow("%.2fms\n".formatted(acRBTree.getInitializationTime() / 1000000d)));


        System.out.printf(makeGreen("    ->") + " Computation time:    ");
        System.out.print(makeRed(running));

        List<String> result = acRBTree.autoComplete(prefix, max);

        for (int i = 0; i < running.length(); i++) {
            System.out.print("\b");
        }

        System.out.printf(makeYellow("%.2fms\n".formatted(acRBTree.getLastComputationTime() / 1000000d)));
        System.out.printf(makeGreen("    ->") + " Results:             " + makeYellow("%s\n".formatted(result)));
    }

    private static String makeYellow(String str) {
        return "\u001B[33m" + str + "\u001B[0m";
    }

    private static String makeGreen(String str) {
        return "\u001B[32m" + str + "\u001B[0m";
    }

    private static String makeRed(String str) {
        return "\u001B[31m" + str + "\u001B[0m";
    }
}
