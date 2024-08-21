// Bst.java

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Iterator;

import java.util.List;

/**

* Provides an implementation of a binary search tree with no balance

* constraints, implemented with linked nodes.

*

*

*

*/

public class Bst<T extends Comparable<T>> implements Iterable<T> {

// ////////////////////////////////////////////////////////////////

// I M P L E M E N T T H E M I N M E T H O D B E L O W //

// ////////////////////////////////////////////////////////////////

/**

* Returns the smallest element in this binary search tree. If this tree is

* empty, this method returns null.

*/

   public T min() {
   
   // calling the private helper method to find the minimum value
   
   // recursively
   
      return min(root);
   
   }

// helper method to find the smallest value in bst.

   private T min(Node node) {
   
   // note: the smallest value in a BST will be the left most value. that
   
   // is the value at the left most node is the smallest value.
   
   // checking if node is null
   
      if (node == null) {
      
      // returning null, because the bst is empty
      
         return null;
      
      }
   
   // if there is a non null left node exists for this node, calling method
   
   // recursively passing left node as paremeter
   
      if (node.left != null) {
      
         return min(node.left);
      
      }
   
   // if the left node is null, then this is the left most node, returning
   
   // the value.
   
      return node.element;
   
   }

// //////////////////////////////////////////////////////////////////

// D O N O T M O D I F Y B E L O W T H I S P O I N T //

// //////////////////////////////////////////////////////////////////

// //////////

// Fields //

// //////////

// the root of this bst

   private Node root;

// the number of nodes in this bst

   private int size;

/** Defines the node structure for this bst. */

   private class Node {
   
      T element;
   
      Node left;
   
      Node right;
   
   /** Constructs a node containing the given element. */
   
      public Node(T elem) {
      
         element = elem;
      
      }
   
   }

// ////////////// //

// Sample driver //

// /////////////// /

/** Drives execution. */

   public static void main(String[] args) {
   
      Integer[] values = { 2, 4, 6, 8, 10 };
   
      Collections.shuffle(Arrays.asList(values));
   
      Bst<Integer> bst = new Bst<Integer>();
   
      for (Integer value : values) {
      
         bst.add(value);
      
      }
   
   // should print out 2:
   
      System.out.println(bst.min());
   
   }

// //////////////////

// M E T R I C S //

// //////////////////

/**

* Returns the number of elements in this bst.

*/

   public int size() {
   
      return size;
   
   }

/**

* Returns true if this bst is empty, false otherwise.

*/

   public boolean isEmpty() {
   
      return size == 0;
   
   }

/**

* Returns the height of this bst.

*/

   public int height() {
   
      return height(root);
   
   }

/**

* Returns the height of node n in this bst.

*/

   private int height(Node n) {
   
      if (n == null) {
      
         return 0;
      
      }
   
      int leftHeight = height(n.left);
   
      int rightHeight = height(n.right);
   
      return 1 + Math.max(leftHeight, rightHeight);
   
   }

// //////////////////////////////////

// A D D I N G E L E M E N T S //

// //////////////////////////////////

/**

* Ensures this bst contains the specified element. Uses an iterative

* implementation.

*/

   public void add(T element) {
   
   // special case if empty
   
      if (root == null) {
      
         root = new Node(element);
      
         size++;
      
         return;
      
      }
   
   // find where this element should be in the tree
   
      Node n = root;
   
      Node parent = null;
   
      int cmp = 0;
   
      while (n != null) {
      
         parent = n;
      
         cmp = element.compareTo(parent.element);
      
         if (cmp == 0) {
         
         // don't add a duplicate
         
            return;
         
         } else if (cmp < 0) {
         
            n = n.left;
         
         } else {
         
            n = n.right;
         
         }
      
      }
   
   // add element to the appropriate empty subtree of parent
   
      if (cmp < 0) {
      
         parent.left = new Node(element);
      
      } else {
      
         parent.right = new Node(element);
      
      }
   
      size++;
   
   }

/**

* Ensures this bst contains the specified element. Calls a recursive

* method.

*/

   public void put(T element) {
   
      root = put(element, root);
   
   }

/**

* Ensures this bst contains the specified element. Uses a recursive

* implementation.

*/

   private Node put(T element, Node n) {
   
      if (n == null) {
      
         size++;
      
         return new Node(element);
      
      }
   
      int cmp = element.compareTo(n.element);
   
      if (cmp < 0) {
      
         n.left = put(element, n.left);
      
      } else if (cmp > 0) {
      
         n.right = put(element, n.right);
      
      }
   
      return n;
   
   }

// ////////////////////

// T O S T R I N G //

// ////////////////////

/**

* Returns a string representation of the elements in this bst listed in

* ascending natural order.

*/

   @Override
   
   public String toString() {
   
      return inorderList(root).toString();
   
   }

/**

* Returns a List containing the elements of this bst in ascending natural

* order.

*/

   private List<T> inorderList(Node n) {
   
      List<T> list = new ArrayList<T>();
   
      buildInorderList(root, list);
   
      return list;
   
   }

/**

* Builds list from the elements of this bst in ascending natural order.

*/

   private void buildInorderList(Node n, List<T> list) {
   
      if (n != null) {
      
         buildInorderList(n.left, list);
      
         list.add(n.element);
      
         buildInorderList(n.right, list);
      
      }
   
   }

// //////////////////////

// I T E R A T I O N //

// //////////////////////

/**

* Provides an iterator over the elements in this bst. Elements will be

* returned in ascending natural order.

*/

   @Override
   
   public Iterator<T> iterator() {
   
      return inorderList(root).iterator();
   
   }

}