import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
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
        public static class TreeNode<T extends Comparable<T>> implements TreePrinter.PrintableNode {
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
//                return "" + this.hashCode();
                return "{" + data + "}, WEIGHT: " + weight;
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
                return (this.parent != null && this.parent.left == this);
            }
            public boolean isRightChild() {
                return (this.parent != null && this.parent.right == this);
            }

            public boolean hasTwoChildren() {
                return this.left != null && this.right != null;
            }

            @Override
            public TreePrinter.PrintableNode getLeft() {
                return this.left;
            }

            @Override
            public TreePrinter.PrintableNode getRight() {
                return this.right;
            }

            @Override
            public String getText() {
                return this.toString();
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

        public int getRank(TreeNode<T> node, T data) {
            if (node == null) return 0;
            if (node.data.compareTo(data) > 0) {
                if (node.right == null) return 1 + getRank(node.left, data);
                else return 1 + node.right.weight + getRank(node.left, data);
            } else {
                return getRank(node.right, data);
            }
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
            if (node.isLeaf()) {
                node.weight = 1;
                return 1;
            }

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
            TreeNode<T> p = node.parent;

            if (node.isLeaf()) {
                if (node.isLeftChild()) p.left = null;
                else if (node.isRightChild()) p.right = null;
                node.parent = null;
            } else if (node.hasTwoChildren()) {
                TreeNode<T> successor = successor(node);
                System.out.println("SUCCESSOR: " + successor);
                node.data = successor.data;
                System.out.println("REPLACED NODE DATA WITH SUCCESSOR'S");
                System.out.println(node);
                delete(successor);
                fixAllWeights(node);
            } else {
                TreeNode<T> c = node.hasNoLeftChild() ? node.right : node.left;
                if (node.isLeftChild()) p.left = c;
                else if (node.isRightChild()) p.right = c;
                c.parent = p;
                if (node == root) {
                    root = c;
                    System.out.println("REPLACED ROOT");
                }
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
        public TreeNode<T> insert(T data) {
            TreeNode<T> newChild = new TreeNode<T>(data);
            if (root == null) {
                root = newChild;
                return newChild;
            }


            TreeNode<T> node = root;
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
            return newChild;
        }
    }
    private static class Team implements Comparable<Team> {
        long numSolved;
        long totalPenalty;
        int id;
        public Team(long numSolved, long totalPenalty, int id) {
            this.numSolved = numSolved;
            this.totalPenalty = totalPenalty;
            this.id = id;
        }
        @Override
        public String toString() {
            return String.format("%s (%s, %s)", this.id, this.numSolved, this.totalPenalty);
        }

        @Override
        public int compareTo(Team other) {
            Long numSolved = this.numSolved;
            Long totalPenalty = this.totalPenalty;
            if (numSolved == other.numSolved) {
                int tieBreak = -(totalPenalty.compareTo(other.totalPenalty));
                if (tieBreak == 0) {
                    Integer id = this.id;
                    return -(id.compareTo(other.id));
                } else {
                    return tieBreak;
                }
            }
            return numSolved.compareTo(other.numSolved);
        }
    }

    public SGTree<Team> tree;
    public SGTree.TreeNode<Team> favTeam;
    public ArrayList<Team> array;
    public int numTeams;

    public Solution(int numTeams) {
        this.numTeams = numTeams;
        this.tree = new SGTree<>();
        this.array = new ArrayList<>();

        for (int i = 0; i < numTeams; i++) {

            SGTree.TreeNode<Team> team = this.tree.insert(new Team(0, 0, i+1));
            if (i == 0) {
                favTeam = team;
            }
            this.array.add(new Team(0, 0, i+1));
        }
    }

    public int update(int team, int newPenalty) {

        System.out.println("BEFORE");
        System.out.println("ROOT IS " + tree.root);
        TreePrinter.print(tree.root);
        System.out.println("TEAMS: " + Arrays.toString(teams));
        SGTree.TreeNode<Team> oldTeam = this.teams[team-1];

        Team newTeam = new Team(oldTeam.data.numSolved + 1,
                     oldTeam.data.totalPenalty + newPenalty,
                                team);
        System.out.println("DELETE " + oldTeam);
        this.tree.delete(oldTeam);
        TreePrinter.print(tree.root);
        SGTree.TreeNode<Team> newTeamNode = this.tree.insert(newTeam);

        this.teams[team-1] = newTeamNode;

        this.tree.fixAllWeights(this.tree.root);
//        TreePrinter.print(tree.root.left);
//        System.out.println("RANK: " + (tree.getRank(tree.root.left, teams[0].data) + 1));
        return tree.getRank(tree.root, teams[0].data) + 1;
    }

    public int naiveUpdate(int team, int newPenalty) {
        for (Team t : array) {
            if (t.id == team) {
                t.numSolved++;
                t.totalPenalty += newPenalty;
            }
        }
        Collections.sort(array);
//        System.out.println(array);
        for (int i = 0; i < numTeams; i++) {
            if (array.get(i).id == 1) {
                return numTeams - i;
            }
        }
        return numTeams;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Solution solution = new Solution(n);
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int team = sc.nextInt(), penalty = sc.nextInt();
            System.out.println(solution.update(team, penalty));
//            System.out.println(solution.naiveUpdate(team, penalty));
        }
    }

}
