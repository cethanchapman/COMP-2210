/**
 * Count the number of odd values in an array.
 *
 */
public class CountOdds {

    //  C O M P L E T E   T H I S    M E T H O D 

    /**
     * Returns the number of odd values in the paramter.
     */
    public static int countOdds(int[] nums) {
        int count = 0;
        
        for(int i = 0; i<nums.length; i++) {
            if (nums [i] % 2 == 1) {
                count++;
            }
        }
        
        
        
        return count;
   }

}

