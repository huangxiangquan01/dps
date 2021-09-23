package cn.xqhuang.dps.algorithms;


public class Solution {

    public static void main(String[] args) {
        System.out.println(isPowerOfThree(-3));
    }

    public static boolean isPowerOfThree(int n) {
        int index = 0;
        while (true) {
            double val = Math.pow(3, index);
            if (val == n) {
                return true;
            } else if (val > n) {
                break;
            }
            index++;
        }
        return  false;
    }
}
