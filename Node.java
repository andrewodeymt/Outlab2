public class Node<Key, Value> {
    private Key key;
    private Value value;

    public Node(Key k, Value v) {
        key = k;
        value = v;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value v) {
        value = v;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
