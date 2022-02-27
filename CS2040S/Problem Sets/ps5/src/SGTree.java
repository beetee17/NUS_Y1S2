import java.util.ArrayList;

/**
 * ScapeGoat Tree class
 * <p>
 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
 * rebuilding a subtree.
 */

public class SGTree {

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
    public static class TreeNode {
        int key;
        int weight = 1;
        public TreeNode left = null;
        public TreeNode right = null;
        TreeNode(int k) {
            key = k;
        }

        @Override
        public String toString() {
            return "KEY: " + key + ", WEIGHT: " + weight;
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

        public boolean hasTwoChildren() {
            return this.left != null && this.right != null;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        return countAllNodesIncludingRoot(child == Child.LEFT ? node.left : node.right);
    }
    public int countAllNodesIncludingRoot(TreeNode root) {
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
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        return enumerateAllNodes(child == Child.LEFT ? node.left : node.right);
    }

    private TreeNode[] enumerateAllNodes(TreeNode root) {
        TreeNode[] nodes = new TreeNode[countAllNodesIncludingRoot(root)];
        enumerateAllHelper(root, nodes, 0);
        return nodes;
    }
    private int enumerateAllHelper(TreeNode root, TreeNode[] nodes, int index) {
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
    public TreeNode buildTree(TreeNode[] nodeList) {
        return buildTreeHelper(nodeList, 0, nodeList.length - 1);
    }

    private TreeNode buildTreeHelper(TreeNode[] nodeList, int begin, int end) {
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
        TreeNode root = nodeList[mid];

        root.left = buildTreeHelper(nodeList, begin, mid - 1);
        root.right = buildTreeHelper(nodeList, mid + 1, end);

        return root;
    }

    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is balanced if both of its children has weight <= 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        if (node == null || node.isLeaf()) return true;

        if (node.hasTwoChildren())
            return isBalanced(node, node.left) && isBalanced(node, node.right);

        if (node.hasNoLeftChild())
            return isBalanced(node, node.right);

        // node must have ONLY a left child
        return isBalanced(node, node.left);
    }

    private boolean isBalanced(TreeNode parent, TreeNode child) {
        return 3 * child.weight <= 2 * parent.weight;
    }

    /**
     * Rebuilds the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else {
            node.right = newChild;
        }
        fixWeights(node, child);
    }

    /**
     * Fixes the weights for all nodes in the subtree rooted at `node`, including `node`
     * @param node
     * @return The fixed weight of `node`
     */
    private int fixAllWeights(TreeNode node) {
        if (node == null) return 0;
        if (node.isLeaf()) return 1;

        node.weight = 1 + fixAllWeights(node.left) + fixAllWeights(node.right);

        return node.weight;
    }

    public void fixWeights(TreeNode node, Child child) {
        if (node == null) return;
        fixAllWeights(child == Child.LEFT ? node.left : node.right);
    }

    /**
     * Inserts a key into the tree.It works as follows:
     * 1. Insert the specified key in the tree using a typical binary search tree insertion
     *    (notice that this new node will be inserted as a leaf).
     *
     * 2. Identify the highest unbalanced node on the root-to-leaf path to the
     *    newly inserted node. (Why is it sufficient for us to check only the nodes
     *    along the root-to-newly-inserted-leaf path?)
     *
     * 3. If there is no such unbalanced node, then we are done. If there is an unbalanced node, then rebuild it.
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;
        ArrayList<TreeNode> ancestry = new ArrayList<>();

        while (true) {
            ancestry.add(node);
            if (key <= node.key) {
                if (node.hasNoLeftChild()) break;
                node = node.left;

            } else {
                if (node.hasNoRightChild()) break;
                node = node.right;
            }
        }

        if (key <= node.key)
            node.left = new TreeNode(key);
        else
            node.right = new TreeNode(key);

        for (TreeNode ancestor : ancestry)
            ancestor.weight++;

        for (int i = 1; i < ancestry.size(); i++) {
            // traverse root-to-leaf path
            // skips the root in checking balance; the root has no parent to re-balance
            node = ancestry.get(i);

            if (!checkBalance(node)) {
                // found the highest unbalanced node -> rebuild the appropriate subtree of its parent
                TreeNode parent = ancestry.get(i-1);
                if (parent.left == node) rebuild(parent, Child.LEFT);
                else rebuild(parent, Child.RIGHT);
                break;
            }
        }
    }



    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(8);
        tree.insert(9);
        tree.insert(10);
        tree.insert(6);
        tree.insert(11);
    }
}
