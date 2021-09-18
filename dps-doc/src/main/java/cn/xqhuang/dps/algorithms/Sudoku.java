package cn.xqhuang.dps.algorithms;

/**
 *  LeetCode - 九宫格
 */
public class Sudoku {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'.','.','9','7','4','8','.','.','.'},
                {'7','.','.','.','.','.','.','.','.'},
                {'.','2','.','1','.','9','.','.','.'},
                {'.','.','7','.','.','.','2','4','.'},
                {'.','6','4','.','1','.','5','9','.'},
                {'.','9','8','.','.','.','3','.','.'},
                {'.','.','.','8','.','3','.','2','.'},  
                {'.','.','.','.','.','.','.','.','6'},
                {'.','.','.','2','7','5','9','.','.'}
        };
        solveSudoku(board);
    }

    //
    private static int[][] rowArr = new int[9][10];
    private static int[][] cellArr = new int[9][10];
    private static int[][][] boxArr = new int[3][3][10];

    public static void solveSudoku(char[][] board) {
        init(board);
        putVal(board, 0, 0);
    }

    //初始化已有值
    public static void init(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int val = board[i][j] - '0';
                if (board[i][j] != '.') {
                    rowArr[i][val]++;
                    cellArr[j][val]++;
                    boxArr[i / 3][ j / 3][val]++;
                }
            }
        }
    }

    private static boolean putVal(char[][] board, int row, int cell) {
        print(board);
        if (row == 9) {
            return true;
        }
        if (cell == 9) {
            return putVal(board, row + 1, 0);
        }

        if (board[row][cell] == '.') {
            for (int i = 1; i <= 9; i++) {
                char now = (char) (i + '0');
                if (check(row, cell, i)) {
                    board[row][cell] = now;
                    rowArr[row][i]++;
                    cellArr[cell][i]++;
                    boxArr[row / 3][ cell / 3][i]++;

                    if (!putVal(board, row, cell + 1)) {
                        board[row][cell] = '.';
                        rowArr[row][i]--;
                        cellArr[cell][i]--;
                        boxArr[row / 3][cell / 3][i]--;
                    } else {
                        return true;
                    }
                }
            }
            return  false;
        } else {
            return putVal(board, row, cell + 1);
        }
    }

    private static boolean check(int row, int cell, int val) {
        return rowArr[row][val] == 0 &&  cellArr[cell][val] == 0 && boxArr[row / 3][cell / 3][val] == 0;
    }

    public static void print(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }

        System.out.println(" ---------------- ");
    }
}
