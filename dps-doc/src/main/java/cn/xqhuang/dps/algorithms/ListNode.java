package cn.xqhuang.dps.algorithms;

public class ListNode {
    int val;
    ListNode pre;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}