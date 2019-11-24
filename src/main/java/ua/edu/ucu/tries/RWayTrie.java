package ua.edu.ucu.tries;
import ua.edu.ucu.utils.Queue;


public class RWayTrie implements Trie {
    private static int R = 256;

    private Node root;
    private int n = 0;

    private static class Node {
        int val;
        Node[] next = new Node[R];
    }

    public RWayTrie() {}

    @Override
    public void add(Tuple t) {
        root = add(root, t.term, t.weight, 0);
    }

    private Node add(Node x, String key, int val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            if (x.val == 0) {
                n++;
            }
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = add(x.next[c], key, val, d+1);
        return x;
    }

      public Object get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    @Override
    public boolean contains(String word) {
        if (word == null ) {
            throw new IllegalArgumentException("Argument to contains() is null");
        }
        return get(root, word, 0) != null;
    }

    private Node get(Node x,String word, int d) {
        if (x == null) {
            return null;
        }
        if (d == word.length()) {
            return x;
        }
        char c = word.charAt(d);
        return get(x.next[c], word, d+1);
    }

    @Override
    public boolean delete(String word) {
        if (word == null) {
            throw new IllegalArgumentException(("Argument to delete(0 in null"));
        }
        boolean result = contains(word);
        root = delete(root, word, 0);
        return result;
    }

    private Node delete(Node x, String word, int d) {
        if (x == null) return null;
        if (d == word.length()) {
            if (x.val != 0) n--;
            x.val = 0;
        } else {
            char c = word.charAt(d);
            x.next[c] = delete(x.next[c], word, d+1);
        }
        if (x.val != 0) return x;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) return x;
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return this.wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root,s, 0), s, q);
        return q;
    }

    private void collect( Node x, String pre, Queue q) {
        if (x == null) {return;}
        if (x.val != 0) {
            q.enqueue(pre);
        }
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre+c, q);
        }
    }

    @Override
    public int size() {
        return n;
    }
}
