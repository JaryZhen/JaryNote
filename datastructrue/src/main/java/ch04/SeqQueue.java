package ch04;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public class SeqQueue<T> implements QQueue<T>{
    private Object element[];                              //存储队列数据元素的数组
    private int front, rear;
    @Override
    public boolean isEmpty() {
        return this.element.length==-1;
    }

    @Override
    public void enqueue(T x) {
        if (x==null)
            return;                                         //空对象不能入队
        if (this.front==(this.rear+1)%this.element.length) //当队列满时，扩充容量
        {
            Object[] temp = this.element;
            this.element = new Object[temp.length*2];      //重新申请一个容量更大的数组
            int j=0;
            for (int i=this.front;  i!=this.rear;  i=(i+1) % temp.length) //按照队列元素次序复制数组元素
                this.element[j++] = temp[i];
            this.front = 0;
            this.rear = j;
        }
        this.element[this.rear] = x;
        this.rear = (this.rear+1) % this.element.length;
    }

    @Override
    public T dequeue() {
        if (isEmpty())                           //若队列空返回null
            return null;
        T temp = (T)this.element[this.front];    //取得队头元素
        this.front = (this.front+1) % this.element.length;
        return temp;
    }   //顺序循环队列类，实现队列接口

    public String toString()                     //返回队列所有元素的描述字符串，形式为“(,)”，按照队列元素次序
    {
        String str="(";
        if (!isEmpty())
        {
            str += this.element[this.front].toString();
            int i=(this.front+1) % this.element.length;
            while(i!=this.rear)
            {
                str += ", "+this.element[i].toString();
                i=(i+1) % this.element.length;
            }
        }
        return str+")";
    }
}

