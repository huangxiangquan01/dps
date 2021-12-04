package cn.xqhuang.dps.algorithms;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Solution {

    public static void main(String[] args) {

        System.out.println(largestSumAfterKNegations(new int[]{5,4,3,2,1}, 1));

    }

    public static int largestSumAfterKNegations(int[] nums, int k) {
        int len = nums.length;

        int index = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] < 0) {
                index++;
            }
        }
        Arrays.sort(nums);
        if (index >= k) {
            for (int i = 0; i < k; i++) {
                nums[i] = 0 - nums[i];
            }
        } else {
            int temp = k - index;
            for (int i = 0; i < index; i++) {
                nums[i] = 0 - nums[i];
            }
            Arrays.sort(nums);
            if (temp % 2 != 0) {
                nums[0] = 0 - nums[0];
            }
        }

        return Arrays.stream(nums).sum();
    }

}
