package ch04;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public class SeqStack<T> implements SStack<T> {
    private Object element[];                              //存储栈数据元素的数组
    private int topi;                                       //栈顶元素下标

    public SeqStack(int size)                              //构造容量为size的空栈
    {
        this.element = new Object[Math.abs(size)];
        this.topi = -1;
    }

    public SeqStack()                                      //构造默认容量的空栈
    {
        this(64);
    }

    @Override
    public boolean isEmpty() {
        return this.topi == -1;
    }

    @Override
    public void push(T x) {

        if (x == null) return;

        if (this.topi == this.element.length - 1) {
            Object[] temp = this.element;
            this.element = new Object[temp.length * 2];
            for (int i = 0; i < temp.length; i++) {
                this.element[i] = temp[i];
            }
        }
        this.element[topi + 1] = x;
        this.topi++;
    }

    @Override
    public T pop() {
        if (this.topi == -1) return null;
        return (T) this.element[this.topi--];
    }

    @Override
    public T get() {
        return this.topi == -1 ? null : (T) this.element[this.topi];
    }

    public String toString()            //返回栈所有元素的描述字符串，形式为“(,)”，算法同顺序表
    {
        String str = "(";
        if (this.topi != -1)
            str += this.element[this.topi].toString();
        for (int i = this.topi - 1; i >= 0; i--)
            str += ", " + this.element[i].toString();
        return str + ") ";
    }

    public static void main(String args[])
    {
/*	    SeqStack<String> stack = new SeqStack<String>(20);
	    System.out.print("Push: ");
	    char ch='a';
	    for(int i=0;i<5;i++)
	    {
	        String str = (char)(ch+i)+"";
	        stack.push(str);
	        System.out.print(str+"  ");
	    }
*/
        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        System.out.print("Push: ");
        for (int i=1; i<=5; i++)
        {
            Integer intobj = new Integer(i);
            stack.push(intobj);
            System.out.print(intobj+"  ");
        }

        System.out.println("\nStack: "+stack.toString());
        System.out.print("Pop : ");
        while(!stack.isEmpty())                  //全部出栈
            System.out.print(stack.pop().toString()+"  ");
        System.out.println();
    }
}
