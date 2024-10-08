import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Ethan Chapman (cec0161@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  08/28/22
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
     // throws correct exception if a is null or length is 0
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0]; // sets min to first number
      for (int i = 0; i < a.length; i++) {
         if (a[i] < min) { 
            min = a[i]; // changes min to lowest number, evaluating the array
         }
      }
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
     // throws correct exception if a is null or length is 0      
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0]; //sets max to first number in the array
      for (int i = 0; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i]; // changes max by comparing next element through array
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int sameValues = 0; //calculate number of equal values
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] == a[i+1]) {
            sameValues++;
         }
      }
      if (k > a.length - sameValues) {
         throw new IllegalArgumentException();
      }
      
      int[] b = Arrays.copyOf(a, a.length); //temp array to not change og
      
      Arrays.sort(b); //sort by ascending order
    
      for (int i = 0; i < k - 1; i++) {
         if (b[i] == b[i+1]) { //skip same values until k is reached
            k++;
         }
      }
      
      int kmin = b[k - 1];
      return kmin;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
   
      if (a == null) {
         throw new IllegalArgumentException();
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      
      int sameValues = 0;
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] == a[i+1]) {
            sameValues++;
         }
      }
      
      if (k > a.length - sameValues) {
         throw new IllegalArgumentException();
      }
      
      int[] b = Arrays.copyOf(a, a.length); //temporary array to not change og
      
      for (int i = 0; i < b.length; i++) {
         b[i] = b[i] * -1;
      }
      Arrays.sort(b); // sort by descending order
      for (int i = 0; i < b.length; i++) {
         b[i] = b[i] * -1;
      }
      
      for (int i = 0; i < k - 1; i++) {
         if (b[i] == b[i+1]) { //skip same values until k is reached
            k++;
         }
      }
      
      int kmax = b[k - 1];
      return kmax;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
      // throws correct exception if a is null or length is 0
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int[] rangeArray = new int[0]; //array to be changed/returned
      int j = 0; // position in array
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= high && a[i] >= low) { //evaluation of range
            rangeArray = Arrays.copyOf(rangeArray, rangeArray.length + 1);
            rangeArray[j] = a[i];
            j++;
         }
      }
      return rangeArray;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
      if (a == null) { //if array is null, throw exception
         throw new IllegalArgumentException();
      }
      if (a.length == 0) { //if array is 0 length, throw exception
         throw new IllegalArgumentException();
      }
      boolean notFound = true;
      int ceiling = Selector.max(a); //makes sure for loop will narrow in
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key && a[i] <= ceiling) {
            ceiling = a[i];
            notFound = false; //returns exception only when if is true
         }
      }
      if (notFound) { //if ceiling is not found, throw exception
         throw new IllegalArgumentException();
      }
      
      return ceiling;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      if (a == null) { //if array is null, throw exception
         throw new IllegalArgumentException();
      }
      if (a.length == 0) { //if array is 0 length, throw exception
         throw new IllegalArgumentException();
      }
      boolean notFound = true;
      int floor = Selector.min(a); //makes sure for loop will narrow in
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key && a[i] >= floor) {
            floor = a[i];
            notFound = false; //returns exception only when if is true
         }
      }
      if (notFound) { //if ceiling is not found, throw exception
         throw new IllegalArgumentException();
      }
      
      return floor;
   }

}