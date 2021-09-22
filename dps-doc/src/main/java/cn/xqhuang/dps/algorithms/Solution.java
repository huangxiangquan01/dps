package cn.xqhuang.dps.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    private static int[] queue;
    private static String[][] value;

    public static void main(String[] args) {
        System.out.println();
        ListNode node = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(4,
                                        new ListNode(5, new ListNode(6, new ListNode(7,
                                                new ListNode(8, new ListNode(9, new ListNode(10))))))))));
        splitListToParts(node, 3);
    }


    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode[] splitListToParts(ListNode head, int k) {
        int num  = 1;
        ListNode firs = head;
        while(firs.next != null) {
            num++;
            firs = firs.next;
        }
        int temp = num % k;
        int val = num / k;

        int[] index = new int[k];
        for (int i = 0; i < k; i++) {
            if (k >= num) {
                index[i] = 1;
            } else {
                if (temp > 0) {
                    index[i] = val + 1;
                    temp--;
                } else {
                    index[i] = val;
                }
            }
        }

        ListNode[] ans = new ListNode[k];
        ListNode node = head;

        int ansIndex = 0;
        while (node != null) {
            ListNode curr = new ListNode();
            ListNode listNode = curr;
            for (int i = 0; i < index[ansIndex] - 1; i++) {
                if (node != null) {
                    listNode.next = new ListNode(node.val);
                    listNode = listNode.next;
                }
                node = node.next;
            }
            ans[++ansIndex] = curr.next;
        }

        return ans;
    }
}
