import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.System.out;

public class BinarySearchTreeRefactor<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    public class Node {
        private Key key;
        private Value value;

        protected Node left;
        protected Node right;
        protected Node root;

        protected int size; //# of nodes in subtree rooted here

        public Node(Key k, Value v, int s) {
            key = k;
            value = v;
            size = s;
        }
    }

    protected Node root;

    public int size() {
        return size(root);
    }

    protected int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.size;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        if (get(root, key) == null) {
            out.println("not found");
        } else {
            out.println(get(root, key));
        }
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return get(node.left, key);
        } else if (compare > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to contains() cannot be null");
        }
        return get(key) != null;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            return;
        }

        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }

        int compare = key.compareTo(node.key);

        if (compare < 0) {
            node.left = put(node.left, key, value);
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Key min() {
        if (root == null) {
            throw new NoSuchElementException("Empty binary search tree");
        }

        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    public Key max() {
        if (root == null) {
            throw new NoSuchElementException("Empty binary search tree");
        }

        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }

        return max(node.right);
    }

    private void traverse(Node root) {
        if (root.left != null) {
            traverse(root.left);
        }
        out.println(root.key + " " + root.value);
        if (root.right != null) {
            traverse(root.right);
        }
    }

    public void traverse() {
        if (root == null) {
            out.println("Empty tree!");
        } else traverse(root);
    }

    // Returns the highest key in the symbol table <= to key.
    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) {
            return null;
        }

        if (floor(root, key) == null) {
            out.println("not found");
        } else {
            out.println(get(root, node.key));
        }
        return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int compare = key.compareTo(node.key);

        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return floor(node.left, key);
        } else {
            Node rightNode = floor(node.right, key);
            if (rightNode != null) {
                return rightNode;
            } else {
                return node;
            }
        }
    }

    // Returns the smallest key in the symbol table >= to key.
    public Key ceiling(Key key) {
        Node node = ceiling(root, key);
        if (node == null) {
            return null;
        }

        if (ceiling(root, key) == null) {
            out.println("not found");
        } else {
            out.println(get(root, node.key));
        }
        return node.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int compare = key.compareTo(node.key);

        if (compare == 0) {
            return node;
        } else if (compare > 0) {
            return ceiling(node.right, key);
        } else {
            Node leftNode = ceiling(node.left, key);
            if (leftNode != null) {
                return leftNode;
            } else {
                return node;
            }
        }
    }

    //Helper function for deleting safely min values on nodes
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }

        node.left = deleteMin(node.left);
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public void delete(Key key) {
        if (isEmpty()) {
            return;
        }

        if (!contains(key)) {
            return;
        }

        if (delete(root, key) == null) {
            out.println("not found");
        } else {
            out.println(delete(root, key));
        }
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = delete(node.left, key);
        } else if (compare > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node aux = node;
                node = min(aux.right);
                node.right = deleteMin(aux.right);
                node.left = aux.left;
            }
        }

        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        if (low == null) {
            throw new IllegalArgumentException("First argument to keys() cannot be null");
        }
        if (high == null) {
            throw new IllegalArgumentException("Second argument to keys() cannot be null");
        }

        Queue<Key> queue = new Queue<>();
        keys(root, queue, low, high);
        return queue;
    }

    //Allows for comparison of key values; similar to comparator<key>
    private void keys(Node node, Queue<Key> queue, Key low, Key high) {
        if (node == null) {
            return;
        }

        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);

        if (compareLow < 0) {
            keys(node.left, queue, low, high);
        }

        if (compareLow <= 0 && compareHigh >= 0) {
            queue.enqueue(node.key);
        }

        if (compareHigh > 0) {
            keys(node.right, queue, low, high);
        }
    }

    //Menu for program
    public static void printMenu() {
        out.println("\n\nWelcome to my BinarySearchTree.");
        out.println("Here are the commands you can use:");
        out.println("(p)ut key value");
        out.println("(g)et key");
        out.println("(f)loor key");
        out.println("(c)eiling key");
        out.println("(s)how key-value pairs in order");
        out.println("(r)emove key");
        out.println("e(x)it");
    }

    public static void main(String[] args) {

        boolean done = false;
        BinarySearchTreeRefactor<Integer, String> tree = new BinarySearchTreeRefactor<>();
        Scanner sc = new Scanner(System.in);
        printMenu();

        while (!done) {
            //Capture the entire line entered uses StringTokenizer to parse input
            String stringLine = sc.nextLine();
            String[] splitArray = stringLine.trim().split("\\s+");
            String choice = splitArray[0];
            int lookupKey = -99;
            String lookupValue = "";

            if (splitArray.length == 2) {
                lookupKey = Integer.parseInt(splitArray[1]);
            }
            if (splitArray.length == 3) {
                lookupKey = Integer.parseInt(splitArray[1]);
                lookupValue = splitArray[2];
            }

            switch (choice) {
                case "p":
                    tree.put(lookupKey, lookupValue);
                    break;
                case "g":
                    tree.get(lookupKey);
                    break;
                case "f":
                    tree.floor(lookupKey);
                    break;
                case "c":
                    tree.ceiling(lookupKey);
                    break;
                case "s":
                    tree.traverse();
                    break;
                case "r":
                    tree.delete(lookupKey);
                    out.println("In-order traverse the tree after removing " + lookupKey);
                    tree.traverse();
                    break;
                case "x":
                    done = true;
                    out.println("Bye!");
                    break;
                default:
                    out.println("Invalid option selected");
                    break;
            }
        }
        sc.close();
    }

    private BinarySearchTreeRefactor createBinaryTree() {
        BinarySearchTreeRefactor bt = new BinarySearchTreeRefactor();

        bt.put(5, "cat");
        bt.put(10, "dog");
        bt.put(15, "lion");
        bt.put(7, "marteen");
        bt.put(9, "lynx");
        bt.put(18, "cougar");
        bt.put(19, "mountain lion");
        bt.put(13, "bobcat");
        bt.put(23, "caracals");

        return bt;
    }

/*
    @Test
    public void givenABinaryTreeThenTreeContainsElements() {
        BinarySearchTree bt = createBinaryTree();

        assertTrue(bt.containsNode(7));
        assertTrue(bt.containsNode(13));

        assertFalse(bt.containsNode(1));
    }
*/

}
