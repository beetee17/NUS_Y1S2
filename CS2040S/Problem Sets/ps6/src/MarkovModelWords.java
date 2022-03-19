import java.util.*;

public class MarkovModelWords {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final String NOWORD = "NIL";

	private int order;

	public static final List<Character> PUNCTUATIONS = List.of(',', '.', ';', '"', ':', '/', '!', '(', ')', '&');

	/**
	 * Use a symbol table that maps strings of length `order` to another symbol table;
	 * the second symbol table maps ASCII characters to integers.
	 * These integers represent the frequency that a given character follows the initial string.
	 */
	public HashMap<List<String>, HashMap<String, Integer>> table = new HashMap<>();

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModelWords(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}


	public static ArrayList<String> getWords(String text) {
		int len = text.length();
		ArrayList<String> words = new ArrayList<>();

		StringBuffer word = new StringBuffer();
		for (int i = 0; i < len; i++) {

			Character c = text.charAt(i);

			if (PUNCTUATIONS.contains(c)) {
				if (word.length() > 0) words.add(word.toString());
				words.add(String.valueOf(c));
				word = new StringBuffer();
			} else if (c.equals(' ')) {
				if (word.length() > 0) {
					words.add(word.toString());
					word = new StringBuffer();
				}
			} else {
				word.append(c);
			}
		}
		if (word.length() > 0) words.add(word.toString());
		return words;
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 * This method may be called multiple times on the same MarkovModel object.
	 * Each repeated call is expected to build the MarkovModel again.
	 */
	public void initializeText(List<String> words) {
		this.table = new HashMap<>();

		for (int i = 0; i < words.size() - this.order; i++) {

			List<String> s = words.subList(i, i + this.order);
			String next = words.get(i + this.order);

			HashMap<String, Integer> subTable = this.table.get(s);

			if (subTable == null)
				subTable = new HashMap<>();

			this.table.put(s, subTable);
			subTable.put(next, subTable.getOrDefault(next, 0) + 1);
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(List<String> kgram) {
		HashMap<String, Integer> subTable = this.table.get(kgram);
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
	public int getFrequency(List<String> kgram, String word) {
		HashMap<String, Integer> subTable = this.table.get(kgram);
		if (subTable == null) return 0;
		else return subTable.getOrDefault(word, 0);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 * To generate the random choice, you must use the pseudorandom number generator with the specified seed
	 */
	public String nextWord(List<String> kgram) {
		HashMap<String, Integer> subTable = this.table.get(kgram);
		if (subTable == null) return NOWORD;

		String[] words = new String[subTable.keySet().size()];
		Arrays.sort(subTable.keySet().toArray(words));

		int kgramFrequency = getFrequency(kgram);
		int randInt = this.generator.nextInt(kgramFrequency);
		int sum = 0;

		for (String s : words) {
			sum += subTable.get(s);
			if (sum > randInt) return s;
		}

		return NOWORD;
	}
}
