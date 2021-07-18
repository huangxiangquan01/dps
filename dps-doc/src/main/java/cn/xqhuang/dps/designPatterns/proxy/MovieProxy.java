package cn.xqhuang.dps.designPatterns.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieProxy implements IMovie {
    Movie movie;
 
    public MovieProxy(Movie movie) {
        this.movie = movie;
    }
 
    @Override
    public void play() {
        advertising(true);
        movie.play();
        advertising(false);
    }
 
    private void advertising(boolean isBeforMovie){
        if(isBeforMovie){
            System.out.println("影片马上开始,素小暖入驻CSDN啦,快来关注我啊");
        }else{
            System.out.println("影片正片已经结束,马上彩蛋环节,不要离开哦,素小暖入驻CSDN啦,快来关注我啊");
        }
    }
 
    public static void main(String[] args) {
        /*Movie movie = new Movie();
        IMovie movieProxy = new MovieProxy(movie);
        movieProxy.play();*/

        groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            String temp = order(strs[i]);
            if (map.containsKey(temp)) {
                map.get(temp).add(strs[i]);
            } else {
                List<String> list = new ArrayList();
                list.add(strs[i]);

                map.put(temp, list);
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (String key : map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }

    //  排序
    public static String order(String strs) {
        int[] curr = new int[26];
        char[] array = strs.toCharArray();
        for (int i = 0; i < array.length; i++) {
            curr[array[i] - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            while (curr[i] > 0) {
                sb.append((char) (i + 'a'));
                curr[i]--;
            }
        }

        return  sb.toString();
    }
}