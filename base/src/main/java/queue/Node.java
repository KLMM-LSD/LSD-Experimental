/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Lasse
 */
public class Node {

    public static AtomicReference<Node> LIST = new AtomicReference<>();

    public enum KIND {
        POST,
        SIGNUP
    }

    public String body;
    public Node next;
    public KIND kind;

    public Node(String body, KIND kind) {
        this.body = body;
        this.kind = kind;
        this.next = null;
    }

    public static int getListLength(Node n) {
        int ret = 0;
        Node cur = n;
        while (cur != null) {
            ret++;
            cur = cur.next;
        }
        return ret;
    }

    public static void insertNode(Node n) {
        Node prev;

        for (;;) {
            prev = LIST.get();
            n.next = prev;
            if (LIST.compareAndSet(prev, n)) {
                break;
            }
        }
    }

    public static Node popList() {
        Node n = LIST.getAndSet(null);
        Node cur, prev;

        if (n == null) {
            return null;
        }

        prev = null;
        cur = n;
        while (cur != null) {
            Node tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }

}
