package cn.xqhuang.dps.algorithms;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class Solution {

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

        // System.out.println(mergeSimilarItems(new int[]{7,4,8,9,7,7,5}));
    }

    public static List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < items2.length; i++) {
            map.put(items2[i][0], items2[i][1]);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < items1.length; i++) {
            List<Integer>  temp = new ArrayList<>();
            int value = items1[i][0];
            int weight = items1[i][1];
            temp.add(value);
            temp.add(map.getOrDefault(value, 0) + weight);

            map.remove(value);
            res.add(temp);
        }
        if (map.size() > 0) {
            for(Integer key : map.keySet()) {
                List<Integer>  temp = new ArrayList<>();
                temp.add(key);
                temp.add(map.get(key));

                res.add(temp);
            }
        }

        Collections.sort(res, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        });

        return res;
    }
}
