package p1.comparator;

import p1.card.Card;
import p1.card.CardColor;

import java.util.Comparator;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Compares two {@linkplain Card Cards}.
 * <p>
 * The cards are first compared by their value and then by their {@link CardColor}.
 *
 * @see Card
 * @see CardColor
 */
public class CardComparator implements Comparator<Card> {

    /**
     * Compares two {@linkplain Card Cards}.
     * <p>
     * The cards are first compared by their value and then by their {@link CardColor}.
     * <p>
     * The value of the cards compared by the natural order of the {@link Integer} class.
     * <p>
     * The color of the cards compared using the following order: {@link CardColor#CLUBS} > {@link CardColor#SPADES} >.{@link CardColor#HEARTS} > {@link CardColor#DIAMONDS}.
     *
     * @param o1 the first {@link Card} to compare.
     * @param o2 the second {@link Card} to compare.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     * @throws NullPointerException if either of the {@linkplain Card Cards} is null.
     *
     * @see Card
     * @see CardColor
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(Card o1, Card o2) {

      //Goal:
//First, compare the numbers, and if they are the same, compare the color

        boolean cardValuesAreSame = false;

        if (o1.cardValue() > o2.cardValue()){
            return 1;
        }else if(o1.cardValue() == o2.cardValue()){

            int o1ColorValue = cardColorIntegerValueCreator(o1);
            int o2ColorValue = cardColorIntegerValueCreator(o2);

            if(o1ColorValue > o2ColorValue){
                return 1;
            } else if (o1ColorValue == o2ColorValue) {
                return 0;
            }else{
                return -1;
            }


        }else{
            return -1;
        }

        // card types: Kreuz > Pik >
        //Herz > Karo

        //CLUBS > SPADES > HEARTS > DIAMONDS


        // DIAMONDS, //Karo
        //    HEARTS, // Herz
        //    SPADES, // Pik
        //    CLUBS // Kreuz

        //Beispiel: Karo 3 < Herz 4 < HEARTS 5 < SPADES 5



    }

    private int cardColorIntegerValueCreator(Card generalCard){



        switch (generalCard.cardColor()){
            case DIAMONDS:
                return 1;
            case HEARTS:
                return 2;
            case SPADES:
                return 3;
            case CLUBS:
                return 4;
            default:
                System.err.println("There has been a problem #cardColorIntegerValueCreator");
                System.err.println("Type wasnt one of the four elements, but:" + generalCard.cardColor());
                System.err.println("I return -4242 for debug now.");
                return -4242;

        }


    }
}
