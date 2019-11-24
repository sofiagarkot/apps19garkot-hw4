
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void TestLoadList() {
        String[] strings = {"Sofia", "Denys", "Denden", "Sofsof","Lalala"};
        PrefixMatches prefixm = new PrefixMatches(new RWayTrie());
        int result = prefixm.load(strings);
        assertEquals(5, result);
    }

    @Test
    public void TestLoadListLessThan2Length() {
        String[] strings = {"S", "kys", "Denden", "Sofsof","Lalala"};
        PrefixMatches prefixm = new PrefixMatches(new RWayTrie());
        int result = prefixm.load(strings);
        assertEquals(4, result);
    }

    @Test
    public void TestLoadWithWhitespaces() {
        String[] strings = {"Sofia OOPTHEBEST Denys Ivan Denden Kitty"};
        PrefixMatches prefixm = new PrefixMatches(new RWayTrie());
        int result = prefixm.load(strings);
        assertEquals(6, result);
    }

    @Test
    public void TestLoadWithSameObjects() {
        String[] strings = {"Sofia", "Ivan", "Denys", "Ivan Sofia"};
        PrefixMatches prefixm = new PrefixMatches(new RWayTrie());
        int result = prefixm.load(strings);
        assertEquals(3, result);
    }

    @Test
    public void TestContains() {
        boolean resultTrue = pm.contains("abc");
        boolean resultFalse = pm.contains("bbbb");

        assertTrue(resultTrue==true);
        assertTrue(resultFalse == false);
    }

    @Test
    public void TestDelete() {
        String[] strings = {"abc", "bbc", "ccb", "nnc"};
        PrefixMatches prefixm = new PrefixMatches(new RWayTrie());
        prefixm.load(strings);

        boolean resultTrue = prefixm.delete("bbc");
        boolean resultFalse = prefixm.delete("Jerry");

        assertTrue(resultTrue == true);
        assertTrue(resultFalse == false);

        assertEquals(3, prefixm.size());
    }

    @Test
    public void testWordsWithTooShortPrefix() {
        String pref = "a";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = null;

        assertEquals(result, expResult);
    }

    @Test
    public void testWordsWithTooShortPrefixAndSomeK() {
        String pref = "a";

        Iterable<String> result = pm.wordsWithPrefix(pref, 2);

        String[] expResult = null;

        assertEquals(result, expResult);
    }


}
