import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	private int order;

	/**
	 * Use a symbol table that maps strings of length `order` to another symbol table;
	 * the second symbol table maps ASCII characters to integers.
	 * These integers represent the frequency that a given character follows the initial string.
	 */
	private HashMap<String, HashMap<Character, Integer>> table = new HashMap<>();

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 * This method may be called multiple times on the same MarkovModel object.
	 * Each repeated call is expected to build the MarkovModel again.
	 */
	public void initializeText(String text) {
		this.table = new HashMap<>();
		int len = text.length();
		for (int i = 0; i < len - this.order; i++) {

			String s = text.substring(i, i + this.order);
			Character next = text.charAt(i + this.order);

			HashMap<Character, Integer> subTable = this.table.get(s);

			if (subTable == null)
				subTable = new HashMap<>();
			this.table.put(s, subTable);
			subTable.put(next, subTable.getOrDefault(next, 0) + 1);
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		HashMap<Character, Integer> subTable = this.table.get(kgram);
		if (subTable == null) return 0;

		int sum = 0;
		for (Integer i : subTable.values()) {
			sum += i;
		}
		return sum;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		HashMap<Character, Integer> subTable = this.table.get(kgram);
		if (subTable == null) return 0;
		else return subTable.getOrDefault(c, 0);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 * To generate the random choice, you must use the pseudorandom number generator with the specified seed
	 */
	public char nextCharacter(String kgram) {
		HashMap<Character, Integer> subTable = this.table.get(kgram);

		if (subTable == null) return NOCHARACTER;

		Character[] characters = new Character[subTable.keySet().size()];

		Arrays.sort(subTable.keySet().toArray(characters));

		int kgramFrequency = getFrequency(kgram);
		int randInt = this.generator.nextInt(kgramFrequency);
		int sum = 0;

		for (Character c : characters) {
			sum += subTable.get(c);
			if (sum > randInt) return c;
		}

		return NOCHARACTER;
	}
}
