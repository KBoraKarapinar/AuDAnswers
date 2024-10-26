package p2.binarytree;

import p2.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An implementation of a red-black tree.
 * <p>
 * A red-black tree is a self-balancing binary search tree. It guarantees that the searching and inserting operation
 * have a logarithmic time complexity.
 *
 * @param <T> The type of the keys in the tree.
 * @see AbstractBinarySearchTree
 * @see RBNode
 */
public class RBTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, RBNode<T>> {

    boolean shouldLog = false;
    List<Integer> leafDepths;

    /**
     * The sentinel node of the tree.
     * <p>
     * The sentinel node is a special node that is used to simplify the implementation of the tree. It is a black
     * node that is used as the parent of the root node and is its own child. It is not considered part of the tree.
     */
    protected final RBNode<T> sentinel = new RBNode<>(null, Color.BLACK);

    /**
     * Creates a new, empty red-black tree.
     */
    public RBTree() {
        sentinel.setParent(sentinel);
        sentinel.setLeft(sentinel);
        sentinel.setRight(sentinel);
    }

    @Override
    public void insert(T value) {
        //todo: is logical?

        RBNode<T> nodeToBeAdded = createNode(value);

       super.insert(nodeToBeAdded,sentinel);
       fixColorsAfterInsertion(nodeToBeAdded);



    }

    /**
     * Ensures that the red-black tree properties are maintained after inserting a new node, which might have
     * added a red node as a child of another red node.
     *
     * @param z The node that was inserted.
     */
    protected void fixColorsAfterInsertion(RBNode<T> z) {

        //todo: is there a neccesity to reset?
        //y, to hold uncle
        RBNode<T> y = null;
        // 4 cases
            // z = root
            // z.uncle = red
            // z.uncle = black + triangle
            // z.uncle =

        while (RBisRed(z.getParent())){

            if (shouldLog){
                System.out.println("[][][1] FIXCOLOR: While Head, z = " + z.toString());
                System.out.println("");
            }

            if (z.getParent().equals(z.getParent().getParent().getLeft())){
                if (shouldLog){
                    System.out.println("[][][2] FIXCOLOR: , FATHER, LEFT CHILD OF GRANDFATHER ");
                    System.out.println("");

                }
                //UPDATE 01.06.2024 2159: WHILE CONTROLLNG THE COLOUR OF NULL NODES WE HAVE TO CONSIDER NULL NODES BEING BLACK
                //you can't update node formula as it cant check whether ist is null

                y = z.getParent().getParent().getRight(); //uncle is grandparents right

                if(RBisRed(y)){
                    if (shouldLog){
                        System.out.println("[][][3] FIXCOLOR: PARENTATLEFT: CASE1: UNCLE IS RED ");
                        System.out.println("                              : PARENT+UNCLE = SET TO BLACK, GRANDPARENT SET TO RED ");
                        System.err.println("                              : new z is GRANDPARENT!! ");
                        System.out.println("");
                    }
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                }else{
                    if (shouldLog){
                        System.out.println("[][][4] FIXCOLOR: PARENTATLEFT:  CASE2+3: UNCLE IS BLACK ");
                        System.out.println("");
                    }

                    if(z.equals(z.getParent().getRight())){ //z = z.p.right
                        if (shouldLog){
                            System.out.println("[][][5] FIXCOLOR: PARENTATLEFT: CASE2: TRIANGLE!! ");
                            System.out.println("                              : CURRENT Z IS RIGHT CHILD OF ITS PARENT + ALREADY BECOMES LEFT CHILD OF PARENT");
                            System.err.println("                              : ASSIGN Z TO Y'S PARENT AND CONTINUE!!!!!!");
                            System.out.println("                              : AFTER ASSIGNMENT, ROTATE-LEFT");
                            System.out.println("");
                        }
                        z = z.getParent();
                        rotateLeft(z);

                    }//end of case2

                    if (shouldLog){
                        System.out.println("[][][6] FIXCOLOR: PARENTATLEFT: CASE3: LINE ");
                        System.out.println("                                      : CASE2 MAY HAVE ALREADY BEEN APPLIED ");
                        System.out.println("                                      : CURRENT Z MAKES ITS PARENT BLACK, MAKES ITS GRANDPARENT RED ");
                        System.out.println("                                      : ROTATE-RIGHT AT GRANDPARENT ");
                        System.out.println("");
                    }

                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rotateRight(z.getParent().getParent());


                } //end of case 3
            }// end of baba sol cocuk
            else{ //start baba sag cocuk

                if (shouldLog){
                    System.out.println("[][][7] FIXCOLOR: , BABA, DEDENIN SAG COCUGU ");
                    System.out.println("");
                }
                y = z.getParent().getParent().getLeft(); //uncle is grandparents LEFT

                if(RBisRed(y)){
                    if (shouldLog){
                        System.out.println("[][][8] FIXCOLOR: PARENTATRIGHT: CASE1: UNCLE IS RED ");
                        System.out.println("                              : PARENT+UNCLE = SET TO BLACK, GRANDPARENT SET TO RED ");
                        System.err.println("                              : new z is GRANDPARENT!! ");
                        System.out.println("");
                    }
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                }else{
                    if (shouldLog){
                        System.out.println("[][][9] FIXCOLOR: PARENTATRIGHT:  CASE2+3: UNCLE IS BLACK ");
                        System.out.println("");
                    }

                    if(z.equals(z.getParent().getLeft())){ //z = z.p.left
                        if (shouldLog){
                            System.out.println("[][][10] FIXCOLOR: PARENTATRIGHT: CASE2: TRIANGLE!! ");
                            System.out.println("                                : CURRENT Z IS LEFT CHILD OF ITS PARENT + ALREADY BECOMES RIGHT CHILD OF PARENT");
                            System.err.println("                                : ASSIGN Z TO Y'S PARENT AND CONTINUE!!!!!!");
                            System.out.println("                                : AFTER ASSIGNMENT, ROTATE-RIGHT");
                            System.out.println("");
                        }
                        z = z.getParent();
                        rotateRight(z);

                    }//end of case2

                    if (shouldLog){
                       System.out.println("[][][11] FIXCOLOR: PARENTATRIGHT: CASE3: LINE ");
                        System.out.println("                                        : CASE2 MAY HAVE ALREADY BEEN APPLIED ");
                        System.out.println("                                        : CURRENT Z MAKES ITS PARENT BLACK, MAKES ITS GRANDPARENT RED ");
                        System.out.println("                                        : ROTATE-LEFT AT GRANDPARENT ");
                        System.out.println("");
                    }

                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rotateLeft(z.getParent().getParent());


                } //end of case 3






            } // en of baba sag cocuk

        }//end of while z = red

        getRoot().setColor(Color.BLACK);
        if (shouldLog){
            System.out.println("[][][12] FIXCOLOR: WHILE ENDED, ROOT COLOR: BLACK ");
            System.out.println("");
        }

    }

