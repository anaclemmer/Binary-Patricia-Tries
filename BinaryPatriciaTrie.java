package bpt;

import bpt.UnimplementedMethodException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>{@code BinaryPatriciaTrie} is a Patricia Trie over the binary alphabet &#123;	 0, 1 &#125;. By restricting themselves
 * to this small but terrifically useful alphabet, Binary Patricia Tries combine all the positive
 * aspects of Patricia Tries while shedding the storage cost typically associated with tries that
 * deal with huge alphabets.</p>
 *
 * @author Ana Clemmer
 */
public class BinaryPatriciaTrie {

    /* We are giving you this class as an example of what your inner node might look like.
     * If you would prefer to use a size-2 array or hold other things in your nodes, please feel free
     * to do so. We can *guarantee* that a *correct* implementation exists with *exactly* this data
     * stored in the nodes.
     */
    private static class TrieNode {
        private TrieNode left, right;
        private String str;
        private boolean isKey;

        // Default constructor for your inner nodes.
        TrieNode() {
            this("", false);
        }

        // Non-default constructor.
        TrieNode(String str, boolean isKey) {
            left = right = null;
            this.str = str;
            this.isKey = isKey;
        }
    }

    private TrieNode root;
    int count;

    /**
     * Simple constructor that will initialize the internals of {@code this}.
     */
    public BinaryPatriciaTrie() {
    	this.root = new TrieNode();
    	root.left = null;
    	root.right = null;
    	root.str = "";
    	root.isKey = false;
    	this.count = 0;
    }

