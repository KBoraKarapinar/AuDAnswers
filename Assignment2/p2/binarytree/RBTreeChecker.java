package p2.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for checking the rules of a red-black tree.
 */
public class RBTreeChecker {

boolean log1 = false;
static List<Integer> leafDepths;

    /**
     * Checks if the given tree satisfies all the rules of a red-black tree.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy any of the rules.
     */
    public static void checkAllRules(RBTree<?> rbTree) {
        checkRule1(rbTree);
        checkRule2(rbTree);
        checkRule3(rbTree);
        checkRule4(rbTree);
    }

    /**
     * Checks if the given tree satisfies the first rule of black tree.
     * <p>
     * The first rule of a red-black tree states that every node is either red or black, i.e. its color is not {@code null}.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule1(RBTree<?> rbTree) {

        preorderrule1(rbTree.getRoot());

    }

    /**
     * Checks if the given tree satisfies the second rule of black tree.
     * <p>
     * The second rule of a red-black tree states that the root of the tree is black.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule2(RBTree<?> rbTree) {


        //Update 31.05.2024 2220: adding case root=nil is true, as nil is black.


        if(rbTree.getRoot() != null){
            if (rbTree.getRoot().getColor() != Color.BLACK){
                throw new RBTreeException("Root color is not black");
            }
        }


    }

    /**
     * Checks if the given tree satisfies the third rule of black tree.
     * <p>
     * The third rule of a red-black tree states that no red node has a red child.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule3(RBTree<?> rbTree) {
        preorderrule3(rbTree.getRoot());
    }

    /**
     * Checks if the given tree satisfies the fourth rule of black tree.
     * <p>
     * The fourth rule of a red-black tree states that all paths from a node to a leave or half-leave contain the same number of black nodes.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule4(RBTree<?> rbTree) {

        // LOGIC 31.05.24: creating new arraylist every time checkrule4 called to reset the list, todo: is logical?
        leafDepths = new ArrayList<>();
        preorderrule4(rbTree.getRoot(),0);


        //todo: possible error, we don't count the half leafs
        //UPDATE 31.05.24 2314: currently passing the current tests
        if(leafDepths.stream().anyMatch( n ->n !=  leafDepths.getFirst())){
            throw new RBTreeException("Not all black depths are same");
        }


    }


    private static void preorderrule1(RBNode<?> currentPointer ){

        //For checkrule 1, AT 31.05.2024 functioning

        if (currentPointer != null){

            if (currentPointer.getColor() == null){
                throw new RBTreeException("The color of node is NOT DEFINED at :" + currentPointer.toString());
            }

            preorderrule1(currentPointer.getLeft());
            preorderrule1(currentPointer.getRight());

        }

    }


    private static void preorderrule3(RBNode<?> currentPointer ){

        //For checkrule 1, AT 31.05.2024 functioning

        if (currentPointer != null){

            RBNode<?> left = null;
            RBNode<?> right = null;

            if (currentPointer.hasLeft()){
                 left = currentPointer.getLeft();
            }

            if (currentPointer.hasRight()){
                right = currentPointer.getRight();
            }

            if (currentPointer.getColor() == Color.RED){

                if (currentPointer.hasLeft() && left.getColor() == Color.RED ){
                    throw new RBTreeException("The left child of a red node is RED at (currentpointer) :" + currentPointer.toString());
                }

                if (currentPointer.hasRight() && right.getColor() == Color.RED ){
                    throw new RBTreeException("The right child of a red node is RED at (currentpointer) :" + currentPointer.toString());
                }


            }

            preorderrule3(left);
            preorderrule3(right);

        }




    }

    private static void preorderrule4(RBNode<?> currentPointer, int blackDepthAtLevel){

        //initially level = 0
        int blackDepth = blackDepthAtLevel;

        if (currentPointer != null){

            if (currentPointer.getColor() == Color.BLACK){
                blackDepth = blackDepth + 1;
            }
            if (currentPointer.getColor() == Color.RED){
                System.out.println("Red, depth remains same");
            }
            // if leaf
            if(!currentPointer.hasRight() && !currentPointer.hasLeft()){
                leafDepths.add(blackDepth);
            }


            preorderrule4(currentPointer.getLeft(),blackDepth);
            preorderrule4(currentPointer.getRight(),blackDepth);

        }

    }

    /* UPDATE NOW 05.06.24 0248: NOW IN RBTREE
    //todo 05.06.2024: whether we return the exact black depth or +1
    public static int Rule4DepthReturner(RBTree<?> rbTree){
        leafDepths = new ArrayList<>();
        preorderrule4(rbTree.getRoot(),0);


        //todo: possible error, we don't count the half leafs
        //UPDATE 31.05.24 2314: currently passing the current tests
        if(leafDepths.stream().anyMatch( n ->n !=  leafDepths.getFirst())){
            throw new RBTreeException("Not all black depths are same");
        }else{
            return leafDepths.getFirst();
        }



    }*/




}
