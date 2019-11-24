package ua.edu.ucu.tries;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

public class RWayTrieTest {

    private RWayTrie rw;
    @Before
    public void init() {
        rw = new RWayTrie();
    }

    @Test
    public void testAdd() {
        String[] words = {"hey", "hello","how","are","you"};
        for (String w:words) {
            rw.add(new Tuple(w, w.length()));
        }
        assertEquals(5, rw.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgumentGet() {
        String[] words = {"hey", "hello","how","are","you"};
        for (String w:words) {
            rw.add(new Tuple(w, w.length()));
        }
        rw.get(null);
    }

    @Test
    public void testGet() {
        String[] words = {"hey", "hello","how","are","you"};
        for (String w:words) {
            rw.add(new Tuple(w, w.length()));
        }
        assertEquals(5, rw.get("hello"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgumentContains() {
        String[] words = {"hey", "hello","how","are","you"};
        for (String w:words) {
            rw.add(new Tuple(w, w.length()));
        }
        rw.contains(null);
    }

    @Test
    public void testContains() {
        String[] words = {"hey", "hello","how","are","you"};
        for (String w:words) {
            rw.add(new Tuple(w, w.length()));
        }
        assertTrue(rw.contains("hey")==true);
        assertTrue(rw.contains("lool")==false);
    }
    @Test
    public void testwordsWithPrefix() {
        RWayTrie st = new RWayTrie();
        String[] lst = {"Borys", "Borry", "Borbor", "Boryborys", "Bor", "Bobok", "Boryslav"};
        for ( int i= 0; i < lst.length; i++) {
            Tuple t = new Tuple (lst[i], lst[i].length());
            st.add(t);
        }

        String pref = "Bor";

        Iterable<String> result = st.wordsWithPrefix(pref);

        String[] expResult = {"Borys", "Borry", "Borbor", "Boryborys", "Bor","Boryslav"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWords() {
        RWayTrie st = new RWayTrie();
        String[] lst = {"Borys", "Borry", "Borbor", "Boryborys", "Bor", "Bobok", "Boryslav"};
        for ( int i= 0; i < lst.length; i++) {
            Tuple t = new Tuple (lst[i], lst[i].length());
            st.add(t);
        }
        Iterable<String> result = st.words();

        String[] expResult = {"Borys", "Borry", "Borbor", "Boryborys", "Bor", "Bobok", "Boryslav"};
        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void TestDelete() {
        RWayTrie st = new RWayTrie();
        String[] lst = {"Borys", "Borry", "Borbor", "Boryborys", "Bor", "Bobok", "Boryslav"};
        for ( int i= 0; i < lst.length; i++) {
            Tuple t = new Tuple (lst[i], lst[i].length());
            st.add(t);
        }

        boolean resultFalse = st.delete("Ivan");
        boolean resultTrue = st.delete("Borry");

        assertTrue(resultFalse==false);
        assertTrue(resultTrue==true);
        assertEquals(6, st.size());
    }
}
