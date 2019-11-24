package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;
import ua.edu.ucu.utils.Queue;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (int i =0; i < strings.length; i++) {
            while (strings[i].contains(" ")) {
                int indx = strings[i].indexOf(" ");
                if (indx == 0) {
                    strings[i] =  strings[i].substring(1, strings[i].length());
                } else{
                    String newWord = strings[i].substring(0,indx);
                    strings[i] =  strings[i].substring(indx+1, strings[i].length());
                    if (newWord.length() > 2) {
                        Tuple word = new Tuple(newWord, newWord.length());
                        this.trie.add(word);
                    }
                }
            }
            if (strings[i].length() > 2) {
                Tuple word = new Tuple(strings[i], strings[i].length());
                this.trie.add(word);
            }
        }
        return this.trie.size();
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() >= 2) {
            return this.trie.wordsWithPrefix(pref);
        } else {
            return null;
        }
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() >= 2) {
            Queue q = new Queue();
            for (String word : this.wordsWithPrefix(pref)) {
                if (word.length() <= 2+k) {
                    q.enqueue(word);
                }
            }
            return q;
        } else {
            return null;
        }
    }

    public int size() {
        return this.trie.size();
    }

}
