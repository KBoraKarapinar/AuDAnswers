package p2.binarytree;

import p2.Node;
import p2.SearchTree;

import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A base implementation of a binary search tree containing common methods.
 * <p>
 * It contains the root node of the tree and provides methods for searching and inserting elements.
 * <p>
 * It assumes that only binary nodes are used, i.e. every node contains exactly one key and has at most two children,
 * where the left child is smaller than the parent and the right child is greater than the parent.
 *
 * @param <T> the type of the keys in the tree.
 * @param <N> the type of the nodes in the tree, e.g., {@link BSTNode} or {@link RBNode}.
 * @see SearchTree
 * @see AbstractBinaryNode
 */
public abstract class AbstractBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> implements BinarySearchTree<T> {

    /**
     * The root node of the tree.
     */
    protected N root;

    @Override
    public N search(T value) {

        N x = root;

        while (x != null && x.getKey().compareTo(value) != 0) {
            if (x.getKey().compareTo(value) > 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        return x;
    }

    /**
     * Inserts the given node into the tree.
     *
     * @param node      the node to insert.
     * @param initialPX The initial value{} used for the pointer to the parent node.
     *                  This is required for implementations that use a sentinel node. For normal trees, this value
     *                  should be {@code null}.
     */
    protected void insert(N node, N initialPX) {

        N x = getRoot(); // pointer x
        N px = initialPX; //pointer px

        while( x != null){
            px = x;
            // x.key > eklenecek key (node)
            if (x.getKey().compareTo(node.getKey()) > 0) {
                x = x.getLeft();
            }else{
                x = x.getRight();
            }

        }

        node.setParent(px);

        //UPDATE 01.06.2024 2153: from px==null to initialpx, it was working fine with bst insert and NOT FINE WITH RB
        if (px == initialPX){
            root = node;
        }else{
            if (px.getKey().compareTo(node.getKey()) > 0){
               px.setLeft(node);
            }else{
                px.setRight(node);
            }
        }



    }

    /**
     * Adds all elements in the subtree represented by the given node to the given list.
     * <p>
     * The elements are added in ascending order.
     * The method adds at most {@code max} elements.
     * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
     * not add any further elements. The first element which did not satisfy the predicate is also excluded.
     * It assumes that the predicate returns {@code false} for all greater values once it returned {@code false} for
     * one value, i.e. it represents a limit check.
     *
     * @param node   The root of the subtree to traverse.
     * @param result The list to store the elements in.
     * @param max    The maximum number of elements to include in the result.
     * @param limit  The predicate to test the elements against. If the predicate returns {@code false} for an element,
     *               the traversal stops.
     */
    protected void inOrder(N node, List<? super T> result, int max, Predicate<? super T> limit) {

        // Update 02.06.24 0708: #todo: we are iterating after ONE: predicate doesnt hold TWO: list is full
                //possible fix: if check before recursive calls
        //Update 02.06.24 0726: nullcheck before continuing with recursive inorders
            // we probs would need to add predicate check
        //Update 02.06.24 0738: result size < max, as it returned +1 element then desired
            //Update predicate+ max check before recurring

        //todo: IF IT NEEDS TRY-CATCH, CHECK EVERY GETX = NULL CONDITION!!!!!!!!!!!!

    if(node == null){
        System.err.println("NULL AT INORDER");
        return;
    }
        try {
            if(node.getLeft() != null ){
                inOrder(node.getLeft(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("LEFT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }



        if(result.size() < max && limit.test(node.getKey())){
                result.add(node.getKey());
            // stop after result max
            if (result.size() >= max) {
                return;
            }
        }else{
            //stop after predicate not holds
            return;
        }

        try {
            if(node.getRight() != null){
                inOrder(node.getRight(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("RIGHT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }




    }

    /**
     * Adds all elements in the tree that are greater than or equal to the given node to the given list.
     * <p>
     * The elements are added in ascending order.
     * The method adds at most {@code max} elements.
     * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
     * not add any further elements. The first element which did not satisfy the predicate is also excluded.
     * It assumes that the predicate returns {@code false} for all greater values once it returned {@code false} for
     * one value, i.e. it represents a limit check.
     *
     * @param node   The node to start the search from. The node itself is included in the search.
     * @param result The list to store the elements in.
     * @param max    The maximum number of elements to include in the result.
     * @param limit  The predicate to test the elements against. If the predicate returns {@code false} for an element,
     *               the traversal stops.
     */
    protected void findNext(N node, List<? super T> result, int max, Predicate<? super T> limit) {

        // 0 : Initial çağırılan node ve sağ çocuğunu ekle
        if(findNextAddNodeAndRightSubTree(node, result, max, limit)){
            return;
        }

        N pointer = node;

        //updte 03.06.24 05:31 parent sentinel de olabilir red black treelerde -> kontrol için getkey
            //todo: bu değer sıkıntılı
                //initial: while(pointer != null && pointer. && pointer.getParent() != null ){

        while(pointer.getParent() != null ){

            if(pointer.getKey() == null){
                return;
            }

                //3: anlık pointer, sol çocuk ise EBEVEYN İÇİN YARDIMCIYI ÇAĞIR ve bir adım yukarı çık
                //yukardakı değeri dede olarak değiştirmen gerekebilir whileda

                if (pointer.equals(pointer.getParent().getLeft())) {
                    pointer = pointer.getParent();
                    if (findNextAddNodeAndRightSubTree(pointer, result, max, limit)) {
                        return;
                    }
                } else {
                    pointer = pointer.getParent();
                }


        }

    }

    private boolean findNextAddNodeAndRightSubTree(N node, List<? super T> result, int max, Predicate<? super T> limit){
        if(node == null){
            return true;
        }
        //if null ends with doing nothing

        if(result.size() < max && limit.test(node.getKey())){
            result.add(node.getKey());
            if (result.size() >= max) {
                //list max
                return true;
            }
        }else{
            //limit doesnt hold
            return true;
        }

        //2: traverse right subtree of current node, if exist
        if(node.hasRight()){
            inOrder(node.getRight(), result, max, limit);
        }

        return false;
    }





    @Override
    public N findSmallest() {
        N x = root;
        while (x.hasLeft()) {
            x = x.getLeft();
        }
        return x;
    }

    @Override
    public N getRoot() {
        return root;
    }

    /**
     * Creates a new node with the given key.
     * <p>
     * The type of the node is determined by the concrete implementation. If the implementation uses additional
     * information within the node, a standard value is used for them, e.g., red for the color of a node in a
     * red-black tree.
     *
     * @param key the key of the new node.
     * @return a new node with the given key.
     */
    protected abstract N createNode(T key);

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (root == null) {
            sb.append("[]");
        } else {
            root.buildString(sb);
        }

        return sb.toString();
    }

    /**
     *     Doesn't stop traversing after predicate doesnt hold.
     *
     * */
    protected void upgradedPredicateInOrder(N node, List<? super T> result, int max, Predicate<? super T> limit) {

        //TODO: ARE YOU EDITING RIGHT INORDER? + ASK TUTOR HOW IT SHOULD FUNCTION?

        if(node == null){
            System.err.println("NULL AT INORDER");
            return;
        }
        try {
            if(node.getLeft() != null ){
                upgradedPredicateInOrder(node.getLeft(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("LEFT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }



        if(result.size() < max && limit.test(node.getKey())){
            result.add(node.getKey());
            // stop after result max
            if (result.size() >= max) {
                return;
            }
        }

        try {
            if(node.getRight() != null){
                upgradedPredicateInOrder(node.getRight(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("RIGHT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }




    }

    protected void upgradedPredicateInOrderNode(N node, List<BinaryNode<T>> result, int max, Predicate<? super T> limit) {

        //TODO: ARE YOU EDITING RIGHT INORDER? + ASK TUTOR HOW IT SHOULD FUNCTION?

        if(node == null){
            System.err.println("NULL AT INORDER");
            return;
        }
        try {
            if(node.getLeft() != null ){
                upgradedPredicateInOrderNode(node.getLeft(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("LEFT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }



        if(result.size() < max && limit.test(node.getKey())){
            result.add(node);
            // stop after result max
            if (result.size() >= max) {
                return;
            }
        }

        try {
            if(node.getRight() != null){
                upgradedPredicateInOrderNode(node.getRight(), result, max, limit);
            }
        }
        catch(Exception e) {
            System.out.println("RIGHT NODE IS EMPTY");
            System.out.println(e.getMessage());
        }




    }



}
