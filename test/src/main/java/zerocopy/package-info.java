/**
 * Created by Jary on 2018/1/31 0031.
 */
package zerocopy;

/**

  0.https://yq.aliyun.com/articles/43593?spm=a2c4e.11153940.blogrightarea43581.13.7f64def1cJj2xc
  1.read() 调用导致一次从user mode到kernel mode的上下文切换。在内部调用了sys_read() 来从文件中读取data。第一次copy由DMA (direct memory access)完成，将文件内容从disk读出，存储在kernel的buffer中。
  2.然后请求的数据被copy到user buffer中，此时read()成功返回。调用的返回触发了第二次context switch: 从kernel到user。至此，数据存储在user的buffer中。
  3.send() Socket call 带来了第三次context switch，这次是从user mode到kernel mode。同时，也发生了第三次copy：把data放到了kernel adress space中。当然，这次的kernel buffer和第一步的buffer是不同的buffer。
  4.最终 send() system call 返回了，同时也造成了第四次context switch。同时第四次copy发生，DMA egine将data从kernel buffer拷贝到protocol engine中。第四次copy是独立而且异步的。

  1.transferTo()方法使得文件内容被copy到了kernel buffer，这一动作由DMA engine完成。
  2.没有data被copy到socket buffer。取而代之的是socket buffer被追加了一些descriptor的信息，包括data的位置和长度。然后DMA engine直接把data从kernel buffer传输到protocol engine，这样就消除了唯一的一次需要占用CPU的拷贝操作。
 */

/**
 1.transferTo()方法使得文件内容被copy到了kernel buffer，这一动作由DMA engine完成。
 2.没有data被copy到socket buffer。取而代之的是socket buffer被追加了一些descriptor的信息，包括data的位置和长度。然后DMA engine直接把data从kernel buffer传输到protocol engine，这样就消除了唯一的一次需要占用CPU的拷贝操作。
 */