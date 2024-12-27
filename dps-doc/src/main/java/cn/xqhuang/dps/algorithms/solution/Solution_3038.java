package cn.xqhuang.dps.algorithms.solution;

import java.util.ArrayList;
import java.util.List;

public class Solution_3038 {

    public boolean isSubstringPresent(String s) {
        int length = s.length();

        List<String> list = new ArrayList<>();

        for (int i = 0; i < length - 1; i++) {
            list.add(s.substring(i, i + 2));
        }
        StringBuffer sb = new StringBuffer();
        for (int i = length - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        for (int i = 0; i < list.size(); i++) {
            if (sb.toString().contains(list.get(i))) {
                return true;
            }
        }
        return false;
    }
}
