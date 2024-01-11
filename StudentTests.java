package bpt;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Iterator;

/**
 * A jUnit test suite for {@link BinaryPatriciaTrie}.
 *
 * @author --- Ana Clemmer ----.
 */
public class StudentTests {


    @Test public void testEmptyTrie() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("Trie should be empty",trie.isEmpty());
        assertEquals("Trie size should be 0", 0, trie.getSize());

        assertFalse("No string inserted so search should fail", trie.search("0101"));

    }

    @Test public void testFewInsertionsWithSearch() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully",trie.insert("00000"));
        assertTrue("String should be inserted successfully",trie.insert("00011"));
        assertFalse("Search should fail as string does not exist",trie.search("000"));

    }
    
    @Test public void testManyInsertionsWithSearch() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully",trie.insert("00000"));
        assertTrue("String should be inserted successfully",trie.insert("00011"));
        assertTrue("String should be inserted successfully",trie.insert("000"));
        assertFalse("String should not be inserted successfully",trie.insert("00000"));
        assertTrue("Search should succeed",trie.search("000"));

    }

    //testing isEmpty function
    @Test public void testFewInsertionsWithDeletion() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        trie.insert("000");
        trie.insert("001");
        trie.insert("011");
        trie.insert("1001");
        trie.insert("1");

        assertFalse("After inserting five strings, the trie should not be considered empty!", trie.isEmpty());
        assertEquals("After inserting five strings, the trie should report five strings stored.", 5, trie.getSize());

        trie.delete("0"); // Failed deletion; should affect exactly nothing.
        assertEquals("After inserting five strings and requesting the deletion of one not in the trie, the trie " +
                "should report five strings stored.", 5, trie.getSize());
        assertTrue("After inserting five strings and requesting the deletion of one not in the trie, the trie had some junk in it!",
                trie.isJunkFree());

        trie.delete("011"); // Successful deletion
        assertEquals("After inserting five strings and deleting one of them, the trie should report 4 strings.", 4, trie.getSize());
        assertTrue("After inserting five strings and deleting one of them, the trie had some junk in it!",
                trie.isJunkFree());
    }
    
    @Test
    public void test2() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
        trie.insert("00000");
        trie.insert("00011");
        trie.insert("000");
        trie.insert("11011");
        trie.insert("1");
        trie.inorderTraversal();
        assertEquals("11011",trie.getLongest());
    }
    
    @Test
    public void test5() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
        trie.insert("100");
        trie.insert("101");
        trie.insert("0");
        trie.insert("10111");
        trie.insert("1011");
    }
    
    @Test
    public void testComplexTraversal() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("100");
        trie.insert("101");
        trie.insert("0");
        trie.insert("10111");
        trie.insert("1011");
        trie.insert("1010");
        trie.insert("1");
        trie.insert("111");
        trie.insert("11");
        trie.insert("110");
        trie.insert("10110");
        assertEquals(11,trie.getSize());
        trie.delete("1");
        trie.delete("110");
        trie.delete("10111");
        Iterator<String> it = trie.inorderTraversal();
    	while(it.hasNext()) {
    		System.out.print(it.next() + "\n");
    	}
    }
    
    @Test
    public void testSimpleTraversal() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("000001");
    	trie.insert("010010");
    	trie.insert("010111");
    	trie.insert("011011");
    	trie.insert("011101");
    	trie.insert("011111");
    	trie.insert("100100");
    	trie.insert("110001");
    	trie.insert("110110");
    	trie.delete("000001");
    	trie.delete("011101");
    	trie.delete("110001");
    	Iterator<String> it = trie.inorderTraversal();
    	while(it.hasNext()) {
    		System.out.print(it.next() + "\n");
    	}
    }
    
    @Test
    public void testDelete() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.delete("0");
    	trie.insert("0");
    	trie.insert("00");
    	trie.insert("01");
    	trie.delete("0");
    	trie.insert("0");
    	trie.delete("01");
    	trie.delete("0");
    	trie.insert("0");
    	trie.insert("01");
    	trie.delete("00");
    	trie.delete("0");
    	trie.delete("01");
    	trie.insert("10");
    	trie.insert("100");
    	trie.insert("101");
    	trie.insert("1000");
    	trie.insert("10000");
    	trie.insert("10001");
    	trie.insert("10010");
    	trie.insert("10011");
    	trie.insert("1001");
    	trie.delete("100");
    	Iterator<String> it = trie.inorderTraversal();
    	while(it.hasNext()) {
    		System.out.print(it.next() + "\n");
    	}
    }
    
    @Test
    public void test4() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("100");
    	trie.insert("101");
    	trie.delete("100");
    	trie.delete("101");
    	assertFalse(trie.search("100"));
    	assertFalse(trie.search("101"));
    }
    
}