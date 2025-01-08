package cn.xqhuang.dps.algorithms.solution;

public class Solution_2264 {

    public static String largestGoodInteger(String num) {
        int length = num.length();

        String res = "";

        int index = 0;
        for (int i = 1; i < length; i++) {
            if (num.charAt(i) == num.charAt(i - 1)) {
                index++;
            } else {
                index = 0;
            }
            if (index == 2) {
                String substring = num.substring(i - 2, i + 1);
                if (res.isEmpty()) {
                    res = substring;
                } else {
                    res = res.charAt(0) > substring.charAt(0) ? res : substring;
                }

                index = 0;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        largestGoodInteger("1221000");
    }
}
