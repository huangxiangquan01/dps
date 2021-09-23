package cn.xqhuang.dps.algorithms;

import java.util.Arrays;

/**
 * @author huangxq
 * @date
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = { 1, 2, 7, 4, 5, 6 };

        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        bubbleSort(arr);

        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        int temp;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
