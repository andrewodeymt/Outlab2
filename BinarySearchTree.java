import java.util.Comparator;

import static java.lang.System.out;

public class BinarySearchTree<Key, Value> {
    private TreeNode<Key, Value> root;
    private Comparator<Key> comparator;

    public BinarySearchTree(Comparator<Key> comp) {
        root = null;
        comparator = comp;
    }

    public void add(Key k, Value v) {
        if (root == null) root = new TreeNode<>(k, v, null, null);
        else root.add(k, v, comparator);
    }

    public void traverse() {
        if (root == null) {
            out.println("Empty tree!");
        } else root.traverse();
    }

    public Value search(Key key) {
        if (root == null) {
            return null;
        }
        out.println(root.search(key, comparator));
        return root.search(key, comparator);
    }

    public TreeNode<Key, Value> floor(Key key) {
        if (root == null) {
            return null;
        }
        //Currently returning TreeNode Address not value
        out.println(root.floor(key, root, comparator));
        return root.floor(key, root, comparator);
    }

    public TreeNode<Key, Value> ceiling(Key key) {
        if (root == null) {
            return null;
        }
        out.println(root.ceiling(key, root, comparator));
        return root.ceiling(key, root, comparator);
    }

    public TreeNode<Key, Value> remove(Key key) {
        if (root == null) {
            return null;
        }
        return root.remove(key, root, comparator);
    }

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
        /*
        boolean done = false;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(new AscendingStringComparator());
        Scanner sc = new Scanner(System.in);
        printMenu();

        while (!done) {
            //Capture the entire line entered uses StringTokenizer to parse input
            String choice = sc.nextLine();
            StringTokenizer st = new StringTokenizer(choice, " ");
            //out.println("Choice: " + choice);
            String st1 = st.nextToken();
            String st2 = st.nextToken();
            String st3 = st.nextToken();
            Integer num2 = Integer.parseInt(st2);
            //out.println("String1: " + st1 + " String2: " + st2 + " String3: " + st3 + " Num2: " + num2);

            switch (st1) {
                case "p":
                    tree.add(num2, st3);
                    break;
                case "g":
                    tree.search(num2);
                    break;
                case "f":
                    tree.floor(num2);
                    break;
                case "c":
                    tree.ceiling(num2);
                    break;
                case "s":
                    tree.traverse();
                    break;
                case "r":
                    tree.remove(num2);
                    out.println("In-order traverse the tree after removing " + num2);
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
        */

        out.println("Create a tree");
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<Integer, String>(new AscendingStringComparator());
        tree.add(5, "e");
        tree.add(8, "h");
        tree.add(2, "b");
        tree.add(4, "d");
        tree.add(7, "g");
        tree.add(6, "f");
        tree.add(1, "a");
        tree.add(9, "i");
        out.println("In-order traverse the tree");
        tree.traverse();
        out.println("The value associated with 5 is " + tree.search(5));
        tree.remove(1);
        out.println("In-order traverse the tree after removing 1");
        tree.traverse();
        tree.remove(9);
        out.println("In-order traverse the tree after removing 9");
        tree.traverse();
        tree.remove(5);
        out.println("In-order traverse the tree after removing 5");
        tree.traverse();
    }
}

/* Tree Node class */
class TreeNode<Key, Value> {
    private Node<Key, Value> pair;
    private TreeNode<Key, Value> left;
    private TreeNode<Key, Value> right;

    public TreeNode(Key k, Value v, TreeNode<Key, Value> l, TreeNode<Key, Value> r) {
        pair = new Node<>(k, v);
        left = l;
        right = r;
    }

    public void add(Key key, Value val, Comparator<Key> comp) {
        int compResult = comp.compare(key, pair.getKey());

        if (compResult == 0) {
            pair.setValue(val);
        } else if (compResult < 0) {
            if (left == null) left = new TreeNode<>(key, val, null, null);
            else left.add(key, val, comp);
        } else {
            if (right == null) right = new TreeNode<>(key, val, null, null);
            else right.add(key, val, comp);
        }
    }

    public void traverse() {
        if (left != null) {
            left.traverse();
        }
        out.println(pair);
        if (right != null) {
            right.traverse();
        }
    }

    
    public TreeNode<Key, Value> floor(Key key, TreeNode<Key, Value> node, Comparator<Key> comp) {
        int compResult = comp.compare(key, pair.getKey());
        if (node == null) {
            return null;
        } else if (compResult == 0) {
            return node;
        } else if (compResult > 0) {
            node.left = floor(key, node.left, comp);
        } else {
            node.right = floor(key, node.right, comp);
        }
        return node;
    }

    public TreeNode<Key, Value> ceiling(Key key, TreeNode<Key, Value> node, Comparator<Key> comp) {
        int compResult = comp.compare(key, pair.getKey());
        if (compResult == 0) {
            return node;
        }
        return node;
    }

    public Value search(Key key, Comparator<Key> comp) {
        int compResult = comp.compare(key, pair.getKey());

        if (compResult == 0) {
            return pair.getValue();
        } else if (compResult < 0) {
            if (left == null) {
                return null;
            } else {
                return left.search(key, comp);
            }
        } else {
            if (right == null) {
                return null;
            }
            return right.search(key, comp);
        }
    }

    public TreeNode<Key, Value> remove(Key key, TreeNode<Key, Value> node, Comparator<Key> comp) {
        int compResult = comp.compare(key, node.pair.getKey());

        if (node == null) {
            return null;
        } else if (compResult < 0) {
            node.left = remove(key, node.left, comp);
        } else if (compResult > 0) {
            node.right = remove(key, node.right, comp);
        } else {
            node = removeNode(node, comp);
        }
        return node;
    }

    private TreeNode<Key, Value> removeNode(TreeNode<Key, Value> node, Comparator<Key> comp) {
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            Node<Key, Value> p = getPredecessor(node.left);
            node.pair = p;
            node.left = remove(p.getKey(), node.left, comp);
            return node;
        }
    }

    private Node<Key, Value> getPredecessor(TreeNode<Key, Value> subtree) {
        TreeNode<Key, Value> tmp = subtree;
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        return tmp.pair;
    }
}

/* Comparator class */
class AscendingStringComparator implements Comparator<Integer> {
    public int compare(Integer s1, Integer s2) {
        return s1.compareTo(s2);
    }
}
