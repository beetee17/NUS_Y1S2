///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////

    private int[] seed = null;
    private int size = 0;
    private int tap = 0;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // This constructor initializes the shift register with its size and with the appropriate tap.
        this.size = size;
        this.tap = tap;

    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // Sets the shift register to the specified initial seed.
        // Recall that the seed is the initial set of bits stored in the shift register.
        for (int bit : seed) {
            // The initial seed is specified as an array of 0s and 1s (represented as integers).
            // If the seed contains any other value, that is an error.
            if (bit != 0 && bit != 1) {
                return;
            }
        }
        this.seed = seed;
    }

    public int[] getSeed() {
        return this.seed;
    }

    public void setSeedWithString(String seed) {
        // Sets the shift register to the specified initial seed.
        // Recall that the seed is the initial set of bits stored in the shift register.
        int numBitsInAByte = 8;
        int[] resultingSeed = new int[seed.length()*numBitsInAByte];

        for (int i = 0; i < seed.length(); i++) {
            int asciiCode = (int) seed.charAt(i);

            String byteOfSeed = Integer.toBinaryString(asciiCode);

            int padding = numBitsInAByte - byteOfSeed.length();

            for (int j = 0; j < padding; j++) {
                resultingSeed[j + i*j] = 0;
            }
            for (int j = padding; j < numBitsInAByte; j++) {
                String bit = Character.toString(byteOfSeed.charAt(j - padding)) ;
                resultingSeed[j + i*numBitsInAByte] = Integer.parseInt(bit);
            }

        }

        this.seed = resultingSeed;
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // Executes one shift step and returns the least significant bit of the resulting register.
        // (1) The feedback bit is calculated as the XOR of the most significant bit and the tap bit.
        // Note: In Java, (a^b) calculates the XOR of a and b.
        // (2) The most significant bit is dropped.
        // (3) Every bit is moved one slot to the left.
        // (4) The least significant bit is set to the feedback bit.
        int[] nextSeed = new int[size];

        int mostSignificantBit = seed[size-1];
        int tapBit = seed[tap];
        int feedbackBit =  mostSignificantBit^tapBit;

        for (int i = 0; i < size-1; i++) {
            nextSeed[i+1] = seed[i];
        }
        nextSeed[0] = feedbackBit;

        seed = nextSeed;

        return seed[0];
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // Extracts k bits from the shift register.
        // It executes the shift operation k times, saving the k bits returned.
        // It then converts these k bits from binary into an integer.
        // For example: if generate is called with a value of 3, then it calls shift 3 times.
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = this.shift();
        }
        return toBinary(result);
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        int result = 0;
        int counter = array.length - 1;

        for (int bit : array) {
            if (bit == 1) {
                result += MysteryFunction(2, counter);
            }
            counter -= 1;
        }

        return result;
    }
    static int MysteryFunction(int argA, int argB) {
        int c = 1;
        int d = argA;
        int e = argB;
        while (e > 0) {
            if (2 * (e / 2) != e) {
                c = c * d;
            }
            d = d * d;
            e = e / 2;
        }
        return c;
    }
    public String toString()  {
        String res = "";
        for (int i : seed) {
            res += Integer.toString(i);
        }
        return res;
    }
    public static void main(String[] args) {
//        int[] array = new int[] {1, 1, 0, 0, 0, 1, 0, 1, 1};
//        ShiftRegister shifter = new ShiftRegister(9, 7);
//        shifter.setSeed(array);
//        for (int i = 0; i < 10; i++) {
//            int j = shifter.shift();
//            System.out.println(shifter + " " + j);
//        }
        String seed = "ar";
        ShiftRegister shifter = new ShiftRegister(seed.length()*8, 7);
        shifter.setSeedWithString(seed);
        System.out.println(shifter);
    }
}

