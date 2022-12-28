// Aislinn O'Connell
// CS 143
// HW Core Topics: Sudoku HW #3
//
// This program will read in a file and check if the sudoku board isn't valid
// and if it is already solved. If both those conditions aren't met, then the 
// it will go ahead and solve the board and record how long it took to solve. 

import java.util.*;
import java.io.*;

public class SudokuSolverEngine {

   public static void main(String[] args) throws FileNotFoundException {
      // Here I have called my class `MySudokuBoard` if you named your class
      // differently, modify the line below to use your own class name
      SudokuBoard board = new SudokuBoard("boards/fast-solve.sdk");
      if (!board.isValid()) {
         System.out.println("This Sudoku board cannot be solved"); 
      } else if(board.isSolved()) {
         System.out.println("This Sudoku board is already solved");
      } else {
         System.out.println("Initial board");
         System.out.println(board);
         System.out.println();

         System.out.print("Solving board...");
         long start = System.currentTimeMillis();
         board.solve();
         long stop = System.currentTimeMillis();
         System.out.printf("SOLVED in %.3f seconds.\n", ((stop-start)/1000.0));
         System.out.println();
         System.out.println(board);
      }      
   }
}

/* 
Output: valid-complete.sdk 
  ----jGRASP exec: java -ea SudokuSolverEngine
 This Sudoku board is already solved

 Output: fast-solve.sdk
      ----jGRASP exec: java -ea SudokuSolverEngine
 Initial board
 827154396
 965027148
 341609752
 000000000
 000000000
 618970435
 786235014
 154796803
 239840000
 
 
 Solving board...SOLVED in 14.502 seconds.
 
 827154396
 965327148
 341689752
 472513689
 593468271
 618972435
 786235914
 154796823
 239841567
 
 Output: very-fast-solve.sdk
      ----jGRASP exec: java -ea SudokuSolverEngine
 Initial board
 034678912
 072195348
 198342567
 009061423
 026853791
 013924056
 061537284
 080419635
 345086179
 
 
 Solving board...SOLVED in 3.253 seconds.
 
 534678912
 672195348
 198342567
 859761423
 426853791
 713924856
 961537284
 287419635
 345286179
 
 
Output: dirty-data.sdk
  ----jGRASP exec: java -ea SudokuSolverEngine
 This Sudoku board cannot be solved


Output: grid-violations.sdk
  ----jGRASP exec: java -ea SudokuSolverEngine
 This Sudoku board cannot be solved



*/