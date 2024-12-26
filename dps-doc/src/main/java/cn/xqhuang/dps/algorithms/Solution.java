package cn.xqhuang.dps.algorithms;


import java.io.IOException;
import java.util.*;

public class Solution {

    /**
     * 在一个无限的 x 坐标轴上，有许多水果分布在其中某些位置。
     * 给你一个二维整数数组 fruits ，其中 fruits[i] = [positioni, amounti]
     * 表示共有 amounti 个水果放置在 positioni 上。fruits 已经按 positioni 升序排列 ，
     * 每个 positioni 互不相同 。
     * 另给你两个整数 startPos 和 k 。最初，你位于 startPos 。
     * 从任何位置，你可以选择 向左或者向右 走。在 x 轴上每移动 一个单位 ，
     * 就记作 一步 。你总共可以走 最多 k 步。
     * 你每达到一个位置，都会摘掉全部的水果，水果也将从该位置消失（不会再生）。
     * 返回你可以摘到水果的 最大总数 。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // System.out.println(balancedString("WWEQERQWQWWRWWERQWEQ"));
        /*try (Scanner scanner = new Scanner(System.in)) {
            String v1 = scanner.nextLine();
            String v2 = scanner.nextLine();
            String res = solution(v1, v2);
            System.out.println(res);
        }*/
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // String str = br.readLine();
        //int i = knightDialer(5000);
        System.out.println(eatenApples(new int[]{1,2,3,5,2}, new int[]{3,2,1,4,2}));
        System.out.println();

    }
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {

        return 0;
    }

    public static int minDeletion(int[] nums) {
        int n = nums.length;

        int index = 0;

        int res = 0;
        while (index < n - 1) {
            if (index % 2 == 0 && nums[index] == nums[index + 1]) {
                res++;
            }

            index++;
        }
        if ((n - res) % 2 != 0) {
            return res + 1;
        }

        return res;
    }

    /**
     *给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
     *
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     *
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
     * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
     *
     *
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] res = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    res[i + 1][j + 1] = res[i][j] + 1;
                } else {
                    res[i + 1][j + 1] = Math.max(res[i][j + 1], res[i][j + 1]);
                }
            }
        }
        return res[m][n];
    }

    /*
        给定一个 8 x 8 的棋盘，只有一个 白色的车，用字符 'R' 表示。
        棋盘上还可能存在白色的象 'B' 以及黑色的卒 'p'。空方块用字符 '.' 表示。

        车可以按水平或竖直方向（上，下，左，右）移动任意个方格直到它遇到另一个棋子或棋盘的边界。如果它能够在一次移动中移动到棋子的方格，则能够 吃掉 棋子。

        注意：车不能穿过其它棋子，比如象和卒。这意味着如果有其它棋子挡住了路径，车就不能够吃掉棋子。

        返回白车将能 吃掉 的 卒的数量。
     */
    public static int numRookCaptures(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        int res = 0;

        int r = 0;
        int c = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'R') {
                   r = i;
                   c = j;
                }
            }
        }

        for (int x = r - 1; x >= 0; x--) {
            if (board[x][c] == 'B') {
                break;
            }
            if (board[x][c] == 'p') {
                res++;
            }
        }

        for (int i = r + 1; i < m; i++) {
            if (board[i][c] == 'B') {
                break;
            }
            if (board[i][c] == 'p') {
                res++;
            }
        }

        for (int j = c - 1; j >= 0; j++) {
            if (board[r][j] == 'B') {
                break;
            }
            if (board[r][j] == 'p') {
                res++;
            }
        }

        for (int j = c + 1; j < n; j++) {
            if (board[r][j] == 'B') {
                break;
            }
            if (board[r][j] == 'p') {
                res++;
            }
        }


        return res;
    }

    public static boolean squareIsWhite(String coordinates) {
        int i = coordinates.charAt(0);
        int j = coordinates.charAt(1);
        if (i % 2 != 0 && j% 2 != 0) {
            return false;
        }

        if (i % 2 == 0 && j% 2 == 0) {
            return false;
        }
        return true;
    }
    /**
     * 1  -  10
     * 2  -  20
     * 3  -
     * 0  1  2  3  4  5  6  7  8  9
     * -----------------------------
     * 1  1  1  1  1  1  1  1  1  1
     * 2  2  2  2  3  0  3  2  2  2
     * 6  5  4  5  6  0  6  5  4  5
     */
    public static int knightDialer(int n) {
        Map<Integer, List<Integer>> map = new HashMap<>(16);
        map.put(0, Arrays.asList(4, 6));
        map.put(1, Arrays.asList(8, 6));
        map.put(2, Arrays.asList(7, 9));
        map.put(3, Arrays.asList(4, 8));
        map.put(4, Arrays.asList(0, 3, 9));
        map.put(5, Collections.emptyList());
        map.put(6, Arrays.asList(0, 1, 7));
        map.put(7, Arrays.asList(2, 4));
        map.put(8, Arrays.asList(1, 3));
        map.put(9, Arrays.asList(4, 2));

        double[][] res = new double[n + 1][10];
        for (int i = 0; i < 10; i++) {
            res[1][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < 10; j++) {
                List<Integer> integers = map.get(j);
                double sum = 0;
                for (Integer integer : integers) {
                    sum += res[i - 1][integer];
                }
                res[i][j] += (int) (sum % (Math.pow(10L, 9L) + 7));
            }
        }

        double max = 0;
        for (int i = 0; i < 10; i++) {
            max += res[n][i];
        }
        return (int) (max % (Math.pow(10L, 9L) + 7));
    }

    /**
     *
    给你一个下标从 0 开始、长度为 n 的整数排列 nums 。

    如果排列的第一个数字等于 1 且最后一个数字等于 n ，则称其为 半有序排列 。你可以执行多次下述操作，直到将 nums 变成一个 半有序排列 ：

    选择 nums 中相邻的两个元素，然后交换它们。
    返回使 nums 变成 半有序排列 所需的最小操作次数。

    排列 是一个长度为 n 的整数序列，其中包含从 1 到 n 的每个数字恰好一次。
     */
    public static int semiOrderedPermutation(int[] nums) {
        int res = 0;
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] == 1) {
                int temp = nums[i - 1];
                nums[i - 1] = 1;
                nums[i] = temp;

                res++;
            }
        }

        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] == nums.length) {
                int temp = nums[i + 1];
                nums[i + 1] = nums.length;
                nums[i] = temp;
                res++;
            }
        }
        return res;
    }
    /**
     *
     给你一个整数数组 nums ，一个整数 k  和一个整数 multiplier 。

     你需要对 nums 执行 k 次操作，每次操作中：

     找到 nums 中的 最小 值 x ，如果存在多个最小值，选择最 前面 的一个。
     将 x 替换为 x * multiplier 。
     请你返回执行完 k 次乘运算之后，最终的 nums 数组。
     */
    public static int[] getFinalState(int[] nums, int k, int multiplier) {
        int length = nums.length;
        for (int i = 0; i < k; i++) {
            int index = 0;
            for (int j = 1; j < length; j++) {
                if (nums[index] > nums[j]) {
                    index = j;
                }
            }

            nums[index] *= multiplier;
        }

        return nums;
    }

    public static int eatenApples(int[] apples, int[] days) {
        int length = apples.length;

        int[] res = new int[40000];

        for (int i = 0; i < length; i++) {
            if (apples[i] == 0) {
                continue;
            }
            int app = apples[i];
            for (int j = i; j <= days[i]; j++) {
                if (app < 1) {
                    break;
                }
                if (res[j] == 0) {
                    res[j] = 1;
                    app--;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += res[i];
        }

        return sum;
    }
}
