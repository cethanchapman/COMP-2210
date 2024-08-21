import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Ethan Chapman (cec0161@auburn.edu)
 *
 */
 
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
    
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
    
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
    
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
    
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
    
   public boolean add(T element) {
      Node n = new Node(element);
      Node current = front;
      if((this.contains(element)) || (element == null)) {
         return false;
      }
      if (isEmpty()) {
         front = n;
         rear = n;
      }
      else if (front.element.compareTo(element) > 0) {
         n.next = front;
         front.prev = n;
         front = n;
      }
      else {
         rear.next = n;
         n.prev = rear;
         rear = n;
         if(n.prev.element.compareTo(element) > 0) {
            while (current != null) {
               if(current.element.compareTo(element) > 0) {
                  rear = n.prev;
                  current.prev.next = n;
                  n.next = current;
                  n.prev = current.prev;
                  current.prev = n;
                  rear.next = null;
                  break;
               }
               current = current.next;
            }
         }
      }
      size++;
      return true;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
    
 public boolean remove(T element) {
      Node t = front;

      if ((element == null) || (isEmpty())) {
         return false;
      }

      if (!(this.contains(element))) {
         return false;
      }

      while (t != null) {
         if (t.element.equals(element)) {
         //remove element from front
            if (t.equals(front)) {
               front = front.next;
               if (front == null) {
                  rear = null;
               }
               else if (front != null) {
                  front.prev = null;
               }
            }
            //remove element from end
            else if (t.equals(rear)) {
               rear = rear.prev;
               rear.next = null;
            }
            //remove any other element
            else {
               t.prev.next = t.next;
               t.next.prev = t.prev;
            }
            size--;
            return true;
         }
         t = t.next;
      }
      return false;
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
    
   public boolean contains(T element) {
      Node current = front;

      if (element == null) {
         return false;
      }

      if (isEmpty()) {
         return false;
      }

      while (current != null) {
         if (current.element.equals(element)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }




   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
    
   public boolean equals(Set<T> s) {
      if(size == s.size() && complement(s).size() == 0)
         return true;

      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
    
   public boolean equals(LinkedSet<T> s) {
      if (size != s.size()) {
         return false;
      }

      Iterator<T> i1 = this.descendingIterator();
      Iterator<T> i2 = s.descendingIterator();

      if (!(i1.hasNext()) && !(i2.hasNext())) {
         return true;
      }
      else if (i1.hasNext() && i2.hasNext()) {
         while (i1.next().equals(i2.next())) {
            if (!(i1.hasNext())) {
               return true;
            }
         }
      }
      return false;
   }   

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
    
   public Set<T> union(Set<T> s){
      Node current = front;
      Set<T> set1 = new LinkedSet<T>();

      Iterator<T> iterator = s.iterator();

      while (iterator.hasNext()) {
         set1.add(iterator.next());
      }

      while (current != null) {
         set1.add(current.element);
         current = current.next;
      }
      return set1;
   }



   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
    
   public Set<T> union(LinkedSet<T> s){
      LinkedSet<T> lset1 = new LinkedSet<T>();
      lset1.front = front;
      lset1.rear = rear;
      lset1.size = size;
      LinkedSet<T> lset2 = new LinkedSet<T>();
      lset2.front = s.front;
      lset2.rear = s.rear;
      lset2.size = s.size;
      LinkedSet<T> lset3 = new LinkedSet<T>();
      Node current1 = front;
      Node current2 = s.front;
      Node current3 = lset3.front;

      if (current1 == null) {
         return lset2;
      }
      else if (current2 == null) {
         return lset1;
      }

      if ((current1 != null) && (current2 != null)) {
         if ((current1.element.compareTo(current2.element)) < 0) {
            lset3.front = new Node(current1.element);
            lset3.rear = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) > 0) {
            lset3.front = new Node(current2.element);
            lset3.rear = new Node(current2.element);
            current2 = current2.next;
         }
         else if ((current1.element.compareTo(current2.element)) == 0) {
            lset3.front = new Node(current1.element);
            lset3.rear = new Node(current1.element);
            current1 = current1.next;
            current2 = current2.next;
         }
         current3 = lset3.front;
         lset3.size++;
      }

      while ((current1 != null) || (current2 != null)) {
         if (current1 == null) {
            current3.next = new Node(current2.element);
            current2 = current2.next;
         }
         else if (current2 == null) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) < 0) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) > 0) {
            current3.next = new Node(current2.element);
            current2 = current2.next;
         }
         else if ((current1.element.compareTo(current2.element)) == 0) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
            current2 = current2.next;
         }
         current3.next.prev = current3;
         current3 = current3.next;
         lset3.rear = current3;
         lset3.size++;
      }
      return lset3;
   }               


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
    
   public Set<T> intersection(Set<T> s) {
      Set<T> lset = new LinkedSet<T>();

      Iterator<T> itr = this.iterator();

      while (itr.hasNext()) {
         T current = itr.next();
         if (s.contains(current)) {
            lset.add(current);
         }
      }   
      return lset;
   } 
     
   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
    
   public Set<T> intersection(LinkedSet<T> s) {
      Set<T> lset1 = this.union(s);
      Set<T> lset2 = this.complement(s);
      Set<T> lset3 = s.complement(this);
      Set<T> lset4 = lset2.union(lset3);
      Set<T> lset5 = lset1.complement(lset4);
      return lset5;
   }

   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      Set<T> lset = new LinkedSet<T>();

      Iterator<T> iterator1 = this.iterator();

      while (iterator1.hasNext()) {
         lset.add(iterator1.next());
      }   

      Iterator<T> iterator2 = s.iterator();

      while (iterator2.hasNext()) {
         lset.remove(iterator2.next());
      }
      return lset;
   }




   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      LinkedSet<T> lset1 = new LinkedSet<T>();

      if (front == null) {
         return lset1;
      }

      lset1.front = front;
      lset1.rear = rear;
      lset1.size = size;
      
      LinkedSet<T> lset2 = new LinkedSet<T>();
      
      lset2.front = s.front;
      lset2.rear = s.rear;
      lset2.size = s.size;
      
      LinkedSet<T> lset3 = new LinkedSet<T>();
      
      lset3.front = new Node(front.element);
      lset3.rear = lset3.front;
      lset3.size++;
      
      Node current3 = lset3.front;
      Node current1 = front.next;
      Node current2 = s.front;

      while (current1 != null) {
         current3.next = new Node(current1.element);
         current3.next.prev = current3;
         current3 = current3.next;
         
         lset3.rear = current3;
         current1 = current1.next;
         lset3.size++;
      }

      if (current2 == null) {
         return lset3;
      }

      current3 = lset3.front;

      while ((current2 != null) && (current3 != null)) {
         if ((current2.element.compareTo(current3.element)) < 0) {
            current2 = current2.next;
         }
         
         else if ((current2.element.compareTo(current3.element)) > 0) {
            current3 = current3.next;
         
         }
         else if ((current2.element.compareTo(current3.element)) == 0) {
            if ((current3.equals(lset3.rear)) && (current3.equals(lset3.front))) {
               lset3.front = null;
               lset3.rear = null;
               lset3.size--;
               return lset3;
            }
            else if (current3.equals(lset3.rear)) {
               current3.prev.next = null;
               lset3.size--;
               return lset3;
            }
            else if (current3.equals(lset3.front)) {
               lset3.front = lset3.front.next;
               if (lset3.front == null) {
                  lset3.rear = null;
               }
               else if (lset3.front != null) {
                  lset3.front.prev = null;
               }
               current3 = current3.next;
               current2 = current2.next;
               lset3.size--;
            }
            else {
               current3.next.prev = current3.prev;
               current3.prev.next = current3.next;
               current3 = current3.next;
               lset3.size--;
            }
         }
      }
      return lset3;
   }



   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      return new AscendingIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new DescendingIterator(rear);
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new PowerSetIterator(rear,size);
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////


   private class AscendingIterator implements Iterator<T> {
      private Node current = front;

      public boolean hasNext() {
         return (current != null);
      }

      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T result = current.element;
         current = current.next;
         return result;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }



   private class DescendingIterator implements Iterator<T> {
      private Node current;

      public DescendingIterator(Node n) {
         current = n;
      }

      public boolean hasNext() {
         return ((current != null) && (current.element != null));
      }

      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         if (current != null) {
            T result = current.element;
            current = current.prev;
            return result;
         }
         return null;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   private class PowerSetIterator implements Iterator<Set<T>> {
      private Node current;
      private int siz;
      private int count;

      public PowerSetIterator(Node rear,int size) {
         current = rear;
         siz = size;
         count = 0;
      }

      public boolean hasNext() {
         if (count == 0) {
            return true;
         }
         return ((count < (int) Math.pow(2,siz)) && (current != null));
      }

      public Set<T> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Set<T> result = new LinkedSet<T>();
         if (count == 0) {
            count++;
            return result;
         }
         String binary = Integer.toBinaryString(count);
         for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
               result.add(current.element);
            }
            current = current.prev;
         }
         count++;
         current = rear;
         return result;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
      ////////////////////
      // Nested classes //
      ////////////////////

      //////////////////////////////////////////////
      // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
      //////////////////////////////////////////////

      /**
      * Defines a node class for a doubly-linked list.
      */
   class Node {
         /** the value stored in this node. */
      T element;
         /** a reference to the node after this node. */
      Node next;
         /** a reference to the node before this node. */
      Node prev;

         /**
         * Instantiate an empty node.
         */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }

         /**
         * Instantiate a node that containts element
         * and with no node before or after it.
         */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}