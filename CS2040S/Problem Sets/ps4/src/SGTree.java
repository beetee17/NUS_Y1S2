import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }

        @Override
        public String toString() {
            return Integer.toString(this.key);
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree
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
     * Builds an array of nodes in the specified subtree
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
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
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
    * Rebuilds the specified subtree of a node
    * 
    * @param node the part of the subtree to rebuild
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
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert((int) (Math.random() * 100));
        }
        int numLeftNodes = tree.countNodes(tree.root, Child.LEFT);
        int numRightNodes = tree.countNodes(tree.root, Child.RIGHT);

        TreeNode[] leftNodes = tree.enumerateNodes(tree.root, Child.LEFT);
        TreeNode[] rightNodes = tree.enumerateNodes(tree.root, Child.RIGHT);

        System.out.println("# LEFT NODES: " + numLeftNodes);
        System.out.println("# RIGHT NODES: " + numRightNodes);

        System.out.println("LEFT NODES: " + Arrays.toString(leftNodes));
        System.out.println("RIGHT NODES: " + Arrays.toString(rightNodes));

        System.out.println("CHECKS OUT = " + (leftNodes.length + rightNodes.length == numLeftNodes + numRightNodes));

        tree.rebuild(tree.root, Child.RIGHT);

        TreeNode rebuiltSubTree = tree.root.right;
        System.out.println(rebuiltSubTree);
        TreeNode[] leftNodesAfter = tree.enumerateNodes(rebuiltSubTree, Child.LEFT);
        TreeNode[] rightNodesAfter = tree.enumerateNodes(rebuiltSubTree, Child.RIGHT);

        int numLeftNodesAfter = tree.countNodes(rebuiltSubTree, Child.LEFT);
        int numRightNodesAfter = tree.countNodes(rebuiltSubTree, Child.RIGHT);

        System.out.println("(AFTER) # LEFT NODES: " + numLeftNodesAfter);
        System.out.println("(AFTER) # RIGHT NODES: " + numRightNodesAfter);

        System.out.println("(AFTER) LEFT NODES: " + Arrays.toString(leftNodesAfter));
        System.out.println("(AFTER) RIGHT NODES: " + Arrays.toString(rightNodesAfter));
    }
}
