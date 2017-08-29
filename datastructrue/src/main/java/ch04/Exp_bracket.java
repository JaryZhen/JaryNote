package ch04;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public class Exp_bracket {
    //判断expstr表达式中的圆括号是否匹配，若匹配，返回空串，否则返回错误信息
    public static String isValid(String expstr) {
        SeqStack<String> stack = new SeqStack<String>(expstr.length());  //顺序栈
//        LinkedStack<String> stack = new LinkedStack<String>();           //链式栈

        for (int i = 0; i < expstr.length(); i++) {
            char ch = expstr.charAt(i);
            switch (ch) {
                case '(':
                    stack.push(ch + "");               //左括号入栈
                    break;
                case ')':
                    if (stack.isEmpty() || !stack.pop().equals("("))  //遇见右括号时，出栈
                        return "期望(";              //判断出栈字符是否为左括号
            }
        }
        return (stack.isEmpty()) ? "" : "期望)";           //返回空串表示没有错误
    }

    public static String toPostfix(String expstr)          //返回expstr的后缀表达式
    {
        SeqStack<String> stack = new SeqStack<String>(expstr.length());   //创建运算符栈，顺序栈
//        SeqListStack<String> stack = new SeqListStack<String>(expstr.length()); //顺序栈
        String postfix = "";                                 //记载后缀表达式
        int i = 0;
        while (i < expstr.length()) {
            char ch = expstr.charAt(i);
            switch (ch) {
                case '+':                                  //遇到＋、－运算符，与栈顶元素比较
                case '-':
                    while (!stack.isEmpty() && !stack.get().equals("("))
                        postfix += stack.pop();
                    stack.push(ch + "");               //当前运算符入栈
                    i++;
                    break;
                case '*':                                  //遇到*、/ 运算符
                case '/':
                    while (!stack.isEmpty() && (stack.get().equals("*") || stack.get().equals("/")))
                        postfix += stack.pop();
                    stack.push(ch + "");
                    i++;
                    break;
                case '(':
                    stack.push(ch + "");               //遇到左括号，入栈
                    i++;
                    break;
                case ')':
                    String out = stack.pop();        //遇到右括号，出栈，若栈空返回null
                    while (out != null && !out.equals("("))  //判断出栈字符是否为左括号
                    {
                        postfix += out;
                        out = stack.pop();
                    }
                    i++;
                    break;
                default:
                    while (i < expstr.length() && ch >= '0' && ch <= '9') //遇到数字时，加入到后缀表达式
                    {
                        postfix += ch;
                        i++;
                        if (i < expstr.length())
                            ch = expstr.charAt(i);
                    }
                    postfix += " ";                   //添加空格作为数值之间的分隔符
            }
        }
        while (!stack.isEmpty())
            postfix += stack.pop();
        return postfix;
    }

    public static int value(String postfix)                //计算后缀表达式的值
    {
        LinkedStack<Integer> stack = new LinkedStack<Integer>();     //创建操作数栈，链式栈
//    	SinglyLinkedStack<Integer> stack = new SinglyLinkedStack<Integer>(); //链式栈
        int i = 0, result = 0;
        while (i < postfix.length())                         //逐个检查后缀表达式中的字符
        {
            char ch = postfix.charAt(i);
            if (ch >= '0' && ch <= '9')                        //遇到数字字符
            {
                result = 0;
                while (ch != ' ')                            //字符串转化为数值
                {
                    result = result * 10 + Integer.parseInt(ch + "");
                    i++;
                    ch = postfix.charAt(i);
                }
                i++;
                stack.push(new Integer(result));           //操作数入栈
            } else {
                int y = stack.pop().intValue();              //出栈两个操作数
                int x = stack.pop().intValue();
                switch (ch)                                //根据运算符分别计算
                {
                    case '+':
                        result = x + y;
                        break;
                    case '-':
                        result = x - y;
                        break;
                    case '*':
                        result = x * y;
                        break;
                    case '/':
                        result = x / y;
                        break;           //整除，除数为0时将抛出异常
                }
                stack.push(new Integer(result));           //运算结果入栈
                i++;
            }
        }
        return stack.pop().intValue();                     //返回运算结果
    }

    public static void main(String args[]) {
        String expstr = "121+10*(53-49+20)/((35-25)*2+10)+11";
        String postfix = toPostfix(expstr);
        System.out.println("expstr= " + expstr);
        System.out.println("postfix= " + postfix);
        System.out.println("value= " + value(postfix));
        String expstr2 = "(((1+2)*3+4))";
        System.out.println(expstr + "  " + isValid(expstr2));
    }
}
