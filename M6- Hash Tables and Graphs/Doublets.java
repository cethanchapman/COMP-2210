import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Your Name (you@auburn.edu)
 */

public class Doublets implements WordLadderGame {

// The word list used to validate words.
    // Must be instantiated and populated in the constructor.
    /////////////////////////////////////////////////////////////////////////////
    // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
    // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
    // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
    // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
    // table with chaining).
    /////////////////////////////////////////////////////////////////////////////
   List<String> EMPTY_LADDER = new ArrayList<>();


   TreeSet<String> lexicon;

   int wordCount;


     /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
   public Doublets(InputStream in) {
      try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
      
         wordCount = 0;
      
         while (s.hasNext()) {
            String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////         
            lexicon.add(str.toLowerCase());
            wordCount++;
         
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////

   public int getHammingDistance(String str1, String str2) {
   
      int result = 0;
   
      if (str1.length() != str2.length()) {
         return -1;
      }
   
      char[] string1 = str1.toCharArray();
      char[] string2 = str2.toCharArray();
   
      for (int i = 0; i < str1.length(); i++) {
         if (string1[i] != string2[i]) {
            result++;
         }
      }
   
      return result;
   }

   public List<String> getMinLadder(String start, String stop) {
   
      return null;
   }

   public List<String> getNeighbors(String word) {
   
      ArrayList result = new ArrayList();
   
   
   //type safety issue
      for (String s: lexicon) {
         if (getHammingDistance(word, s) == 1) {
            result.add(s);
         }
      }
   
      if (result.isEmpty()) {
         return EMPTY_LADDER;
      }
   
      return result;
   }

   public int getWordCount() {
   
      return lexicon.size();
   }

   public boolean isWord(String str) {
   
      if (lexicon.contains(str)) {
         return true;
      }
   
      return false;
   }

   public boolean isWordLadder(List<String> sequence) {
   
      if (sequence.isEmpty()) {
         return false;
      }
   
      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;
         }
      }
   
      for (int i = 0; i < sequence.size(); i++) {
         if (!lexicon.contains(sequence.get(i))) {
            return false;
         }
      }
   
      return true;
   }

   private class Node {
      String word;
      int steps;
   
      public Node(String word, int steps){
         this.word = word;
         this.steps = steps;
      }
   }

}