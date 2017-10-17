package stack_queue;

import java.util.Stack;
/**
 * Created by Jary on 2017/9/12 0012.
 * /*
 * 输入两个整数序列，第�?个序列表示栈的压入顺序，
 * 请判断第二个序列是否为该栈的弹出顺序�?
 * 假设压入栈的�?有数字均不相等�??
 * 例如序列1,2,3,4,5是某栈的压入顺序�?
 * 序列4�?5,3,2,1是该压栈序列对应的一个弹出序列，
 * �?4,3,5,1,2就不可能是该压栈序列的弹出序列�??
 * */

public class S_pop {

    public static boolean IsPopOrder(int [] pushA,int [] popA)
    {
        if(pushA.length<=0 || popA.length<=0 )
        {
            return false;
        }
        int j=0;
        Stack<Integer> help=new Stack<Integer>();
        for(int i=0;i<pushA.length;i++)
        {
            help.push(pushA[i]);
            if(pushA[i] == popA[j])
            {
                int tempj = j;
                if(tempj==popA.length-1)
                {
                    return true;
                }
                j++;
                help.pop();
            }
        }
        while(!help.isEmpty())
        {
            if(help.pop()!=popA[j++])
            {
                return false;
            }
        }
        return true;
    }

        public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(3);
        stack.push(6);
        stack.push(1);
        stack.push(9);
        stack.push(4);

        for (Integer a : stack) {
            System.out.print(a + " ");
        }

            int [] pushA = {1,2,3,4,5};
            int [] popA = {4,5,3,2,1};
            System.out.println(IsPopOrder(pushA,popA));
    }
}