    /**
     * Searches the trie for a given key.
     *
     * @param key The input {@link String} key.
     * @return {@code true} if and only if key is in the trie, {@code false} otherwise.
     */
    public boolean search(String key) {
    	if (count == 0) {
    		return false;
    	}
    	TrieNode node = root;
    	String matchString = key;
    	int match = 0;

	    while (matchString.length() > 0) {
	    	if (matchString.charAt(0) == '0') {
	    		if (node.left != null) {
	    			node = node.left;
	    			if (node.str.length()>key.length() || node.str.length()>matchString.length()) {
		    			return false;
		    		}
	    			match = commonPrefix(node.str,matchString);
	    			if (match == 0) {
		    			return false;
		    		} else {
		    			matchString = matchString.substring(match);
		    		}
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		if (node.right != null) {
	    			node = node.right;
	    			if (node.str.length()>key.length() || node.str.length()>matchString.length()) {
		    			return false;
		    		}
	    			match = commonPrefix(node.str,matchString);
	    			if (match == 0) {
		    			return false;
		    		} else {
		    			matchString = matchString.substring(match);
		    		}
	    		} else {
	    			return false;
	    		}
	    	}

	    }
	    if (node.isKey) {
			return true;
		} else {
			return false;
		}
    }
    

    /**
     * Inserts key into the trie.
     *
     * @param key The input {@link String}  key.
     * @return {@code true} if and only if the key was not already in the trie, {@code false} otherwise.
     */
    public boolean insert(String key) {
    	System.out.print("Inserting: " + key + "\n");
    	if (count == 0) {
    		if (key.charAt(0) == '0') {
        		root.left = new TrieNode();
        		root.left.str = key;
        		root.left.isKey = true;
        		count++;
        		return true;
        	} else {
        		root.right = new TrieNode();
        		root.right.str = key;
        		root.right.isKey = true;
        		count++;
        		return true;
        	}
    	} else {
    		if (search(key)) {
    			return false;
    		}
    		if (key.charAt(0) == '0') {
    			TrieNode help = insertHelp(root.left,key);
    			if (help == null) {
    				return false;
    			}
    			root.left = help;
    		} else {
    			TrieNode help = insertHelp(root.right,key);
    			if (help == null) {
    				return false;
    			}    		
    			root.right = help;
    		}
    		count++;
    		return true;
    	}
    	
    }
    
    private TrieNode insertHelp(TrieNode node, String key) {
    	if (node == null) {
    		node = new TrieNode();
    		node.str = key;
    		node.isKey = true;
    		//count++;
    		return node;
    	} else {
    		int pre = commonPrefix(node.str,key);   		
    		if (pre < node.str.length()) {
    			String nodeLeft = node.str.substring(pre);
    			node.str = nodeLeft;
    			TrieNode split = new TrieNode();
    			if (nodeLeft.charAt(0) == '0') {
    				split.left = node;
				} else {
					split.right = node;
				}
    			if (pre == key.length()) {
    				split.str = key;
    				split.isKey = true;
    			} else {
    				split.str = key.substring(0,pre); //matched section
    				String keyLeft = key.substring(pre);
    				if (keyLeft.charAt(0) == '0') {
    					split.left = insertHelp(split.left,keyLeft);
    				} else {
    					split.right = insertHelp(split.right,keyLeft);
    				}
    			}
				return split;
    		} else { //prefix == node
    			if (pre < key.length()) {
    				String keyLeft = key.substring(pre);
    				if (keyLeft.charAt(0) == '0') {
    					node.left = insertHelp(node.left,keyLeft);
    				} else {
    					node.right = insertHelp(node.right,keyLeft);
    				}
    			} else {
    				//node.str = key
    				if (node.isKey) { //key already in trie
    					return null;
    				}
    				node.isKey = true;
    			}
    			return node;
    		}
    	}
    }
    
    private int commonPrefix(String node, String key) {
    	int i = 0;
    	int nl = node.length();
    	int kl = key.length();
    	while (i < nl && i < kl) {
    		if (node.charAt(i) == (key.charAt(i))) {
    			i++;
    		} else {
    			break;
    		}
    	}
    	return i;
    }


    /**
     * Deletes key from the trie.
     *
     * @param key The {@link String}  key to be deleted.
     * @return {@code true} if and only if key was contained by the trie before we attempted deletion, {@code false} otherwise.
     */
    public boolean delete(String key) {
    	System.out.print("Removing" + key + "\n");
    	if (count == 0) {
    		return false;
    	}
    	if (!search(key)) {
    		return false;
    	}    
    	if (deleteHelp(root,key,null) == null) {
    		return false;
    	}
    	count--;
		return true;
    }
    
    private TrieNode deleteHelp(TrieNode node, String match, TrieNode prev) { 
    	int pre = commonPrefix(node.str,match); 
    	match = match.substring(pre);
    	if (match.length() == 0) {
    		if (node.left != null && node.right != null) { //both children
    	    	node.isKey = false;
    	    } else if (node.left == null && node.right != null) {
    	    	TrieNode temp = node.right;
    	    	node.str = node.str + temp.str;
    	    	node.left = temp.left;
    	    	node.right = temp.right;
    	    	node.isKey = temp.isKey;
    	    } else if (node.left != null && node.right == null) {
    	    	TrieNode temp = node.left;
    	    	node.str = node.str + temp.str;
    	    	node.left = temp.left;
    	    	node.right = temp.right;
    	    	node.isKey = temp.isKey;
    	    } else {
    	    	node = null;
    	    }
    		if (prev != null && !prev.isKey) {
	    		if (prev.left != null && prev.right == null) {
		    		TrieNode temp = prev.left;
		    		prev.str = prev.str + temp.str;
		    		prev.left = temp.left;
		    		prev.right = temp.right;
		    		prev.isKey = temp.isKey;
		    	} else if (prev.left == null && prev.right != null) {
		    		TrieNode temp = prev.right;
		    		prev.str = prev.str + temp.str;
		    		prev.left = temp.left;
		    		prev.right = temp.right;
		    		prev.isKey = temp.isKey;
		    	}
	    	}
    	    return prev;
    	}
    	if (match.charAt(0) == '0') {
			node = deleteHelp(node.left,match,node);
		} else {
			node = deleteHelp(node.right,match,node);
		}
    	return node;
    }
   

    /**
     * Queries the trie for emptiness.
     *
     * @return {@code true} if and only if {@link #getSize()} == 0, {@code false} otherwise.
     */
    public boolean isEmpty() {
    	return (count == 0);
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return The number of keys in the tree.
     */
    public int getSize() {
    	return count;
    }

    /**
     * <p>Performs an <i>inorder (symmetric) traversal</i> of the Binary Patricia Trie. Remember from lecture that inorder
     * traversal in tries is NOT sorted traversal, unless all the stored keys have the same length. This
     * is of course not required by your implementation, so you should make sure that in your tests you
     * are not expecting this method to return keys in lexicographic order. We put this method in the
     * interface because it helps us test your submission thoroughly and it helps you debug your code! </p>
     *
     * <p>We <b>neither require nor test </b> whether the {@link Iterator} returned by this method is fail-safe or fail-fast.
     * This means that you  do <b>not</b> need to test for thrown {@link java.util.ConcurrentModificationException}s and we do
     * <b>not</b> test your code for the possible occurrence of concurrent modifications.</p>
     *
     * <p>We also assume that the {@link Iterator} is <em>immutable</em>, i,e we do <b>not</b> test for the behavior
     * of {@link Iterator#remove()}. You can handle it any way you want for your own application, yet <b>we</b> will
     * <b>not</b> test for it.</p>
     *
     * @return An {@link Iterator} over the {@link String} keys stored in the trie, exposing the elements in <i>symmetric
     * order</i>.
     */
    public Iterator<String> inorderTraversal() {
    	System.out.print("traversal\n");
    	ArrayList<String> list = new ArrayList<String>();
    	inOrder(root,"",list);
    	return list.iterator();
    }
    
    private void inOrder(TrieNode curr, String prefix, ArrayList<String> list) {
    	if (curr == null) {
    		return;
    	}
    	inOrder(curr.left,prefix+curr.str,list);
    	if (curr.isKey) {
    		list.add(prefix+curr.str);
    	}
    	inOrder(curr.right,prefix+curr.str,list);
    }
    

    /**
     * Finds the longest {@link String} stored in the Binary Patricia Trie.
     * @return <p>The longest {@link String} stored in this. If the trie is empty, the empty string &quot;&quot; should be
     * returned. Careful: the empty string &quot;&quot;is <b>not</b> the same string as &quot; &quot;; the latter is a string
     * consisting of a single <b>space character</b>! It is also <b>not the same as the</b> null <b>reference</b>!</p>
     *
     * <p>Ties should be broken in terms of <b>value</b> of the bit string. For example, if our trie contained
     * only the binary strings 01 and 11, <b>11</b> would be the longest string. If our trie contained
     * only 001 and 010, <b>010</b> would be the longest string.</p>
     */
    public String getLongest() {
    	if (count == 0) {
    		return "";
    	}
    	return findLong(root,"","");
    }
    
    private String findLong(TrieNode node, String prefix, String longest) { 
    	if (node == null) {
    		return longest;
    	}
    	String nodeString = prefix + node.str;
        if (node.isKey && nodeString.length() > longest.length()) {
        	longest = nodeString;
        } else if (node.isKey && nodeString.length() == longest.length()) {
        	for (int i = 0; i < longest.length(); i++) {
        		if (nodeString.charAt(i) > longest.charAt(i)) {
        			longest = nodeString;
        			break;
        		}
        	}
        }
        longest = findLong(node.left, nodeString, longest);
        longest = findLong(node.right, nodeString, longest);
        return longest;
    }

    /**
     * Makes sure that your trie doesn't have splitter nodes with a single child. In a Patricia trie, those nodes should
     * be pruned.
     * @return {@code true} iff all nodes in the trie either denote stored strings or split into two subtrees, {@code false} otherwise.
     */
    public boolean isJunkFree(){
        return isEmpty() || (isJunkFree(root.left) && isJunkFree(root.right));
    }

    private boolean isJunkFree(TrieNode n){
        if(n == null){   // Null subtrees trivially junk-free
            return true;
        }
        if(!n.isKey){   // Non-key nodes need to be strict splitter nodes
            return ( (n.left != null) && (n.right != null) && isJunkFree(n.left) && isJunkFree(n.right) );
        } else {
            return ( isJunkFree(n.left) && isJunkFree(n.right) ); // But key-containing nodes need not.
        }
    }
}
