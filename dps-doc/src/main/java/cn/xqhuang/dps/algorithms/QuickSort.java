package cn.xqhuang.dps.algorithms;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int arr[] = new int[]{1,3,5,4,6};

        quickSort(arr, 0, 4);

        System.out.println(Arrays.toString(arr));
    }

    private static  void quickSort(int arr[], int left, int right) {
        if (left < right) {
            int partition = partition(arr, left, right);

            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
    }

    private static int partition(int arr[], int left, int right) {
        int pivot = arr[left];
        while ( left < right) {
            while (left < right && arr[right] >= pivot) {
                --right;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot) {
                ++left;
            }

            arr[right] = arr[left];
        }
        arr[left] = pivot;

        return left;
    }

}
