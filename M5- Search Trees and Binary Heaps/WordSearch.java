import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;


import java.util.Arrays;


import java.util.Deque;
import java.util.Iterator;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/* Implementation of the WordSearchGame interface.
   @author - Ethan Chapman -  cec0161@auburn.edu
   @version - 10/31/22
 
 */
 
public class WordSearch implements WordSearchGame {
   //Fields
   private TreeSet<String> lexicon;
   private String[][] board;
   private static final int MAX_NEIGHBORS = 8;
   private int width;
   private int height;
   private boolean[][] visited;
   private ArrayList<Integer> p1;
   private String partWord;
   private SortedSet<String> allWords;
   private ArrayList<Position> p2;
   
   /** Constructor. **/
   public WordSearch() {
      lexicon = null;
      
      
      board = new String[4][4];
      board[0][0] = "E"; 
      board[0][1] = "E"; 
      board[0][2] = "C"; 
      board[0][3] = "A"; 
      board[1][0] = "A"; 
      board[1][1] = "L"; 
      board[1][2] = "E"; 
      board[1][3] = "P"; 
      board[2][0] = "H"; 
      board[2][1] = "N"; 
      board[2][2] = "B"; 
      board[2][3] = "O"; 
      board[3][0] = "Q"; 
      board[3][1] = "T"; 
      board[3][2] = "T"; 
      board[3][3] = "Y";  
        
      width = board.length;
      height = board[0].length;
   }
       
    /**
    * Loads the lexicon into a data structure for later use.
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
    
   public void loadLexicon(String fileName) {
   
      lexicon = new TreeSet<String>(); 
   
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      
      try {
         Scanner scan = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (scan.hasNext()) {
            String word = scan.next();
            word = word.toUpperCase();
            lexicon.add(word);
            scan.nextLine();
         }
      }
      
      catch (java.io.FileNotFoundException e) {
         throw new IllegalArgumentException();
      } 
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
    
   public void setBoard(String[] letterArray) {
    
      if (letterArray == null) {
         throw new IllegalArgumentException();
      
      }
      
      int n = (int) Math.sqrt(letterArray.length);
      
      if (n * n != letterArray.length) {
         throw new IllegalArgumentException();
      
      }
      
      board = new String[n][n];
      width = n;
      height = n;
      int index = 0;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            board[i][j] = letterArray[index];
            index++;
         }
      }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
    
   public String getBoard() {
   
      String boardString = "";
      
      for (int i = 0; i < height; i++) {
         boardString += "\n";
         
         for (int j = 0; j < width; j++) {
            boardString += board[i][j] + " ";
         }
      }
      
      return boardString;
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllScorableWords(int minWordLength) {
   
      if (minWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      p2 = new ArrayList<Position>();
      allWords = new TreeSet<String>();
      partWord = "";
      
      for (int i = 0; i < height; i++) {
      
         for (int j = 0; j < width; j ++) {
            partWord = board[i][j];
            
            if (isValidWord(partWord) && partWord.length() >= minWordLength) {
               allWords.add(partWord);
            }
            
            if (isValidPrefix(partWord)) {
               Position t = new Position(i,j);
               p2.add(t);
               dfs2(i, j, minWordLength); 
               p2.remove(t);
            }
         }
      }
      
      return allWords;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
   
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      int score = 0;
      Iterator<String> itr = words.iterator();
      
      while (itr.hasNext()) {
         String word = itr.next();
         
         if (word.length() >= minimumWordLength && isValidWord(word)
             && !isOnBoard(word).isEmpty()) {
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      
      return score;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
    
   public boolean isValidWord(String wordToCheck) {
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      return lexicon.contains(wordToCheck);
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
    
   public boolean isValidPrefix(String prefixToCheck) {
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefixToCheck);
      
      if (word != null) {
         return word.startsWith(prefixToCheck);
      }
      
      return false;
   }
      
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
    
   public List<Integer> isOnBoard(String wordToCheck) {
   
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      p2 = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      partWord = "";
      p1 = new ArrayList<Integer>();
      
      for (int i = 0; i < height; i++) {
      
         for (int j = 0; j < width; j ++) {
         
            if (wordToCheck.equals(board[i][j])) {
               p1.add(i * width + j);
               return p1;
            }
            
            if (wordToCheck.startsWith(board[i][j])) {
               Position pos = new Position(i, j);
               p2.add(pos);
               partWord = board[i][j];
               dfs(i, j, wordToCheck);
               
               if (!wordToCheck.equals(partWord)) {
                  p2.remove(pos);
               }
               else {
                  for (Position p: p2) {
                     p1.add((p.x * width) + p.y);
                  } 
                  
                  return p1;
               }
            }
         }
      }
      return p1;
   }
   
     
   private void dfs(int x, int y, String wordToCheck) {
   
      Position start = new Position(x, y);
      allUnvisited();
      p1Visited();
      
      for (Position p: start.neighbors()) {
      
         if (!isVisited(p)) {
            visit(p);
            
            if (wordToCheck.startsWith(partWord + board[p.x][p.y])) {
               partWord += board[p.x][p.y];
               p2.add(p);
               dfs(p.x, p.y, wordToCheck);
               
               if (wordToCheck.equals(partWord)) {
                  return;
               }
               else {
                  p2.remove(p);
               
                  int endIndex = partWord.length() - board[p.x][p.y].length();
                  partWord = partWord.substring(0, endIndex);
               }
            }
         }
      }
      allUnvisited();
      p1Visited();
   }
   
     
   private void dfs2(int x, int y, int min) {
      Position start = new Position(x, y);
      allUnvisited();
      p1Visited();
      
      for (Position p : start.neighbors()) {
      
         if (!isVisited(p)) {
            visit(p);
            
            if (isValidPrefix(partWord + board[p.x][p.y])) {
               partWord += board[p.x][p.y];
               p2.add(p);
               
               if (isValidWord(partWord) && partWord.length() >= min) {
                  allWords.add(partWord);
               }
               
               dfs2(p.x, p.y, min);
               p2.remove(p);
               int endIndex = partWord.length() - board[p.x][p.y].length();
               partWord = partWord.substring(0, endIndex);
            }
         }
      }
      
      allUnvisited();
      p1Visited();
   }

   /**
   * Marks unvisited positions.
   */
   
   private void allUnvisited() {
      visited = new boolean[width][height];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   /**
   * Creates path.
   */
   
   private void p1Visited() {
      for (int i = 0; i < p2.size(); i ++) {
         visit(p2.get(i));
      }
   }
   
       
   private class Position {
      int x;
      int y;
   
      /** Constructor. */
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      
      public Position[] neighbors() {
      
         Position[] adjValues = new Position[MAX_NEIGHBORS];
         int count = 0;
         Position p;
         
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     adjValues[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(adjValues, count);
      }
   }

   /**
    * Checks for valid position.
    * @param p the position
    */
    
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height);
   }

   /**
    * Checks position.
    * @param p the position
    */
    
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   
    // Marks position.
    
    
   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }

}