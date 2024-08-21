/**
 * Implements shift-right behavior in an array.
 *
 */

public class ShiftRight {


   /**
    * Shifts the elements at a[index] through a[a.length - 2] one
    * position to the right. 
    */
   public static void shiftRight(Object[] array, int index) {
   
      // FILL IN THE BODY OF THIS METHOD
   
   
   
   
   
   
      int lth = array.length;   // length of the given array
      Object last = array[lth - 1]; // to store the last element
      for (int i = lth - 1; i > index; i--) {    // right shift elements of the array till the given index
         array[i] = array[i-1];  // replace previous one with current element
      }
      array[index] = last;    // at last place the last element at the given position
   }

}