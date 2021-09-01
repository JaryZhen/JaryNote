package tree;

import basc.ListNode;
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

    public void flatten(TreeNode root) {
        TreeNode s = new TreeNode(null, null, root.val);
        dfsFla(root, s);
    }

    TreeNode dfsFla(TreeNode root, TreeNode list) {
        if (root == null)
            return list;
        System.out.println(root.val);
        list.right = new TreeNode(null, null, root.val);
        list = list.right;
        TreeNode listNode = dfsFla(root.left, list);
        TreeNode listNode1 = dfsFla(root.right, listNode);
        return listNode1;
    }


    class MaxPathSum {
        int max;
        boolean wan;

        public MaxPathSum(int max, boolean wan) {
            this.max = max;
            this.wan = wan;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public boolean isWan() {
            return wan;
        }

        public void setWan(boolean wan) {
            this.wan = wan;
        }
    }

    public int maxPathSum(TreeNode root) {

        MaxPathSum max = dfsMax(root, new MaxPathSum(0, true));
        return max.max;
    }

    private MaxPathSum dfsMax(TreeNode root, MaxPathSum max) {
        if (root == null) {
            return max;
        }
        if (!max.isWan())
            return max;

        int m = root.val;
        boolean is = max.isWan();
        if (root.val + max.getMax() >= root.val) {
            m = root.val + max.getMax();
            is = true;
        } else
            is = false;

        MaxPathSum le = dfsMax(root.left, new MaxPathSum(m, is));
        MaxPathSum lr = dfsMax(root.right, le);
        return lr;
    }

    private int maxPathSum = Integer.MIN_VALUE;

    private int recur(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = recur(root.left), r = recur(root.right);
        int res = root.val + Math.max(0, l) + Math.max(0, r);
        maxPathSum = Math.max(maxPathSum, res);

        return Math.max(Math.max(l, r), 0) + root.val;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return dfsLCA(root, p, q);
    }

    TreeNode dfsLCA(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val == q.val || root.val == p.val)
            return root;

        TreeNode lef = root.left == null ? null : dfsLCA(root.left, p, q);
        TreeNode rig = root.right == null ? null : dfsLCA(root.right, p, q);
        if (lef != null && rig != null)
            return root;
        if (lef != null) return lef;
        if (rig != null) return rig;
        return null;
    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return dfsSer(root, "");
    }

    private String dfsSer(TreeNode root, String ser) {
        if (root == null) {
            ser = ser + "#" + "null";
            return ser;
        }
        return dfsSer(root.right, dfsSer(root.left, ser + "#" + root.val));
    }


    int index = 1;

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return dfsDer(new TreeNode(), data.split("#"));
    }

    private TreeNode dfsDer(TreeNode node, String[] list) {
        String da = list[index++];
        if ("null".equals(da)) {
            node = null;
            return node;
        }
        node = new TreeNode(Integer.valueOf(da));
        node.left = dfsDer(new TreeNode(), list);
        node.right = dfsDer(new TreeNode(), list);
        return node;
    }

    public int rob(TreeNode root) {
        return Math.max(dfsRob(root)[0], dfsRob(root)[1]);
    }

    int[] dfsRob(TreeNode node) {
        if (node == null)
            return new int[]{0, 0};
        int[] le = dfsRob(node.left);
        int[] ri = dfsRob(node.right);

        int yes = node.val;
        int no = Math.max(le[0], le[1]) + Math.max(ri[0], ri[1]);
        return new int[]{yes, no};
    }

    public int pathSum(TreeNode root, int sum) {
        // key是前缀和, value是大小为key的前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0的一条路径
        prefixSumCount.put(0, 1);
        // 前缀和的递归回溯思路
        return recursionPathSum(root, prefixSumCount, sum, 0);
    }

    /**
     * 前缀和的递归回溯思路
     * 从当前节点反推到根节点(反推比较好理解，正向其实也只有一条)，有且仅有一条路径，因为这是一棵树
     * 如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
     * 所以前缀和对于当前路径来说是唯一的，当前记录的前缀和，在回溯结束，回到本层时去除，保证其不影响其他分支的结果
     *
     * @param node      树节点
     * @param prefixMsp 前缀和Map
     * @param target    目标值
     * @param currSum   当前路径和
     * @return 满足题意的解
     */
    private int recursionPathSum(TreeNode node, Map<Integer, Integer> prefixMsp, int target, int currSum) {
        if (node == null) {
            return 0;
        }
        int res = 0;
        currSum += node.val;
        int a = currSum - target;
        res += prefixMsp.getOrDefault(a, 0);

        prefixMsp.put(currSum, prefixMsp.getOrDefault(currSum, 0) + 1);

        res += recursionPathSum(node.left, prefixMsp, target, currSum);
        res += recursionPathSum(node.right, prefixMsp, target, currSum);

        // 4.回到本层，恢复状态，去除当前节点的前缀和数量
        prefixMsp.put(currSum, prefixMsp.get(currSum) - 1);
        return res;
    }

    public boolean isValidBST(TreeNode head, int lowe, int uper) {
        if (head == null) return true;
        if (head.val < lowe || head.val > uper)
            return false;
        boolean le = isValidBST(head.left, lowe, head.val);
        boolean ri = isValidBST(head.right, head.val, uper);
        return le && ri;
    }


    public TreeNode convertBST(TreeNode root) {
        dfsCBST(root, 0);
        return root;
    }

    public int dfsCBST(TreeNode root, int v) {
        if (root == null)
            return 0;
        int ri = dfsCBST(root.right, v);
        root.val = root.val + v + ri;
        int le = dfsCBST(root.left, root.val);

        return le;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        return dfsH(root.left)+ dfsH(root.right);
    }

    int dfsH(TreeNode treeNode) {
        if (treeNode == null) return 0;
        int le = dfsH(treeNode.left) + 1;
        int re = dfsH(treeNode.right) + 1;
        return le > re ? le : re;
    }

    public static void main(String[] args) {
        TreeTest test = new TreeTest();

        int[] a = new int[]{10, 5, 20, 15, 5, 10};
        int[] b = new int[]{5, 10, 15, 20, 5, 10};

        TreeNode head = new TreeNode(4,
                new TreeNode(2,
                        new TreeNode(1),
                        new TreeNode(3)),
                new TreeNode(8,
                        new TreeNode(6),
                        new TreeNode(9)));

        bfsTreePrint(head);

        //dfsTree(head);
        //dfsTreeNonRecursiveFirs(head);
        //dfsTreeNonRecursiveMid(head);
        //dfsTreeNonRecursiveLast(head);

        // TreeNode node = desTree(serTreePre(head));

        //System.out.println(bigTree.ifFull(head));
        //System.out.println(bigTree.maxDistens(head));
        //System.out.println(bigTree.maxBST(head));

        //System.out.println(findAllparentPath(head, left_left));
        //System.out.println(list);
        //bfsTreePrint(test.invertTree(head));

        System.out.println(test.convertBST(head));
    }


}
