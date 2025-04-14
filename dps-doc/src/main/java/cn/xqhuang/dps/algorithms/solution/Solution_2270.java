package cn.xqhuang.dps.algorithms.solution;

/**
 *
 给你一个下标从 0 开始长度为 n 的整数数组 nums 。
 如果以下描述为真，那么 nums 在下标 i 处有一个 合法的分割 ：

 前 i + 1 个元素的和 大于等于 剩下的 n - i - 1 个元素的和。
 下标 i 的右边 至少有一个 元素，也就是说下标 i 满足 0 <= i < n - 1 。
 请你返回 nums 中的 合法分割 方案数。
 0  1  2   3   4
       2

 */
public class Solution_2270 {

    public int waysToSplitArray(int[] nums) {

        int[] lr = new int[nums.length];
        // int[] rl = new int[nums.length];

        lr[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            lr[i] = nums[i] + lr[i - 1];
        }

        int sum =  lr[nums.length - 2] + nums[nums.length - 1];

        int res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (lr[i] >= sum - lr[i]) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution_2270 solution = new Solution_2270();
        System.out.println(solution.waysToSplitArray(new int[]{2,3,1,0}));
    }
}
