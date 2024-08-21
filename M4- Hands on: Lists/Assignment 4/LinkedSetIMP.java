import java.util.*;

public class LinkedSetIMP{

   public static void main(String []args){
   
      LinkedSet<Integer> set1 = new LinkedSet<Integer>();
   
      set1.add(99);
   
      System.out.println("Linked set " + set1);
   
      set1.add(1);
   
      set1.add(2);
   
      set1.add(3);
   
      Iterator<Integer> i = set1.iterator();
   
      System.out.print("Linked Set elements");
   
      while (i.hasNext()) {
      
         System.out.print(i.next() + " ");
      
      }
   
   }

}