package cn.xqhuang.dps.algorithms.solution;

import java.util.ArrayList;
import java.util.List;

public class Solution_3159 {

    public static int[] occurrencesOfElement(int[] nums, int[] queries, int x) {
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == x) {
                index.add(i);
            }
        }

        int[] res = new int[nums.length];
        for (int i = 0; i < queries.length; i++) {
            int query = queries[i];
            if (query <= index.size()) {
                res[i] = index.get(query - 1);
            } else {
                res[i] = -1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        occurrencesOfElement(new int[]{1,3,1,7}, new int[]{1,3,2,4}, 1);
    }
}