    /**
     * Rotates the given node to the left by making it the left child of its previous right child.
     * <p>
     * The method assumes that the right child of the given node is not {@code null}.
     *
     * @param x The node to rotate.
     */
    protected void rotateLeft(RBNode<T> x) {

        // node parameters
            //color specially, from abstract binary node
                // key, left, right, parent

        // Left rotate
            // -> Move y up; y = x.right
            // -> Move x to the left; x = the node to be moved

        RBNode<T> y = x.getRight();
        x.setRight(y.getLeft());
        if(y.getLeft() != null){
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());

        //todo: do we need to do a check for whether we used a sentinel?
            //todo: RB TREEde olduğumuz için değil, ama normalde NULLla da çalışmalı
        if(x.getParent() == sentinel){
            root = y;
        }else{
            if(x.equals(x.getParent().getLeft())){
                x.getParent().setLeft(y);
            }else{
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);
        x.setParent(y);



    }

    /**
     * Rotates the given node to the right by making it the right child of its previous left child.
     * <p>
     * The method assumes that the left child of the given node is not {@code null}.
     *
     * @param x The node to rotate.
     */
    protected void rotateRight(RBNode<T> x) {

        
        RBNode<T> y = x.getLeft(); 
        x.setLeft(y.getRight()); 
        if(y.getRight() != null){
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent()); //DEĞİŞTİRMEDİK

        //todo: do we need to do a check for whether we used a sentinel?
        //todo: RB TREEde olduğumuz için değil, ama normalde NULLla da çalışmalı
        if(x.getParent() == sentinel){ //Değiştirmedik
            root = y;
        }else{
            if(x.equals(x.getParent().getLeft())){
                x.getParent().setLeft(y);
            }else{
                x.getParent().setRight(y);
            }
        }
        y.setRight(x);
        x.setParent(y);



    }

    @Override
    public void inOrder(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.inOrder(rbNode, result, max, predicate);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    @Override
    public void upgradedPredicateInOrder(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.upgradedPredicateInOrder(rbNode, result, max, predicate);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    @Override
    public void upgradedPredicateInOrderNode(Node<T> node, List<BinaryNode<T>> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.upgradedPredicateInOrderNode(rbNode, result, max, predicate);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    @Override
    public void findNext(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.findNext(rbNode, result, max, predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    /**
     * Joins this red-black tree with another red-black tree by inserting a join-key.
     * <p>
     * The method assumes that both trees are non-empty and every element in this tree is less than every element in
     * the other tree. Additionally, it assumes that the join-key is greater than every element in this tree and less
     * than every element in the other tree.
     * <p>
     * The method modifies the tree in place, so that the other tree is merged into this tree. The other tree is
     * effectively destroyed in the process, i.e. it is not guaranteed that it is a valid red-black tree anymore.
     * <p>
     * It works by first finding two nodes in both trees with the same black height. For the tree with the smaller black
     * height, this is the node root node. For the tree with the larger black height, this is the largest or smallest
     * node with the same black height as the root node of the other tree. Then, it creates a new, red, node with the
     * join-key as the key and makes it the parent of the two previously found nodes. Finally, it fixes the colors of
     * the tree to ensure that the red-black tree properties are maintained.
     *
     * @param other   The other red-black tree to join with this tree.
     * @param joinKey The key to insert into the tree to join the two trees.
     */
    public void join(RBTree<T> other, T joinKey) {

        int currentSmallBlackHeight = blackHeight();
        RBTree<T> otherBig = other;
        RBTree<T> currentSmall = this;
        int otherBigBlackHeight = other.blackHeight();
        int targetBlackDepth = -1; //allways the small one
        RBNode<T> targetNode = null;
        RBNode<T> joinNode = createNode(joinKey);
        RBNode<T> OldParentOfTargetNode = null;

        //cases

        //case1: black height of other/bigger tree is bigger (as in the blatt)
                //find the smallest in the OTHER tree
        if(otherBigBlackHeight > currentSmallBlackHeight){

            targetBlackDepth = currentSmallBlackHeight;
            targetNode = otherBig.findBlackNodeWithBlackHeight(targetBlackDepth,otherBigBlackHeight,true);

            // left child of join node: root of the current/small
            joinNode.setLeft(currentSmall.root);
            currentSmall.root.setParent(joinNode);

            // right child of join node: smallest one in the big/other AKA targetnode
            OldParentOfTargetNode = targetNode.getParent();
            joinNode.setRight(targetNode);
            targetNode.setParent(joinNode);

            //as it was the SMALLEST, it is a left child, left side of the OLD parent of target is empty, join goes there
            OldParentOfTargetNode.setLeft(joinNode);
            joinNode.setParent(OldParentOfTargetNode);


            //update 07.07.24: possible error here, but we cant update sentinel
                    //we have to use the sentinel of currentsmall


            //now root of the new tree is, root of the one with HIGHER black depth
            otherBig.root.setParent(currentSmall.sentinel);
            root = otherBig.getRoot();


            //call fixup with "neu erstellten knoten"
            fixColorsAfterInsertion(joinNode);




        }
        //case2:
        else if (currentSmallBlackHeight > otherBigBlackHeight) {
            //todo: whether we need to check if root is affected, probs no

            targetBlackDepth = otherBigBlackHeight;
            targetNode = currentSmall.findBlackNodeWithBlackHeight(targetBlackDepth,currentSmallBlackHeight,false);


            //right child of join = other big tree, entirely
            joinNode.setRight(otherBig.getRoot());
            otherBig.root.setParent(joinNode);

            //old parent of target node
            OldParentOfTargetNode = targetNode.getParent();

            //left child of join = target node
            joinNode.setLeft(targetNode);
            targetNode.setParent(joinNode);

            //joinnode is right child of the old parent
            OldParentOfTargetNode.setRight(joinNode);
            joinNode.setParent(OldParentOfTargetNode);

            //no root initialization as we havent changed

            //call fixup
            fixColorsAfterInsertion(joinNode);





        }
        //case 3: blackheight same: make join root and create left/right
        else {

            //set current/small to the left of joinnode
            joinNode.setParent(currentSmall.sentinel);
            joinNode.setLeft(currentSmall.getRoot());
            currentSmall.getRoot().setParent(joinNode);

            //set otherbig to the right of joinnode
            joinNode.setRight(otherBig.getRoot());
            otherBig.root.setParent(joinNode);

            //make joinnode root
            currentSmall.root = joinNode;

            //call fixup
            fixColorsAfterInsertion(joinNode);


        }




    }

    /**
     * Returns the black height of the tree, i.e. the number of black nodes on a path from the root to a leaf.
     *
     * @return the black height of the tree.
     */
    public int blackHeight() {
       return Rule4DepthReturner(this);
    }

    /**
     * Finds a black node with the given black height in the tree.
     * <p>
     * Depending on the value of the {@code findSmallest} parameter, the method finds the smallest or largest node with the
     * target black height.
     * <p>
     * It assumes that the tree is non-empty and that there is a node with the target black height.
     *
     * @param targetBlackHeight The target black height to find a node with.
     * @param totalBlackHeight  The total black height of the tree.
     * @param findSmallest      Whether to find the smallest or largest node with the target black height.
     * @return A black node with the target black height.
     */
    public RBNode<T> findBlackNodeWithBlackHeight(int targetBlackHeight, int totalBlackHeight, boolean findSmallest) {

        List<RBNode<T>> result = new ArrayList<RBNode<T>>();

        //mathematical update 05.06.2024 04:22
        //due to your preorder, TARGETVALUE + VALUEPREODERFINDS = BLACKHEIGHT + 1
        //we must give valuepreoderfinds as parameter,
            // so to reach the real values, we must use

        int targetValueTranslator = totalBlackHeight + 1 - targetBlackHeight;

        preorderRule4WithLimitAndPredicateReturnsNode(getRoot(),0,result,Integer.MAX_VALUE,x-> x.isBlack(),targetValueTranslator,true);
        if(findSmallest){
            return result.get(0);
        }else{
            return result.getLast();
        }

    }

    //todo: not done
    private void preorderrule4(RBNode<?> currentPointer, int blackDepthAtLevel){

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

    //todo: not done

    /**
     *
     * @param currentPointer initally root to treverse entire tree
     * @param blackDepthAtLevel must be 0, when started at root
     * @param result a list that contains nodes with given presquities
     * @param max desired total amount of element
     * @param generalLimit a general predicate to limit the result set, PREDICATE APPLIES TO RBNODE (to only contain black nodes: n ->n.isBlack()
     * @param blackDepthLimit integer, speficic black depth
     * @param checkDepthLimit true, if it should only add nodes in a specific black depth
     */
    public void preorderRule4WithLimitAndPredicateReturnsNode(RBNode<T> currentPointer, int blackDepthAtLevel,List<RBNode<T>> result, int max, Predicate<? super RBNode<T>> generalLimit, int blackDepthLimit,boolean checkDepthLimit){

        leafDepths = new ArrayList<>();

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
                if(result.size() < max && generalLimit.test(currentPointer)){
                    if(checkDepthLimit){
                        //todo: which pointer is to be used: so far seems ok!
                        if (blackDepth == blackDepthLimit){
                            result.add(currentPointer);
                        }
                    }else{
                        result.add(currentPointer);
                    }


                    // stop after result max
                    if (result.size() >= max) {
                        return;
                    }
                }


                preorderRule4WithLimitAndPredicateReturnsNode(currentPointer.getLeft(),blackDepth,result,max,generalLimit,blackDepthLimit,checkDepthLimit);
                preorderRule4WithLimitAndPredicateReturnsNode(currentPointer.getRight(),blackDepth,result,max,generalLimit,blackDepthLimit,checkDepthLimit);

            }else{
                return;
            }









    }

    //todo 05.06.2024: whether we return the exact black depth or +1
    public  int Rule4DepthReturner(RBTree<?> rbTree) {
        leafDepths = new ArrayList<>();
        preorderrule4(rbTree.getRoot(), 0);


        //todo: possible error, we don't count the half leafs
        //UPDATE 31.05.24 2314: currently passing the current tests
        if (leafDepths.stream().anyMatch(n -> n != leafDepths.getFirst())) {
            throw new RBTreeException("Not all black depths are same");
        } else {
            return leafDepths.getFirst();
        }
    }



    @Override
    protected RBNode<T> createNode(T key) {
        return new RBNode<>(key, Color.RED);
    }


    public static boolean RBisBlack(RBNode<?> node){

        if(node == null){
            return true;
        } else return node.isBlack();

    }

    public static boolean RBisRed(RBNode<?> node){

        if(node == null){
            return false;
        } else return node.isRed();

    }

}
