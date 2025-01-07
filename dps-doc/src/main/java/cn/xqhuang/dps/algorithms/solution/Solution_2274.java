package cn.xqhuang.dps.algorithms.solution;

import java.util.Arrays;

public class Solution_2274 {

    public static int maxConsecutive(int bottom, int top, int[] special) {
        int res = Integer.MIN_VALUE;

        Arrays.sort(special);

        int length = special.length;
        res = Math.max(res, special[0] - bottom);
        res = Math.max(res, top - special[length - 1]);

        int index = special[0];
        for (int i = 1; i < special.length; i++) {
            res = Math.max(res, special[i] - index - 1);
            index = special[i];
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(maxConsecutive(6, 8, new int[]{7, 6, 8}));
    }
}

