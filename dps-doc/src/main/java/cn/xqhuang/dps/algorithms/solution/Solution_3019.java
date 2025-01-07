package cn.xqhuang.dps.algorithms.solution;

public class Solution_3019 {

    public static int countKeyChanges(String s) {
        int length = s.length();

        int res = 0;
        for (int i = 1; i < length; i++) {
            if (s.charAt(i) != s.charAt(i - 1) || Math.abs(s.charAt(i) - s.charAt(i + 1)) != 32) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(countKeyChanges("aAbBcC"));
    }
}
