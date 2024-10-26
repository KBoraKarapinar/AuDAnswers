package p2;

import org.jetbrains.annotations.NotNull;
import p2.binarytree.AutoComplete;
import p2.binarytree.RBTree;

import java.util.function.Predicate;

public class MainSecond {

    //standart tree set: [[[[,4,R,],5,B,[,11,R,]],12,B,[,15,B,]],19,B,[[[,22,R,],23,B,],43,B,[[,43,B,],54,R,[[,91,R,],102,B,[,105,R,]]]]]


    public static void main(String[] args) {





    }


    private static void test1(){

        RBTree test = new RBTree<Integer>();


    }

}

class deleteMe implements Predicate<Integer>{


    /**
     * Evaluates this predicate on the given argument.
     *
     * @param integer the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Integer integer) {
        return integer > 2;
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @NotNull
    @Override
    public Predicate<Integer> and(@NotNull Predicate<? super Integer> other) {
        return Predicate.super.and(other);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    @NotNull
    @Override
    public Predicate<Integer> negate() {
        return Predicate.super.negate();
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @NotNull
    @Override
    public Predicate<Integer> or(@NotNull Predicate<? super Integer> other) {
        return Predicate.super.or(other);
    }

}
