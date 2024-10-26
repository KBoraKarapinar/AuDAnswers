package p1.sort.radix;



import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An {@link RadixIndexExtractor} for extracting the index corresponding to a character in a string.
 *
 * <p>It is case-insensitive. It maps the characters 'a' to 'z' to the indices 0 to 25. All other characters are mapped to 0.
 * The position is interpreted as the position from the end of the string, i.e. position 0 corresponds to the last
 * character in the string.
 */
public class LatinStringIndexExtractor implements RadixIndexExtractor<String> {

    boolean shouldlog = true;

    @Override
    public int extractIndex(String value, int position) {

        if(shouldlog){
            System.out.println("[] [1] [] LatinEx: i'm called, string: " + value +" position:" + position );
            System.out.println();

        }


        int integervalue = value.charAt( (value.length() - 1) - position);

        if(integervalue >= 65 && integervalue <=90 ){
            if(shouldlog){
                System.out.println("[] [2] [] LatinEx: it was  upper case" );
                System.out.println();

            }

            return (integervalue - 65);
        }else if(integervalue >= 97 && integervalue <=122){
            if(shouldlog){
                System.out.println("[] [3] [] LatinEx: it was lower case" );
                System.out.println();

            }
            return (integervalue - 97);
        }else {
            return 0;
        }

    }

    @Override
    public int getRadix() {
        return 'z' - 'a' + 1; //26
    }
}
