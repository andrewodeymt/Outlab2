import java.util.Comparator;
import java.util.Scanner;

import static java.lang.System.out;

public class BinarySearchTree<Key, Value> {
    private TreeNode<Key, Value> root;
    private Comparator<Key> comparator;

    public BinarySearchTree(Comparator<Key> comp) {
        root = null;
        comparator = comp;
    }

    public void add(Key k, Value v) {
        if (root == null) {
            root = new TreeNode<>(k, v, null, null);
        } else {
            root.add(k, v, comparator);
        }
    }

    public void traverse() {
        if (root == null) {
            out.println("Empty tree!");
        } else {
            root.traverse();
        }
    }

    public Value search(Key key) {
        if (root == null) {
            return null;
        }
        if (root.search(key, comparator) == null) {
            out.println("not found");
        } else {
            out.println(root.search(key, comparator));
        }
        return root.search(key, comparator);
    }

    public TreeNode<Key, Value> floor(Key key) {
        if (root == null) {
            return null;
        }
        if (root.floor(key, root, comparator) == null) {
            out.println("not found");
        } else {
            out.println(root.floor(key, root, comparator));
        }
        return root.floor(key, root, comparator);
    }

    public TreeNode<Key, Value> ceiling(Key key) {
        if (root == null) {
            return null;
        }
        if (root.ceiling(key, root, comparator) == null) {
            out.println("not found");
        } else {
            out.println(root.ceiling(key, root, comparator));
        }
        return root.ceiling(key, root, comparator);
    }

    public TreeNode<Key, Value> remove(Key key) {
        if (root == null) {
            return null;
        }
        if (root.remove(key, root, comparator) == null) {
            out.println("not found");
        } else {
            out.println(root.remove(key, root, comparator));
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

        boolean done = false;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(new AscendingStringComparator());
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
                    tree.add(lookupKey, lookupValue);
                    break;
                case "g":
                    tree.search(lookupKey);
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
                    tree.remove(lookupKey);
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
        if (node == null) {
            return null;
        }

        int compResult = comp.compare(key, pair.getKey());
        out.println("Compare Results: " + compResult);
        out.println("Node: " + node.toString());
        //out.println("Left Node: " + node.left.toString());

        if (compResult == 0) {
            return node;
        } else if (compResult <= 0) {
            //return floor(key, node.left, comp);
            int floorValue = (int) floor(key, node.left, comp).pair.getValue();
            return (floorValue <= (int) pair.getKey()) ? node.left : node;
        } else if (compResult > 0) {
            return floor(key, node.right, comp);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
/*          int floorValue = (int) floor(key, node.right, comp).pair.getValue();
            return (floorValue <= (int) pair.getKey()) ? node.right : node;
*/
            return node;
        }
    }

    /*
        // Returns the smallest key in the symbol table greater than or equal to key.
        public Key ceiling(Key key) {
            Node node = ceiling(root, key);
            if (node == null) {
                return null;
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
    */
    
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

    @Override
    public String toString() {
        return (String) pair.getValue();
    }
}

/* Comparator class */
class AscendingStringComparator implements Comparator<Integer> {
    public int compare(Integer s1, Integer s2) {
        return s1.compareTo(s2);
    }
}
