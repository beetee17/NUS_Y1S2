import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private TrieNode root = new TrieNode(ROOT_CHAR);
    private static final char TERMINATING_CHAR = ']';
    private static final char ROOT_CHAR = '_';
    private static final int TOTAL_CHARS = (122 - 48) + 2;

    private static int charToInt(char c) { return (int) c - 48; }

    private static class TrieNode {
        char c;
        TrieNode parent;

        private TrieNode[] presentChars = new TrieNode[TOTAL_CHARS];

        @Override
        public String toString() {
            return String.valueOf(this.c);
        }

        private TrieNode(String s) {
            this.c = s.charAt(0);
        }
        private TrieNode(char c) {
            this.c = c;
        }

        private boolean isTerminal() { return this.c == TERMINATING_CHAR; }
        private boolean isRoot() { return this.c == ROOT_CHAR; }

        public void setChild(char c, TrieNode child) {
            this.presentChars[charToInt(c)] = child;
            child.parent = this;
        }
        public TrieNode[] getChildren() {
            return Stream.of(this.presentChars)
                    .filter(Objects::nonNull)
                    .toArray(TrieNode[]::new);
        }
        public TrieNode findChildForChar(char c) {
            return this.presentChars[charToInt(c)];
        }

        public String getPrefix() {
            if (this.isRoot()) return "";

            TrieNode parent = this.parent;
            StringBuilder prefix = new StringBuilder();
            while (parent != null && !parent.isRoot()) {
                prefix.insert(0, parent.c);
                parent = parent.parent;
            }
            return prefix.toString();
        }
    }

    public Trie() { }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        TrieNode node = this.root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            TrieNode child = node.findChildForChar(c);

            if (child != null) {
                node = child;
            } else {
                // node not present -> insert here
                TrieNode newChild = new TrieNode(c);
                node.setChild(c, newChild);
                node = newChild;
            }
        }
        // insert terminating node
        node.setChild(TERMINATING_CHAR, new TrieNode(TERMINATING_CHAR));
    }
    private int countAll(TrieNode node) {
        if (node == null) return 0;
        if (node.isTerminal()) return 1;
        int count = 0;
        for (TrieNode child : node.getChildren()) {
            if (child != null) {
                count += countAll(child);
            }
        }
        return count;
    }

    private String[] enumerateAll(TrieNode node) {
        String[] words = enumerateAllHelper(node).toArray(String[]::new);
        String prefix = node.getPrefix();
        return Arrays.stream(words).map(word -> prefix + word).toArray(String[]::new);
    }

    private ArrayList<String> enumerateAllHelper(TrieNode node) {
        ArrayList<String> words = new ArrayList<>();
        if (node != null) {
            char c = node.c;
            if (!node.isTerminal()) {
                for (TrieNode child : node.getChildren()) {
                    for (String s : enumerateAllHelper(child)) {
                        words.add(node.isRoot() ? s : c + s);
                    }
                }
            } else {
                words.add("");
                return words;
            }
        }
        return words;
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        TrieNode node = this.root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            TrieNode child = node.findChildForChar(c);
            if (child != null) node = child;
            else return false;

        }
        return node.findChildForChar(TERMINATING_CHAR) != null;
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order.
     * This inserts the results into the specified ArrayList.
     * Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        TrieNode[] nodes = search(this.root, s);
        System.out.println("NODES: " + Arrays.toString(nodes));
        int count = 0;
        for (TrieNode node : nodes) {
            String[] words = enumerateAll(node);
            for (String word : words) {
                if (count >= limit) return;
                results.add(word);
                count++;
            }
        }
    }

    public TrieNode[] search(TrieNode node, String prefix) {
        if (node == null || node.isTerminal()) {
            return new TrieNode[] {};
        }

        System.out.println("START NODE: " + node);
        System.out.println("PREFIX: " + prefix);

        TrieNode currNode = node;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            System.out.println("MATCHING: " + c);
            System.out.println("CURR NODE: " + currNode);

            if (c == WILDCARD) {
                // recursively search all children
                ArrayList<TrieNode> matches = new ArrayList<>();
                for (TrieNode child : currNode.getChildren()) {
                    TrieNode[] searchResult = search(child, prefix.substring(i + 1));
                    matches.addAll(Arrays.asList(searchResult));
                }
                return matches.toArray(TrieNode[]::new);
            } else {
                TrieNode child = currNode.findChildForChar(c);
                if (child == null) {
                    System.out.println("NO SUCH WORD");
                    return new TrieNode[] { };
                } else {
                    System.out.println("UPDATING CURR NODE");
                    currNode = child;
                }
            }
        }
        // went through the whole string but the node has not terminated
        System.out.println("FOUND MATCH: " + currNode);
        return new TrieNode[] { currNode };
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[results.size()]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch(".", 10);
        String[] result2 = t.prefixSearch("pi.", 10);

        String search = "peck";
        System.out.println("Trie contains " + search + " ? " + t.contains(search));

        System.out.println("COUNT: " + t.countAll(t.root));
        System.out.println("ITEMS: " + Arrays.toString(t.enumerateAll(t.root)));

        System.out.println("1: " + Arrays.toString(result1));
        System.out.println("2: " + Arrays.toString(result2));

        // searching for 'pe' and 'pe.' should return:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
