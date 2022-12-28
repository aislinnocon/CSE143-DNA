// Aislinn O'Connell
// CS 143
// HW Core Topics: Sudoku HW #3
//
// This program will read in a file containing the filled in numbers
// and blank spaces for a sudoku game. It will convert this file into
// a multidimensional array allowing it to be editted later on. The 
// program will be able to print out the filled in multidimensional array.
// The program will read through the imported sudoku boards and make sure
// that the imported board does not break any rules of a sudoku board. Then
// it will check whether the sudoku board has already been solved or not. 
// It can also solve the board using recursion and will make sure the board 
// is valid and hasn't been already solved. 

import java.io.*;
import java.util.*;

public class SudokuBoard {
   private int[][] board; 
   
   // Declares the array size & calls upon the method to read the file 
   public SudokuBoard(String fileName) throws FileNotFoundException {
      board = new int[9][9];
      readBoard(fileName); 
   
   } 
   
   // Reads the imported file and fills in the array 
   private void readBoard(String fileName) throws FileNotFoundException {
      Scanner input = new Scanner(new File(fileName));         
      for (int i = 0; i < 9; i++) {
         String token = input.nextLine();
         for (int j = 0; j < 9; j++) {
            String value = token.substring(j, j + 1); 
            if (value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5") 
            || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9")) { 
               this.board[i][j] = (Integer.parseInt(value));
            } else if (value.equals(".")) {
               this.board[i][j] = 0;
            } else { 
               this.board[i][j] = -1; 
            }
         }
      } 
   } 
   
   // Checks if the sudoku board is valid
   public boolean isValid() {
      boolean boardValid = true;  
      if (dataCheck() == false) { 
          boardValid = false;
      } 
      if (rowCheck() == false) {
         boardValid = false;
      } 
      if (columnCheck() == false) {
         boardValid = false;
      } 
      if (squaresCheck() == false) { 
         boardValid = false;
      }
      return boardValid;
   }
   
   // Private Helper: Checks that there is no "bad" data 
   private boolean dataCheck() {
      boolean dataCorrect = true;
      Set<Integer> dataList = new HashSet<>();
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            dataList.add(board[i][j]);
         }
      } 
      if (dataList.contains(-1)) {
         dataCorrect = false; 
      } 
      return dataCorrect;
   }
   
   // Private Helper: Checks if the rows are correct
   private boolean rowCheck() {
      boolean rowCorrect = true;
      for (int i = 0; i < 9; i++) {
         Set<Integer> rowChecker = new HashSet<Integer>();
         for (int j = 0; j < 9; j++) {
            if (board[i][j] != 0) {
               if (rowChecker.contains(board[i][j])) {
                  rowCorrect = false;
               } else {
                  rowChecker.add(board[i][j]);
                
               } 
            }
         }
      }
      return rowCorrect; 
   }  
   
   // Private Helper: Checks if columns are correct
   private boolean columnCheck() {
      boolean colCorrect = true;
      for (int i = 0; i < 9; i++) {
         Set<Integer> colChecker = new HashSet<Integer>();
         for (int j = 0; j < 9; j++) {
             if (board[j][i] != 0) {
               if (colChecker.contains(board[j][i])) {
                  colCorrect = false;
               } else {
                  colChecker.add(board[j][i]);
               }
            }
         }
      }
      return colCorrect; 
   }      
   
   // Private Helper: Checks if the mini square are correct
   private boolean squaresCheck() { 
      boolean squaresCorrect = true;
      for(int i = 1; i <= 9; i++) {
         Set<Integer> miniSquareChecker = new HashSet<Integer>();
         int[][] square = miniSquare(i);
         for(int j = 0; j < 3; j++) {
            for(int k = 0; k < 3; k++) {
               if (square[j][k] != 0) {
                  if (miniSquareChecker.contains(square[j][k])) {
                     squaresCorrect = false;
                  } else {
                     miniSquareChecker.add(square[j][k]);
                  }
               }
            }
         }
      }
      return squaresCorrect;
   }
         
   // Private Helper: Calls upon mini 3x3 squares from the sudoku board
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   // Checks if the board has already been solved
   public boolean isSolved() {
      boolean boardSolved = true;
      Map<Integer, Integer> valueOccurance = new HashMap<>();
      for (int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            if (valueOccurance.containsKey(board[i][j])) {
               int count = valueOccurance.get(board[i][j]);
               valueOccurance.put(board[i][j], count + 1);
            } else {
               valueOccurance.put(board[i][j], 1);
            }
         }
      } 
      for (int k = 1; k <= 9; k++) {
         Set<Integer> keys = valueOccurance.keySet(); 
         if (!keys.contains(k)) {
            boardSolved = false; 
         }
         if (valueOccurance.get(k) == null || valueOccurance.get(k) != 9) {
            boardSolved = false;
         }
      }             
      return boardSolved;
   }
   
   // Solves Sudoku board
   public boolean solve() {
      boolean boardSolved = false; 
      if (!isValid()) {
         return false;
      } else if (isSolved()) {
            return true;
      } else {
         for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
               if(board[row][col] == 0) {
                  // Found an empty row start trying all digits
                  for (int i = 1; i<=9 && !boardSolved; i++) {
                     board[row][col] = i;
                     boardSolved = solve();
                  }
                  if (!boardSolved) {
                     board[row][col] = 0; 
                  }
               }
            }
         }
      }
      return boardSolved;
   } 
   
   // Prints out the array
   public String toString() {
      String rows = "";
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            rows = rows + this.board[i][j];
         }
         rows = rows + "\n";
      }
      return rows;
   } 

} 






 