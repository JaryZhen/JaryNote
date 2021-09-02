import basc.ListNode;

import java.util.*;
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
                if (a[i - 1] == b[j - 1]) {
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

    void swop(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void sortColors(int[] nums) {
        int tm = 1;
        int low = -1, mid = 0;
        int more = nums.length;
        while (mid < more) {
            if (nums[mid] > tm) {
                swop(nums, mid, --more);
            } else if (nums[mid] < tm) {
                swop(nums, ++low, mid++);
            } else {
                mid++;
            }
        }
    }

/*
        动态规划算法，dp[i]表示s前i个字符能否拆分
        转移方程：dp[j] = dp[i] && check(s[i+1, j]);
        check(s[i+1, j])就是判断i+1到j这一段字符是否能够拆分
        其实，调整遍历顺序，这等价于s[i+1, j]是否是wordDict中的元素
        这个举个例子就很容易理解。
        假如wordDict=["apple", "pen", "code"],s = "applepencode";
        dp[8] = dp[5] + check("pen")
        翻译一下：前八位能否拆分取决于前五位能否拆分，加上五到八位是否属于字典
        （注意：i的顺序是从j-1 -> 0哦~
    */

    public HashMap<String, Boolean> hash = new HashMap<>();

    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 0; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                String sub = s.substring(j, i);
                dp[i] = dp[j] && check(sub, wordDict);
                if (dp[i]) break;
            }
        }
        return dp[s.length()];
    }

    public boolean check(String s, List<String> d) {
        return d.contains(s);
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return true;
        ListNode slow = head.next;
        ListNode fast = head.next.next;

        while (slow != null && fast != null) {
            if (slow == fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    public ListNode sortList(ListNode head) {
        return process(head, null);
    }

    private ListNode process(ListNode head, ListNode tail) {
        if (head == null)
            return head;
        if (head.next == tail) {
            head.next = null;
            return head;
        }

        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }

        ListNode left = process(head, slow);
        ListNode right = process(slow.next, tail);
        return mergeTwoLists(left, right);
    }

    /**
     * 归并排序—— 二分的意思
     * 将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            //从两个数组中选取较小的数放入中间数组
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        //将剩余的部分放入中间数组
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }
        //将中间数组复制回原数组
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
    }

    public int maxProduct(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }

        // dp[i][0]：以 nums[i] 结尾的连续子数组的最小值
        // dp[i][1]：以 nums[i] 结尾的连续子数组的最大值
        int[][] dp = new int[len][2];
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        for (int i = 1; i < len; i++) {
            int curr = nums[i];
            if (curr >= 0) {
                dp[i][0] = Math.min(curr, curr * dp[i - 1][0]);
                dp[i][1] = Math.max(curr, curr * dp[i - 1][1]);
            } else {
                dp[i][0] = Math.min(curr, curr * dp[i - 1][1]);
                dp[i][1] = Math.max(curr, curr * dp[i - 1][0]);
            }
        }

        // 只关心最大值，需要遍历
        int res = dp[0][1];
        for (int i = 1; i < len; i++) {
            res = Math.max(res, dp[i][1]);
        }
        return res;
    }

    public int rob2(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        if (nums.length == 2)
            return Math.max(nums[0], nums[1]);

        int pre = nums[0];
        int preMax = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int curr = Math.max(pre + nums[i], preMax);
            pre = preMax;
            preMax = curr;
        }
        return preMax;
    }

    public int rob(int[] nums) {
        int pre = 0, max = 0, preMax;
        for (int num : nums) {
            preMax = max;
            max = Math.max(pre + num, max);
            pre = preMax;
        }
        return max;
    }

    public boolean canFinish(int numCourses, int[][] ints) {
        HashSet<Integer> set = new HashSet();
        HashMap<Integer, String> mapL = new HashMap();

        for (int i = 0; i < ints.length; i++) {
            int key = ints[i][0];
            if (mapL.containsKey(key))
                mapL.put(key, mapL.get(key) + "-" + ints[i][1]);
            else mapL.put(key, ints[i][1] + "");
        }
        for (int i = 0; i < ints.length; i++) {
            int key = ints[i][0];
            processCanF(key, set, mapL);

        }

        return false;
    }

    void processCanF(Integer key, HashSet<Integer> set, HashMap<Integer, String> mapL) {
        if (set.contains(key))
            return;
        String[] va = mapL.get(key).split("-");
        if (va.length < 1) {
            set.add(key);
        } else {
            for (String s : va) {
                processCanF(Integer.valueOf(s), set, mapL);
            }
        }
    }


    class Trie {
        private Trie[] children;
        private boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }

        private Trie searchPrefix(String prefix) {
            Trie node = this;
            for (int i = 0; i < prefix.length(); i++) {
                char ch = prefix.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    return null;
                }
                node = node.children[index];
            }
            return node;
        }
    }

    public boolean isLand(int[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1;
    }

    public int maximalRectangle(char[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) return 0;
        int columns = matrix[0].length;
        int[][] dp = new int[rows][columns];
        //求长度
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = j == 0 ? 1 : dp[i][j - 1] + 1;
                }
            }
        }
        int area = 0;
        //求面积
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '0') continue;
                int len = dp[i][j];
                for (int k = i; k >= 0 && matrix[k][j] == '1'; k--) {//求高
                    len = Math.min(len, dp[k][j]);//得到长度
                    area = Math.max(area, (i - k + 1) * len);
                }
            }
        }
        return area;
    }

    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) return 0;
        int columns = matrix[0].length;
        int[][] dp = new int[rows][columns];

        int are = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '1') {
                    if (j == 0 || i == 0) {
                        dp[i][j] = 1;
                        are = Math.max(are, 1);
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1;
                        are = Math.max(are, dp[i][j] * dp[i][j]);
                    }

                }
            }
        }
        return are;
    }

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        if (head.next.next == null)
            return head.val == head.next.val;
        Stack<Integer> stack = new Stack<>();
        ListNode<Integer> slow = head.next;

        ListNode fast = head.next.next;

        stack.add((int) head.val);
        stack.add(slow.val);

        int n = 2;
        while (fast != null) {
            if (fast.next == null) {
                n = n + 1;
                break;
            } else {
                fast = fast.next.next;
                n = n + 2;
                slow = slow.next;
                stack.add(slow.val);
            }
        }

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        stack.pop();
        if (n % 2 == 1) slow = slow.next;
        while (slow != null) {
            if (stack.pop() != slow.val) return false;
            slow = slow.next;
        }
        return true;
    }

    public int numSquares(int n) {
        if (n <= 3)
            return n;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int dex = i * i;
            if (dex <= n)
                dp[dex] = 1;
            else break;
        }
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 1; i <= n; i++) {
            if (dp[i] != 0) continue;
            int min = n;
            for (int j = 1; j < i && i - j >= i / 2; j++) {
                min = Math.min(min, dp[j] + dp[i - j]);
            }
            dp[i] = min;
        }
        return dp[n];
    }

    public void moveZeroes(int[] nums) {
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[slow] == 0) {
                swop(nums, slow, fast++);
            } else {
                slow++;
                if (slow > fast) fast++;
            }
        }
    }

    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[slow];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length <= 1)
            return nums.length;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int rema = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 0;
            for (int j = 0; j <= i; j++) {
                max = Math.max(max, nums[i] > nums[j] ? dp[j] + 1 : 1);
            }
            dp[i] = max;
            rema = Math.max(rema, dp[i]);
        }
        return rema;
    }

    public int maxProfit(int[] prices) {

        int[][] dp = new int[prices.length][2];
        // o: 不持有
        // 1: 持有 fei冷冻
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;

        for (int i = 0; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            dp[i][1] = dp[i - 1][0] - prices[i];
            dp[i][2] = dp[i - 1][0] - prices[i];
        }
        return dp[prices.length][0];
    }

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] > amount) continue;
            dp[coins[i]] = 1;
        }
        for (int i = 1; i < dp.length; i++) {
            if (dp[i] == 1) continue;
            int le = 1;
            int ri = i - 1;
            int min = Integer.MAX_VALUE;
            while (le <= ri) {
                if (dp[le] == -1 || dp[ri] == -1) {
                    le++;
                    ri--;
                    continue;
                }
                min = Math.min(dp[le++] + dp[ri--], min);
            }
            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
        }
        return dp[amount];
    }

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]))
                map.put(nums[i], map.get(nums[i]) + 1);
            else map.put(nums[i], 1);
        }
        PriorityQueue<int[]> queue = new PriorityQueue(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        });

        for (int a : map.keySet()) {
            queue.add(new int[]{a, map.get(a)});
        }

        int[] ret = new int[k];
        for (int i = 0; i < k; ++i) {
            ret[i] = queue.poll()[0];
        }
        return ret;
    }

    public String decodeString(String s) {
        Stack<String> ch = new Stack<>();
        ch.add("");
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (s.charAt(i) == ']') {
                String c = ch.pop();
                ch.pop();
                int n = Integer.valueOf(ch.pop());
                String tem = ch.pop();
                for (int j = 0; j < n; j++) {
                    tem = tem + c;
                }
                ch.push(tem);
            } else {
                String ss = "";
                while (Character.isLetter(cur)) {
                    ss = ss + cur;
                    cur = s.charAt(++i);
                }
                ch.add(ss);
            }
        }
        return ch.pop();
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i];
            nums[tmp - 1] = tmp * 10;
        }
        List a = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums.length) a.add(i + 1);
        }
        return a;
    }

    public int[][] reconstructQueue(int[][] people) {
        // quickSort(people, 0, people.length - 1);
        List<int[]> list = new ArrayList<>();
        for (int[] p : people)
            list.add(p[1], p);

        return list.toArray(new int[0][2]);
    }

    private void quickSort(int[][] arr, int left, int right) {
        if (left >= right) return;
        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[][] arr, int left, int right) {
        int[] pivot = arr[left];
        while (left < right) {
            while (left < right) {
                if (arr[right][0] > pivot[0] || arr[right][0] == pivot[0] && arr[right][1] < pivot[1]) {
                    arr[left++] = arr[right];
                    break;
                }
                right--;
            }
            while (left < right) {
                if (arr[left][0] < pivot[0] || arr[left][0] == pivot[0] && arr[left][1] > pivot[1]) {
                    arr[right--] = arr[left];
                    break;
                }
                left++;
            }
        }
        arr[left] = pivot;
        return left;
    }

    public int[][] reconstructQueue2(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] person1, int[] person2) {
                if (person1[0] != person2[0]) {
                    return person2[0] - person1[0];
                } else {
                    return person1[1] - person2[1];
                }
            }
        });
        List<int[]> ans = new ArrayList<int[]>();
        for (int[] person : people) {
            ans.add(person[1], person);
        }
        return ans.toArray(new int[ans.size()][]);
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        if ((sum & 1) == 1) {
            return false;
        }
        int length = nums.length;
        int target = sum >> 1;
        //dp[j]表示前i个元素可以找到相加等于j情况
        boolean[] dp = new boolean[target + 1];
        //对于第一个元素，只有当j=nums[0]时，才恰好填充满
        if (nums[0] <= target) {
            dp[nums[0]] = true;
        }

        for (int i = 1; i < length; i++) {
            //j由右往左直到nums[i]
            for (int j = target; j >= nums[i]; j--) {
                //只有两种情况，要么放，要么不放
                //取其中的TRUE值
                dp[j] = dp[j] || dp[j - nums[i]];
            }
            //一旦满足，结束，因为只需要找到一组值即可
            if (dp[target]) {
                return dp[target];
            }
        }
        return dp[target];
    }

    //背包问题
    public int zeroOneKnapsack(int[] weights, int[] values, int target) {
        int N = weights.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1];//每个物品的体积和价值

            for (int j = 1; j <= target; j++) {
                if (j >= w) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][target];
    }

    public int zeroOneKnapsack_dp(int[] weights, int[] values, int targ) {
        int[] dp = new int[targ + 1];
        for (int i = 1; i <= weights.length; i++) {
            int w = weights[i - 1];
            int v = values[i - 1];//每个物品的体积和价值
            for (int j = targ; j >= w; j--) {
                dp[j] = Math.max(dp[j], dp[j - w] + v);
            }
        }
        return dp[targ];
    }

    public int zeroOneKnapsack_Rec(int[] weight, int[] value, int bag) {
        //从开头开始
        return processMaxValue(weight, value, 0, 0, bag, 0);
    }

    private int processMaxValue(int[] weight, int[] value,
                                int index,
                                int currentBag,
                                int bag, int maxValue) {
        if (currentBag >= bag) {
            return maxValue;
        }
        if (index >= value.length)
            return maxValue;

        int res1 = processMaxValue(weight, value, index + 1, currentBag + weight[index], bag, maxValue + value[index]);
        int res2 = processMaxValue(weight, value, index + 1, currentBag, bag, maxValue);
        return Math.max(res1, res2);
    }

    public int findTargetSumWays(int[] nums, int target) {
        return dfsFS(nums, 0, 0, target);
    }

    int dfsFS(int[] arr, int index, int sum, int target) {
        if (index >= arr.length) {
            if (sum == target) return 1;
            else return 0;
        }
        int le = dfsFS(arr, index + 1, sum + arr[index], target);
        int re = dfsFS(arr, index + 1, sum - arr[index], target);
        return le + re;
    }


    public int findUnsortedSubarray(int[] nums) {
        if (nums.length <= 1)
            return 0;
        if (nums.length == 2)
            return nums[0] <= nums[1] ? 0 : 2;

        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int k = start + 1; k < end; k++) {
                min = Math.min(min, nums[k]);
                max = Math.max(max, nums[k]);
            }
            if (nums[start] > min && nums[end] < max)
                break;
            if (nums[start] >= nums[end])
                break;
            if (nums[start] <= min)
                start++;
            if (nums[end] >= max) {
                end--;
            }
        }

        return start == end ? 0 : end - start + 1;
    }

    public int[] dailyTemperatures(int[] tem) {
        int[] big = new int[tem.length];
        big[tem.length - 1] = 0;
        for (int i = tem.length - 2; i >= 0; i--) {
            big[i] = 0;
            for (int j = i + 1; j < tem.length; j++) {
                if (tem[i] < tem[j]) {
                    big[i] = j - i;
                    break;
                }
            }
        }
        return big;
    }
    public static void main(String[] args) {
        Test test = new Test();
        int[] nums = new int[]{73,74,75,71,69,72,76,73}; //4,2,0,3,2,5  0,1,0,2,1,0,1,3,2,1,2,1
        int[][] mar = new int[][]{
                {7, 0},
                {4, 4},
                {7, 1},
                {5, 0},
                {6, 1},
                {5, 2}};

        char[][] chars = new char[][]{
                {'1', '0', '1', '1', '0', '1'},
                {'1', '1', '1', '1', '1', '1'},
                {'0', '1', '1', '0', '1', '1'},
                {'1', '1', '1', '0', '1', '0'},
                {'0', '1', '1', '1', '1', '1'},
                {'1', '1', '0', '1', '1', '1'}};

        char[][] strin = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};

        ListNode list1 = new ListNode(2, new ListNode(4, new ListNode(9)));
        ListNode list2 = new ListNode(1, new ListNode(2, new ListNode(22, new ListNode(1))));

        int[] weght = new int[]{4, 3, 1}; //4,2,0,3,2,5  0,1,0,2,1,0,1,3,2,1,2,1
        int[] value = new int[]{30, 20, 15}; //4,2,0,3,2,5  0,1,0,2,1,0,1,3,2,1,2,1
        // System.out.println(test.zeroOneKnapsack_dp(weght, value, 4));
        //System.out.println(test.zeroOneKnapsack(weght, value, 4));

        //System.out.println(test.zeroOneKnapsack_Rec(weght, value, 4));

        System.out.println(test.dailyTemperatures(nums));


    }
}
