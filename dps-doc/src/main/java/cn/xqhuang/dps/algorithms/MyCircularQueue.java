package cn.xqhuang.dps.algorithms;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/8/210:47
 */
public class MyCircularQueue {


    Node head;

    Node tail;

    int current;

    int count;

    public static class Node {
        int value;

        Node prev;

        Node next;

        public Node(int value) {
            this.value = value;
        }
    }


    public MyCircularQueue(int k) {
        current = 0;
        count = k;
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        head.prev = tail;
        tail.prev = head;
        tail.next = head;
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        Node node = new Node(value);

        Node prev = tail.prev;

        prev.next = node;
        node.prev = prev;

        node.next = tail;
        tail.prev = node;

        current++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        Node next = head.next.next;

        head.next = next;
        next.prev = head;
        current--;
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }

        return head.next.value;
    }

    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return tail.prev.value;
    }

    public boolean isEmpty() {
        return current == 0;
    }

    public boolean isFull() {
        return current == count;
    }

    public static void main(String[] args) {
        MyCircularQueue circularQueue = new MyCircularQueue(8); // 设置长度为 3
        circularQueue.enQueue(3); // 返回 true
        circularQueue.enQueue(9); // 返回 true
        circularQueue.enQueue(5); // 返回 true
        circularQueue.enQueue(0); // 返回 false，队列已满
        circularQueue.deQueue(); // 返回
        circularQueue.deQueue(); // 返回
        circularQueue.isEmpty(); // 返回
        circularQueue.isEmpty(); // 返回
        System.out.println(circularQueue.Rear()); // 返回
        System.out.println(circularQueue.Rear()); // 返回
        circularQueue.deQueue(); // 返回 4

    }
}
