import basc.ListNode;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author: Jary
 * @Date: 2021/6/19 3:36 下午
 */
public class Test {
    public void ReverseList(ListNode head) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        // Rewers rStack = new Rewers();
        //rStack.reverse(stack);
        ListNode listNode = new ListNode("1", null,
                new ListNode("2", null,
                        new ListNode("3", null, null)));
        Test t = new Test();
        ListNode re = t.reversList(listNode);
        boolean hs = true;
        while (hs) {
            System.out.println(re.val);
            re = re.next;
            if (re == null)
                hs = false;
        }
    }

    public ListNode reversList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public static void heap() {
        PriorityQueue a = new PriorityQueue(2);
        a.add(10);
        a.add(23);
        a.add(6);
        System.out.println(a.size());
        System.out.println(a.poll());
    }

    public static int[] maxSliding(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        if (nums.length == 0) return result;

        for (int i = 0; i < nums.length - k + 1; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                if (nums[j] > max)
                    max = nums[j];
            }
            result[i] = max;
        }
        return result;
    }

    public static int[] maxSliding2(int[] nums, int k) {
        int[] q = new int[100010];
        int hh = 0, tt = -1;
        int[] res = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (tt >= hh && q[hh] < i - k + 1) hh++;
            while (tt >= hh && nums[q[tt]] <= nums[i]) tt--;
            q[++tt] = i;
            if (i >= k - 1) res[j++] = nums[q[hh]];
        }
        return res;
    }

    public static int[] maxSliding3(int[] nums, int k) {
        if (nums == null || nums.length < 2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length - k + 1];
        // 遍历nums数组
        for (int i = 0; i < nums.length; i++) {
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if (i + 1 >= k) {
                result[i + 1 - k] = nums[queue.peek()];
            }
        }
        return result;
    }

    public static void maxSliding() {
        int[] ints = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] re = maxSliding3(ints, 3);
        for (int i = 0; i < re.length; i++) {
            System.out.println(re[i]);
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean islag = false;
        ListNode head = new ListNode();
        ListNode cu = head;
        while (l1 != null || l2 != null) {
            int re1 = 0;
            if (l1 != null) {
                re1 = (int) l1.val;
                l1 = l1.next;
            }
            int re2 = 0;
            if (l2 != null) {
                re2 = (int) l2.val;
                l2 = l2.next;
            }
            int sum = islag ? re1 + re2 + 1 : re1 + re2;
            int isLa = sum >= 10 ? sum % 10 : sum;
            ListNode da = new ListNode(isLa);
            cu.next = da;
            cu = da;
            islag = sum >= 10 ? true : false;
        }
        if (islag) {
            cu.next = new ListNode(1);
        }
        return head;
    }

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }

    public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String> strings = new ArrayList<>();
        process("(", n - 1, n, "(", strings);
        return strings;
    }

    void process(String kuo, int left, int right, String s, List<String> list) {
        System.out.println("curr:  " + s);
        if (left == right && left == 0) {
            list.add(s);
            return;
        }

        if (left > 0)
            process("(", left - 1, right, s + "(", list);
        if (right > 0 && right > left)
            process(")", left, right - 1, s + ")", list);
    }

    public ListNode mergeTwoLists(ListNode<Integer> l1, ListNode<Integer> l2) {
        ListNode head = new ListNode();
        ListNode curr = head;
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                curr.next = l2;
                break;
            }
            if (l2 == null) {
                curr.next = l1;
                break;
            }
            if (l1.val >= l2.val) {
                curr.next = l2;
                l2 = l2.next;
            } else {
                curr.next = l1;
                l1 = l1.next;
            }
            curr = curr.next;
        }
        return head.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode ans = null;
        for (int i = 0; i < lists.length; ++i) {
            ans = mergeTwoLists(ans, lists[i]);
        }
        return ans;
    }

    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length <= 0) return null;
        ListNode ans = processMKL(0, lists.length - 1, lists);
        return ans;
    }

    ListNode processMKL(int lef, int rig, ListNode[] lists) {
        if (lef == rig) {
            return lists[lef];
        }
        int mid = lef + (rig - lef) / 2;
        ListNode leff = processMKL(lef, mid, lists);
        ListNode rigf = processMKL(mid + 1, rig, lists);

        return this.mergeTwoLists(leff, rigf);
    }

    public int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    int len = i - stack.peek();
                    maxans = Math.max(maxans, len);
                }
            }
        }
        return maxans;
    }

    //33
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            //前半部分有序,注意此处用小于等于
            if (nums[start] <= nums[mid]) {
                //target在前半部分
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {//后半部分有序,注意此处用小于等于
                if (target <= nums[end] && target > nums[mid]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    //34
    public int[] searchRange(int[] nums, int target) {
        int lef = processSR(nums, target, true);
        int rir = processSR(nums, target, false);
        return new int[]{lef, rir};
    }

    int processSR(int[] nums, int target, boolean lower) {
        if (nums == null || nums.length < 1)
            return -1;
        int lef = 0, rig = nums.length - 1;
        while (lef <= rig) {
            int mid = lef + (rig - lef) / 2;
            if (nums[mid] == target) {
                if (lower)
                    rig = mid - 1;
                else lef = mid + 1;
            } else if (nums[mid] > target) {
                rig = mid - 1;
                if (rig < 0) {
                    rig++;
                    break;
                }
            } else {
                lef = mid + 1;
                if (lef > nums.length - 1) {
                    lef--;
                    break;
                }
            }
        }
        return lower ? nums[lef] == target ? lef : -1 : nums[rig] == target ? rig : -1;
    }

    //39
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        processCS(candidates, 0, target, res, new ArrayList<>(), 0);
        return res;
    }

    private void processCS(int[] candidates, int index, int target, List<List<Integer>> lists, List<Integer> list, int sum) {
        if (index >= candidates.length)
            return;
        if (sum == target) {
            lists.add(new ArrayList<>(list));
            return;
        }
        if (sum > target) return;

        list.add(candidates[index]);
        sum = sum + candidates[index];
        processCS(candidates, index, target, lists, list, sum);
        list.remove(list.size() - 1);
        sum = sum - candidates[index];
        processCS(candidates, index + 1, target, lists, list, sum);
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String s = sort(strs[i]);
            if (map.containsKey(s)) {
                List<String> strings = map.get(s);
                strings.add(strs[i]);
            } else {
                List<String> list = new ArrayList<>();
                list.add(strs[i]);
                map.put(s, list);
            }
        }
        return map.values().stream().collect(Collectors.toList());
    }

    String sort(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    public boolean canJump(int[] nums) {
        if (nums.length <= 1) return false;
        int pre = 0;
        for (int s : nums) {
            if (s > 0) {
                pre = Math.max(pre - 1, s);
            } else {
                if (pre >= 1) {
                    pre--;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int i1 = 1;
        int i2 = 2;
        for (int i = 3; i <= n; i++) {
            int temp = i1 + i2;
            i1 = i2;
            i2 = temp;
        }
        return i2;
    }


    public int minDistance(String word1, String word2) {
        char[] a = word1.toCharArray();
        char[] b = word2.toCharArray();
        if (a.length < 1)
            return b.length;
        if (b.length < 1)
            return a.length;

        int[][] dp = new int[a.length + 1][b.length + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (a[i-1] == b[j-1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[a.length][b.length];
    }

    public int minDistance2(String word1, String word2) {
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        int[][] dp = new int[w1.length + 1][w2.length + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < w1.length + 1; i++) {
            for (int j = 1; j < w2.length + 1; j++) {
                if (w1[i - 1] == w2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
            }
        }
        return dp[w1.length][w2.length];
    }

    public static void main(String[] args) {
        Test test = new Test();
        int[] nums = new int[]{2, 0, 0}; //4,2,0,3,2,5  0,1,0,2,1,0,1,3,2,1,2,1
        int[][] mar = new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        ListNode list1 = new ListNode(2, new ListNode(4, new ListNode(9)));
        ListNode list2 = new ListNode(1, new ListNode(2, new ListNode(4, new ListNode(6, new ListNode(7)))));
        ListNode list3 = new ListNode(2, new ListNode(4, new ListNode(6)));
        ListNode list4 = new ListNode(2, new ListNode(4, new ListNode(8)));
        ListNode list5 = new ListNode(2, new ListNode(4, new ListNode(10)));

        ListNode[] nodes = new ListNode[]{list1, list2, list3, list4, list5};
        ListNode re = test.mergeKLists2(nodes);
       /* while (re != null) {
            System.out.print(re.val + " ");
            re = re.next;
        }*/

        String w1 = "umonoult";//zoologicoarchaeologist
        String w2 = "ul";
        System.out.println(test.minDistance(w1, w2));
        System.out.println(test.minDistance2(w1, w2));

    }
}
