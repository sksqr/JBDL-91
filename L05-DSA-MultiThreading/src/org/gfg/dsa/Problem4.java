package org.gfg.dsa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//https://www.geeksforgeeks.org/problems/level-order-traversal/1
public class Problem4 {

    public ArrayList<ArrayList<Integer>> levelOrder(Node root) {
        // code here
        return null;
    }

    public static void main(String[] args) {
        Problem4 obj = new Problem4();
        Node node1 = new Node(10);
        Node node2 = new Node(20);
        Node node3 = new Node(30);
        Node node4 = new Node(40);
        Node node5 = new Node(50);

        node1.left=node2;
        node1.right=node3;

        node2.left=node4;
        node2.right=node5;

        System.out.println(obj.levelOrd(node1));
    }

    public List<Integer> levelOrd(Node root) {
        // code here
        List<Integer> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            result.add(node.data);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        return result;
    }
}
class Node {
    int data;
    Node left, right;

    Node(int item) {
        data = item;
        left = right = null;
    }
}
