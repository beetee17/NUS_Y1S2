public class MedianFinder {

    class Tree<T extends Comparable<T>> {
        private Node<T> root;
        private Node<T> median;

        private int size;
        // Adds a number into the data structure.
        public void addNum(T data) {
            if (root == null) {
                root = new Node(data);
                median = root;
            }

            else {
                root.addNode(data);
                if (size % 2 == 0) {

                    if (data.compareTo(median.data) >= 0) {
                        // do nothing
                    }
                    else {
                        median = median.predecessor();
                    }
                }
                else {
                    if (data.compareTo(median.data) <= 0) {
                        // do nothing
                    }
                    else {
                        median = median.successor();
                    }
                }
            }
            size++;
        }

        public void remove(Node<T> node) {
            if (this.size == 1) {
                root = null;
                median = null;
            }
            if (root == null) return;

            Node<T> p = node.parent;
            T data = node.data;

            if (node.isLeaf()) {
                if (p != null && node.isLeftChild()) p.left = null;
                else if (p != null && node.isRightChild()) p.right = null;
                node.parent = null;
            } else if (node.hasTwoChildren()) {
                Node<T> successor = node.successor();
                node.data = successor.data;
                remove(successor);
            } else {
                Node<T> c = node.hasNoLeftChild() ? node.right : node.left;
                if (p != null && node.isLeftChild()) p.left = c;
                else if (p != null && node.isRightChild()) p.right = c;
                c.parent = p;
            }
        }
        // Returns the median of current data stream
        public Node<T> findMedian() {
            return median;
        }
    }
    class Node<T extends Comparable<T>> {
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;
        private T data;

        public Node(T data) {
            this.data = data;
        }
        @Override
        public String toString() {
            return "DATA { " + data + "} @" + this.hashCode();
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

        public void addNode(T data) {
            if (data.compareTo(this.data) >= 0) {
                if (right == null) {
                    right = new Node<T>(data);
                    right.parent = this;
                }
                else right.addNode(data);
            }
            else {
                if (left == null) {
                    left = new Node<T>(data);
                    left.parent = this;
                }
                else left.addNode(data);
            }
        }

        public Node<T> predecessor() {
            if (left != null)
                return left.rightMost();

            Node<T> predecessor = parent;
            Node<T> child = this;

            while (predecessor != null && child != predecessor.right) {
                child = predecessor;
                predecessor = predecessor.parent;
            }

            return predecessor;
        }

        public Node<T> successor() {
            if (right != null)
                return right.leftMost();

            Node<T> successor = parent;
            Node<T> child = this;

            while (successor != null && child != successor.left) {
                child = successor;
                successor = successor.parent;
            }

            return successor;
        }

        public Node<T> leftMost(){
            if (left == null)
                return this;
            return left.leftMost();
        }

        private Node<T> rightMost() {
            if (right == null) return this;
            return right.rightMost();
        }

    }

    private Tree<Integer> tree = new Tree<>();

    public MedianFinder() {
        // TODO: Construct/Initialise your data structures here

    }

    public void insert(int x) {
//        System.out.println("INSERTING " + x);
//        System.out.println("ROOT WAS: " + tree.root);
//        System.out.println("MEDIAN WAS: " + tree.median);
        tree.addNum(x);
//        System.out.println("NOW ROOT IS: " + tree.root);
//        System.out.println("NOW MEDIAN IS: " + tree.median);
        System.out.println("\n\n");
    }

    public int getMedian() {
//        System.out.println("ROOT IS: " + tree.root);
//        System.out.println("MEDIAN IS: " + tree.median);
//        System.out.println("SIZE IS: " + tree.size);
        Node<Integer> medianNode = tree.findMedian();
        int median = medianNode.data;
//        System.out.println("GOT MEDIAN: " + medianNode);

        if (tree.size % 2 == 0) tree.median = medianNode.predecessor();
        else tree.median = medianNode.successor();

        tree.remove(medianNode);
        tree.size--;
//        System.out.println("AFTER REMOVAL");
//        System.out.println("ROOT IS: " + tree.root);
//        System.out.println("MEDIAN IS: " + tree.median);
//        System.out.println("SIZE IS: " + tree.size);
        return median;
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();

        medianFinder.insert(3);
        medianFinder.insert(2);
        medianFinder.insert(4);
        medianFinder.insert(1);
        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
    }
}
