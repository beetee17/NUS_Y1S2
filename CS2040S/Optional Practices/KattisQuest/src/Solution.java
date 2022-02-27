import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Solution {

    /**
     * ScapeGoat Tree class
     * <p>
     * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
     * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
     * rebuilding a subtree.
     */
    public static class SGTree<T extends Comparable<T>> {

        // Designates which child in a binary tree
        enum Child {LEFT, RIGHT}

        /**
         * TreeNode class.
         * <p>
         * This class holds the data for a node in a binary tree.
         * <p>
         * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything
         * public like this is a bad idea!
         */
        public static class TreeNode<T extends Comparable<T>> {
            T data;
            int weight = 1;

            public TreeNode<T> parent = null;
            public TreeNode<T> left = null;
            public TreeNode<T> right = null;

            TreeNode(T data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return "DATA { " + data + "}, WEIGHT: " + weight;
            }

            public boolean hasNoLeftChild() {
                return this.left == null;
            }

            public boolean hasNoRightChild() {
                return this.right == null;
            }

            public boolean isLeaf() {
                return this.hasNoLeftChild() && this.hasNoRightChild();
            }

            public boolean isLeftChild() {
                return this.parent.left == this;
            }
            public boolean isRightChild() {
                return this.parent.right == this;
            }

            public boolean hasTwoChildren() {
                return this.left != null && this.right != null;
            }
        }

        // Root of the binary tree
        public TreeNode<T> root;

        /**
         * Counts the number of nodes in the specified subtree.
         *
         * @param node  the parent node, not to be counted
         * @param child the specified subtree
         * @return number of nodes
         */
        public int countNodes(TreeNode<T> node, Child child) {
            return countAllNodesIncludingRoot(child == Child.LEFT ? node.left : node.right);
        }
        public int countAllNodesIncludingRoot(TreeNode<T> root) {
            if (root == null) { return 0; }
            return 1 + countAllNodesIncludingRoot(root.left) + countAllNodesIncludingRoot(root.right);
        }

        /**
         * Builds an array of nodes in the specified subtree.
         *
         * @param node  the parent node, not to be included in returned array
         * @param child the specified subtree
         * @return array of nodes
         */
        public TreeNode<T>[] enumerateNodes(TreeNode<T> node, Child child) {
            return enumerateAllNodes(child == Child.LEFT ? node.left : node.right);
        }

        private TreeNode<T>[] enumerateAllNodes(TreeNode<T> root) {
            TreeNode<T>[] nodes = new TreeNode[countAllNodesIncludingRoot(root)];
            enumerateAllHelper(root, nodes, 0);
            return nodes;
        }

        private int enumerateAllHelper(TreeNode<T> root, TreeNode<T>[] nodes, int index) {
            if (root == null) { return index; }
            index = enumerateAllHelper(root.left, nodes, index);

            nodes[index] = root;
            index++;

            index = enumerateAllHelper(root.right, nodes, index);
            return index;
        }

        /**
         * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
         *
         * @param nodeList ordered array of nodes
         * @return the new root node
         */
        public TreeNode<T> buildTree(TreeNode<T>[] nodeList) {
            return buildTreeHelper(nodeList, 0, nodeList.length - 1);
        }

        private TreeNode<T> buildTreeHelper(TreeNode<T>[] nodeList, int begin, int end) {
            /**
             * 1) Get the Middle of the array and make it root.
             * 2) Recursively do same for left half and right half.
             *       a) Get the middle of left half and make it left child of the root
             *           created in step 1.
             *       b) Get the middle of right half and make it right child of the
             *           root created in step 1.
             */
            if (begin > end)
                return null;

            int mid = begin + Math.floorDiv(end - begin, 2);
            TreeNode<T> root = nodeList[mid];

            root.left = buildTreeHelper(nodeList, begin, mid - 1);
            root.right = buildTreeHelper(nodeList, mid + 1, end);

            if (root.left != null)
                root.left.parent = root;
            if (root.right != null)
                root.right.parent = root;

            return root;
        }

        /**
         * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
         * false. A node is balanced if both of its children has weight <= 2/3 of its weight.
         *
         * @param node a node to check balance on
         * @return true if the node is balanced, false otherwise
         */
        public boolean checkBalance(TreeNode<T> node) {
            if (node == null || node.isLeaf()) return true;

            if (node.hasTwoChildren())
                return isBalanced(node, node.left) && isBalanced(node, node.right);

            if (node.hasNoLeftChild())
                return isBalanced(node, node.right);

            // node must have ONLY a left child
            return isBalanced(node, node.left);
        }

        private boolean isBalanced(TreeNode<T> parent, TreeNode<T> child) {
            return 3 * child.weight <= 2 * parent.weight;
        }

        /**
         * Rebuilds the specified subtree of a node.
         *
         * @param node  the part of the subtree to rebuild
         * @param child specifies which child is the root of the subtree to rebuild
         */
        public void rebuild(TreeNode<T> node, Child child) {
            // Error checking: cannot rebuild null tree
            if (node == null) return;
            // First, retrieve a list of all the nodes of the subtree rooted at child
            TreeNode<T>[] nodeList = enumerateNodes(node, child);
            // Then, build a new subtree from that list
            TreeNode<T> newChild = buildTree(nodeList);
            // Finally, replace the specified child with the new subtree
            if (child == Child.LEFT) {
                node.left = newChild;
            } else {
                node.right = newChild;
            }
            newChild.parent = node;
            fixWeights(node, child);
        }

        /**
         * Fixes the weights for all nodes in the subtree rooted at `node`, including `node`
         * @param node
         * @return The fixed weight of `node`
         */
        private int fixAllWeights(TreeNode<T> node) {
            if (node == null) return 0;
            if (node.isLeaf()) return 1;

            node.weight = 1 + fixAllWeights(node.left) + fixAllWeights(node.right);

            return node.weight;
        }

        public void fixWeights(TreeNode<T> node, Child child) {
            if (node == null) return;
            fixAllWeights(child == Child.LEFT ? node.left : node.right);
        }

        public TreeNode<T> find(TreeNode<T> node, T data) {
            if (node == null)
                return null;
            if (node.data.compareTo(data) < 0) {
                return find(node.left, data);
            } else if (node.data.compareTo(data) > 0) {
                return find(node.right, data);
            } else {
                return node;
            }
        }

        public TreeNode<T> findLargest(TreeNode<T> node, T max) {
            if (node == null) return null;
            if (node.isLeaf()) return node.data.compareTo(max) <= 0 ? node : null;

            if (node.data.compareTo(max) > 0) {
                return findLargest(node.left, max);
            } else {
                TreeNode<T> wish = findLargest(node.right, max);
                if (wish != null) return wish;
                else return node;
            }
        }

        public TreeNode<T> findMin(TreeNode<T> node) {
            if (node == null) return null;
            if (node.left == null) return node;
            return findMin(node.left);
        }

        public TreeNode<T> successor(TreeNode<T> node) {
            if (node.right != null) return findMin(node.right);
            TreeNode<T> p = node.parent;
            while (p != null && node == p.right) {
                node = p;
                p = node.parent;
            }
            return p;
        }
        public void delete(TreeNode<T> node) {
            if (node == root) return;
            TreeNode<T> p = node.parent;

            if (node.isLeaf()) {
                if (node.isLeftChild()) p.left = null;
                else p.right = null;
                node.parent = null;
            } else if (node.hasTwoChildren()) {
                TreeNode<T> successor = successor(node);
                node.data = successor.data;
                delete(successor);
            } else {
                TreeNode<T> c = node.hasNoLeftChild() ? node.right : node.left;
                if (node.isLeftChild()) p.left = c;
                else p.right = c;
                c.parent = p;
            }

            if (!checkBalance(p)) {
                if (p.left != null)
                    rebuild(p, isBalanced(p, p.left) ? Child.RIGHT : Child.LEFT);
                if (p.right != null)
                    rebuild(p, isBalanced(p, p.right) ? Child.LEFT : Child.RIGHT);
            }
        }

        /**
         * Inserts a key into the tree. It works as follows:
         * 1. Insert the specified key in the tree using a typical binary search tree insertion
         *    (notice that this new node will be inserted as a leaf).
         *
         * 2. Identify the highest unbalanced node on the root-to-leaf path to the
         *    newly inserted node. (Why is it sufficient for us to check only the nodes
         *    along the root-to-newly-inserted-leaf path?)
         *
         * 3. If there is no such unbalanced node, then we are done. If there is an unbalanced node, then rebuild it.
         *
         * @param data the data to insert
         */
        public void insert(T data) {
            if (root == null) {
                root = new TreeNode<T>(data);
                return;
            }

            TreeNode<T> node = root;
            TreeNode<T> newChild = new TreeNode<T>(data);
            ArrayList<TreeNode<T>> ancestry = new ArrayList<>();

            while (true) {
                ancestry.add(node);
                if (data.compareTo(node.data) <= 0) {
                    if (node.hasNoLeftChild()) break;
                    node = node.left;

                } else {
                    if (node.hasNoRightChild()) break;
                    node = node.right;
                }
            }

            if (data.compareTo(node.data) <= 0)
                node.left = newChild;
            else
                node.right = newChild;
            newChild.parent = node;
            for (TreeNode<T> ancestor : ancestry)
                ancestor.weight++;

            for (int i = 1; i < ancestry.size(); i++) {
                // traverse root-to-leaf path
                // skips the root in checking balance; the root has no parent to re-balance
                node = ancestry.get(i);

                if (!checkBalance(node)) {
                    // found the highest unbalanced node -> rebuild the appropriate subtree of its parent
                    TreeNode<T> parent = ancestry.get(i-1);
                    if (parent.left == node)
                        rebuild(parent, Child.LEFT);
                    else
                        rebuild(parent, Child.RIGHT);
                    break;
                }
            }
        }
    }

    private static class Quest implements Comparable<Quest> {
        long energy;
        long value;
        public Quest(long energy, long value) {
            this.energy = energy;
            this.value = value;
        }
        @Override
        public String toString() {
            return "ENERGY: " + this.energy + ", REWARD: " + value;
        }
        @Override
        public int compareTo(Quest other) {
            Long energy = this.energy;
            Long value = this.value;
            if (energy == other.energy) {
                return value.compareTo(other.value);
            }
            return energy.compareTo(other.energy);
        }
    }

    private SGTree<Quest> tree;
    private ArrayList<Quest> quests;

    public Solution() {
        this.tree = new SGTree<>();
        this.tree.root = new SGTree.TreeNode<>(new Quest(Long.MAX_VALUE, Long.MAX_VALUE));
        this.quests = new ArrayList<>();
    }

    void add(long energy, long value) {
        Quest quest = new Quest(energy, value);
        tree.insert(quest);
        quests.add(quest);
    }

    long query(long remainingEnergy) {
        long totalGoldReward = 0;
        while (remainingEnergy > 0) {
//            System.out.println("TREE: " + Arrays.toString(tree.enumerateAllNodes(tree.root)));
            SGTree.TreeNode<Quest> quest = tree.findLargest(tree.root.left, new Quest(remainingEnergy, Long.MAX_VALUE));
//            System.out.println("FOUND QUEST NODE: " + quest);

            if (quest == null) break;

            totalGoldReward += quest.data.value;
            remainingEnergy -= quest.data.energy;
            tree.delete(quest);
        }
        return totalGoldReward;
    }
    long naiveQuery(long remainingEnergy) {
//        System.out.println("INPUT ENERGY: " + remainingEnergy);
        long totalGoldReward = 0;
        Collections.sort(quests);

        while (remainingEnergy > 0) {
//            System.out.println("ARRAY: " + quests);
            if (quests.size() == 0) break;
            if (quests.get(0).energy > remainingEnergy) break;
            for (int i = quests.size() - 1; i >= 0; i--) {
                Quest quest = quests.get(i);
                if (quest.energy <= remainingEnergy) {
//                    System.out.println("GOT QUEST: " + quest);
                    totalGoldReward += quest.value;
                    remainingEnergy -= quest.energy;
                    quests.remove(i);
                    break;
                }
            }

        }
        return totalGoldReward;
    }


    public static void main(String[] args) {
        /**
         * 1. add 8 10
         * 2. add 3 25
         * 3. add 5 6
         * 4. query 7 (returns 6) 5. query 7 (returns 25)
         * 6. add 1 9
         * 7. add 2 13
         * 8. query 20 (returns 32) 9. query 1 (returns 0)
         */
        Solution solution = new Solution();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String str = sc.next();
            if (str.equals("query")) {
                long remainingEnergy = sc.nextLong();
                System.out.println(solution.query(remainingEnergy));
            } else if (str.equals("add")) {
                long energy = sc.nextLong(), value = sc.nextLong();
                solution.add(energy, value);
            }
        }
//        int NUM_TESTS = 10000;
//        for (int i = 0; i < NUM_TESTS; i++) {
//            System.out.println("TEST " + i);
//            int n = (int) (Math.random() * 10);
//            for (int j = 0; j < n; j++) {
//                int rand = (int) (Math.random() * 10);
//                if (rand < 5) {
//                    long remainingEnergy = (long) (Math.random() * 100);
//                    long answer = solution.naiveQuery(remainingEnergy);
//                    long output = solution.query(remainingEnergy);
//                    if (answer != output) {
//                        System.out.println("WRONG ANSWER");
//                        System.out.println("EXPECTED: " + answer);
//                        System.out.println("OUTPUT: " + output);
//                        return;
//                    } else {
////                        System.out.println("CORRECT");
//                    }
//                } else {
//                    long energy = (long) (Math.random() * 100);
//                    long value = (long) (Math.random() * 200);
//                    solution.add(energy, value);
////                    System.out.println("ADD QUEST: " + new Quest(energy, value));
//                }
//            }
//        }
//        System.out.println("ALL CORRECT");
    }

}
