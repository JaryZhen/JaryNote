package tree;

import basc.TreeNode;

import java.util.*;

/**
 * @Author: Jary
 * @Date: 2020/6/28 10:59 AM
 */
public class TreeTest {

    /**
     * 深度优先遍历 - 递归的
     *
     * @param root
     */
    public static void dfsTree(TreeNode root) {
        if (root == null || root.left == null || root.right == null) return;
        System.out.println(root.left.val);//先序
        dfsTree(root.left);
        System.out.println(root.left.val);//中序
        dfsTree(root.right);
        System.out.println(root.right.val);//后序
    }

    /**
     * 深度优先遍历 - 非递归的  1. 先
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveFirs(TreeNode node) {
        System.out.println("firs:");
        Stack<TreeNode> stack = new Stack<>();
        if (node == null)
            return;
        stack.add(node);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.right != null)
                stack.add(curr.right);
            if (curr.left != null)
                stack.add(curr.left);
        }
    }

    /**
     * 深度优先遍历 - 非递归的  2. 中
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveMid(TreeNode node) {
        if (node == null)
            return;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {// 不空 压入节点 处理左节点
                stack.add(node);
                node = node.left;
            } else { //为空 弹出节点 再处理右节点
                node = stack.pop();
                System.out.print(node.val + " ");
                node = node.right;
            }
        }
    }

    /**
     * 深度优先遍历 - 非递归的 3. 后
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveLast(TreeNode node) {
        if (node == null)
            return;
        System.out.println("\nlast: ");
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> restack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            restack.add(curr);
            if (curr.left != null)
                stack.add(curr.left);
            if (curr.right != null)
                stack.add(curr.right);
        }
        while (!restack.isEmpty()) {
            System.out.print(restack.pop().val + " ");
        }
    }

    /**
     * 按层打印(广度优先)
     * 可以解决
     * 。。。1. 按层打印
     * 。。。2. 最大（小）宽度，以及对应的层数
     */
    public static void bfsTreePrint(TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque();
        int level = 1;
        queue.add(node);
        while (!queue.isEmpty()) {
            int si = queue.size();
            System.out.print("level=" + level + ", size=" + si + " :");
            // 打印当前这一层所有元素
            for (int i = 0; i < si; i++) {
                TreeNode curr = queue.poll();
                System.out.print(curr.val + "\t");
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    /**
     * 序列化
     */

    public static Queue serTreePre(TreeNode node) {
        Queue<Integer> queue = new ArrayDeque<>();
        serTreePre(node, queue);
        return queue;
    }

    public static void serTreePre(TreeNode hea, Queue<Integer> queue) {
        if (hea == null)
            queue.add(-1);
        else {
            queue.add(hea.val);
            serTreePre(hea.left, queue);
            serTreePre(hea.right, queue);
        }
    }

    public static TreeNode desTree(Queue<Integer> queue) {
        Integer curr = queue.poll();
        if (curr == -1)
            return null;
        TreeNode node = new TreeNode(curr);
        node.left = desTree(queue);
        node.right = desTree(queue);
        return node;
    }

    /**
     * 102. 给一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> le = new ArrayList<List<Integer>>();
        if (root == null) return le;
        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(root);
        bfsTree3(nodeQueue, le);
        return le;
    }

    public void bfsTree3(Queue<TreeNode> nodeQueue2, ArrayList<List<Integer>> le) {
        List<Integer> list;
        while (!nodeQueue2.isEmpty()) {
            list = new ArrayList<>();
            int si = nodeQueue2.size();
            for (int i = 0; i < si; i++) {
                TreeNode curr = nodeQueue2.poll();
                list.add(curr.val);
                if (curr.left != null) {
                    nodeQueue2.add(curr.left);
                }
                if (curr.right != null) {
                    nodeQueue2.add(curr.right);
                }
            }
            le.add(list);
        }
    }


    /**
     * 104. 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数
     * 返回它的最大深度 3 。
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left) + 1;
        int right = maxDepth(root.right) + 1;
        return left > right ? left : right;
    }

    public int maxDepth2(TreeNode root) {
        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(root);
        int level = 0;
        while (!nodeQueue.isEmpty()) {
            int si = nodeQueue.size();
            level++;
            for (int i = 0; i < si; i++) {
                TreeNode curr = nodeQueue.poll();
                if (curr.left != null) {
                    nodeQueue.add(curr.left);
                }
                if (curr.right != null) {
                    nodeQueue.add(curr.right);
                }
            }
        }
        return level;
    }

    public TreeNode maxComParent(TreeNode head, TreeNode p, TreeNode b) {
        if (head == null) return head;
        if (p.val > b.val) {
            TreeNode temp = b;
            b = p;
            p = temp;
        }
        Stack<TreeNode> stack = new Stack();
        stack.push(head);
        TreeNode yes = null;
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            Boolean isP = p.val > curr.val;
            Boolean isB = b.val > curr.val;
            if (isB && isP)
                stack.push(head.right);
            else if (!isB && !isP)
                stack.push(head.left);
            else {
                yes = head;
                break;
            }
        }
        return yes;
    }

    public TreeNode findAllparent(TreeNode head, TreeNode p, TreeNode q) {
        if (head == null || p == head || q == head) return head;
        TreeNode left = findAllparent(head.left, p, q);
        TreeNode right = findAllparent(head.right, p, q);
        TreeNode res;
        if (left == null) {
            res = right;
        } else if (right == null) {
            res = left;
        } else
            res = head;
        return res;
    }


    /*
    find parent path
     */
    static List<TreeNode> list = new ArrayList<>();

    public static TreeNode findAllparentPath(TreeNode head, TreeNode p) {
        if (head == null || p == head) return head;
        TreeNode left = findAllparentPath(head.left, p);
        TreeNode right = findAllparentPath(head.right, p);

        TreeNode res = null;
        if (left != null) {
            list.add(head);
            res = left;
        } else if (right != null) {
            list.add(head);
            res = right;
        }
        return res;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;

        int lev = 1;
        boolean is = true;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            int[] tm = new int[(int) Math.pow(2, lev)];
            int k = 0;
            for (int i = 0; i < n; i++) {
                TreeNode c = queue.poll();
                if (c.left != null) {
                    queue.add(c.left);
                    tm[k++] = c.left.val;
                } else
                    tm[k++] = Integer.MAX_VALUE;
                if (c.right != null) {
                    queue.add(c.right);
                    tm[k++] = c.right.val;
                } else
                    tm[k++] = Integer.MAX_VALUE;
            }
            ++lev;
            for (int i = 0; i < tm.length / 2; i++) {
                if (tm[i] != tm[tm.length - 1 - i]) {
                    is = false;
                    break;
                }
            }
        }
        return is;
    }

    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }
        int preorder_root = preorder_left;
        int inorder_root = indexMap.get(preorder[preorder_root]);
        TreeNode root = new TreeNode(preorder[preorder_root]);
        int size_left_subtree = inorder_root - inorder_left;
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }



    public static void main(String[] args) {
        TreeTest test = new TreeTest();

        int[] a = new int[]{8, 4, 3, 5, 12, 6, 88, 99, 13};
        int[] b = new int[]{3, 4, 5, 8, 6, 88, 99, 12, 13};
        TreeNode head = test.buildTree(a, b);
        /*
              8
            /   \
           4     12
          / \    / \
         3   5  6  13
                 \
                 88
                   \
                    99

        */

        bfsTreePrint(head);

        //dfsTree(head);
        //dfsTreeNonRecursiveFirs(head);
        //dfsTreeNonRecursiveMid(head);
        //dfsTreeNonRecursiveLast(head);

        // TreeNode node = desTree(serTreePre(head));

        TheBigTree bigTree = new TheBigTree();
        //System.out.println(bigTree.ifFull(head));
        //System.out.println(bigTree.maxDistens(head));
        //System.out.println(bigTree.maxBST(head));

        //System.out.println(findAllparentPath(head, left_left));
        //System.out.println(list);
        System.out.println(test.isSymmetric(head));
    }


}
