package cn.xqhuang.dps.algorithms.solution;

/**
 *
 * 3065. 超过阈值的最少操作数 I
 * 简单
 * 相关标签
 * 相关企业
 * 提示
 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
 *
 * 一次操作中，你可以删除 nums 中的最小元素。
 *
 * 你需要使数组中的所有元素都大于或等于 k ，请你返回需要的 最少 操作次数。
 *
 */
public class Solution_3065 {

    public int minOperations(int[] nums, int k) {
        int res = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < k) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
