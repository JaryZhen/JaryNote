package test;

import java.util.*;

public class Test {
    public static void main(String[] args) {
    }

/*
    【第一题】JSON格式转换
    在某个特定应用场景中，我们有一个从JSON获取的内容，比如：
    m = { "a": 1, "b": { "c": 2, "d": [3,4] } }
    现在需要把这个层级的结构做展开，只保留一层key/value结构。对于上述输入，需要得到的结构是：
    o = {"a": 1, "b.c": 2, "b.d": [3,4] }
    也就是说，原来需要通过 m["b"]["c"] 访问的值，在展开后可以通过 o["b.c"] 访问。
    请实现这个“层级结构展开”的代码。
    输入：任意JSON（或者map/dict）
    输出：展开后的JSON（或者map/dict）
*/

    public static Map<String, Object> formatToJson(Map<String, Object> map) {
        HashMap<String, Object> resMap = new HashMap<>();
        process(map, resMap, "");
        return resMap;
    }

    private static void process(Map<String, Object> inputMap, Map<String, Object> resMap, String head) {
        Iterator<Map.Entry<String, Object>> it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof Map) {
                process((Map<String, Object>) value, resMap, head + key + ".");
            } else {
                resMap.put(head + key, value);
            }
        }
    }

/*

【第二题】数据存取
    我们的程序运行过程中用到了一个数组a，数组元素是一个map/dict。
    数组元素的“键”和“值”都是字符串类型。在不同的语言中，对应的类型是：
    PHP的array, Java的HashMap, C++的std::map, Objective-C的NSDictionary, Swift的Dictionary, Python的dict, JavaScript的object, 等等
    示例：
    a[0]["key1"]="value1"
    a[0]["key2"]="value2"
    a[1]["keyA"]="valueA"
            ...
    为了方便保存和加载，我们使用了一个基于文本的存储结构，数组元素每行一个：
    text=
    "key1=value1\n
    key2=value2\n
    keyA=valueA\n..."

    要求：请实现一个“保存”函数、一个“加载”函数。
    text=store(a);  //把数组保存到一个文本字符串中
    a=load(text); //把文本字符串中的内容读取为数组
    必须严格按照上述的“每行一个、key=value”的格式保存。
*/

    /**
     * 把数组保存到一个文本字符串中
     *
     * @param mapStr
     * @return 目标字符串
     */
    public static String store(Map<String, String>[] mapStr) {
        if (mapStr == null || mapStr.length < 1) {
            return "";
        }

        StringBuilder resStr = new StringBuilder();
        for (Map<String, String> map : mapStr) {
            resStr.append(mapToString(map));
        }
        return resStr.toString();
    }

    private static String mapToString(Map<String, String> map) {
        StringBuilder resStr = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<String, String> next = it.next();
            resStr.append(next.getKey()).append("=").append(next.getValue()).append("\n");

        }
        return resStr.toString();
    }

    /**
     * 把文本字符串中的内容读取为数组
     *
     * @param str
     * @return
     */
    public static Map<String, String>[] load(String str) {
        String[] strings = str.split("\\n");
        int length = strings.length;
        Map<String, String>[] resMap = new Map[length];
        for (int i = 0; i < length; i++) {
            resMap[i] = stringToMap(strings[i]);
        }
        return resMap;
    }

    private static Map<String, String> stringToMap(String str) {
        HashMap<String, String> res = new HashMap<>();
        String[] split = str.split(";");
        for (String s : split) {
            String[] kv = s.split("=");
            res.put(kv[0], kv[1]);
        }
        return res;
    }

    /*
    【第三题】路径规划
        假设现在有一个有向无环图，每个节点上都带有正数权重。我们希望找到一条最优路径，使得这个路径上经过的节点的权重之和最大。
        输入：n个节点，m个路径，起点
        输出：最优路径的权重值之和
        举例：
        3个节点：
        A 1
        B 2
        C 2
        3条路径：
        A->B
        B->C
        A->C
        起点：
        A
        输出：5  （最优路径是 A->B->C ，权重之和是 1+2+2=5）
        附加问题：我们要求的输入是有向无环图，但是没人知道实际使用的时候会有什么数据输入进来，如何避免输入了带环路的图导致的死循环呢？
    */
// 最大权重之和
    private int max = 0;

    public int find(int[] vertexs, int[][] edges, int start) {
        dfs(vertexs, edges, start, 0, new HashSet<Integer>());
        return max;
    }

    public void dfs(int[] vertexs, int[][] edges, int index, int sum, Set<Integer> set) {
        sum += vertexs[index];
        int length = vertexs.length;
        for (int i = 0; i < length; i++) {
            if (edges[index][i] != 0) {
                set.add(i);
                dfs(vertexs, edges, i, sum, set);
                set.remove(i);
            }
        }
        max = Math.max(max, sum);
    }

    /**
     * 【第四题】最小差值
     * 输入一个整数数组a，和一个整数k，对于a中每一个元素，必须进行一次操作（加上k或者减去k），要求是数组中所有元素执行完操作后，整个数组最大和最小值之差最小。
     * 输出这个差值。
     * 例如
     * 输入：数组a为：[1, 7, 3, 5]，k为3
     * 输出：4
     */
    public static int minLag(int[] arr, int k) {
        int length = arr.length;
        int[] dp = new int[length];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            dp[i] = arr[i] + k;
            min = Math.min(min, dp[i]);
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            int num = arr[i] - k;
            if (num >= min) max = Math.max(max, num);
            else max = Math.max(max, dp[i]);
        }
        return max - min;
    }
}
