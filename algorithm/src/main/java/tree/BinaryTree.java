package tree;

import com.sun.jmx.remote.internal.ArrayQueue;
import stack_queue.Queue;

import java.util.LinkedList;

public class BinaryTree {
    private Node root = null;


    private class Node {
        private Node left;
        private Node right;
        private String data;

        public Node(Node left, Node right,String data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }

    public Node create(){
        Node G = new Node(null,null,"G");
        Node E = new Node(null,null,"E");
        Node F = new Node(G,null,"F");
        Node C = new Node(E,F,"C");
        Node B = new Node(null,null,"B");
        Node A = new Node(B,C,"A");
        return A;
    }


    public static void main(String[] args) {
        Node root = new BinaryTree().create();
        Node next = root;
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(root);

        while (!queue.isEmpty()){
            Node temp  = queue.poll();
            System.out.print(temp.data +" " );
            if (next == temp){
                System.out.print("\n");
            }
            if (temp.left != null){
                next = temp.left;
                queue.offer(temp.left);
            }
            if (temp.right != null){
                queue.offer(temp.right);
                next = temp.right;
            }
        }


    }
}
