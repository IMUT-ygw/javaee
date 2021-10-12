package com.ido.Netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 引入选择器及关系梳理：
 *       当客户端连接数时，会通过ServerSocketChannel获取到一个SocketChannel
 *       把SocketChannel注册进Selector，这是通道关注事件（第二个参数），并且注册后返回一个selectionKeys
 *       selector通过select方法对通道进行监听，返回有事件发生的通道个数
 *       获取到有事件发生的通道后，可以进一步获取到selectionKeys,还可以通过这个key反向获取通道channel方法
 *       得到channel进行业务处理
 * selectionKeys有四个属性：
 *      OP_READ = 1
 *      OP_WRITE = 4
 *      OP_CONNECT = 8
 *      OP_ ACCEPT = 16
 *      Channel必须是非阻塞的，因为FileChannel不能够切换到非阻塞状态，更准确的说FileChannel没有继承SelectableChannel
 *  所以FileChannel不适合Selector（SelectableChannel抽象类 有一个 configureBlocking（boolean block））
 *  监听方法：
 *      返回值就是有多少通道已经就绪
 *       int select()：阻塞到至少有一个通道在你注册的事件上就绪了。
 *       int select(long timeout)：和select()一样，但最长阻塞时间为timeout毫秒。
 *       int selectNow()：非阻塞，只要有通道就绪就立刻返回。
 *  梳理：
 *      这是个服务端必须声明 ServerSocketChannel 并绑定端口
 *      还要声明一个selector来监听ACCEPT事件（这是个服务器端应该对接受事件感兴趣）
 *      还要将serverSocket注册到选择器中（注册前要设置为非阻塞）
 *      循环等待客户端连接while(true)
 *      通过选择器获取selectionKeys,然后对其进行迭代
 *      判断是不是选择器感兴趣的事件，服务端一般是判断是否为accept
 *      如果是则ServerSocketChannel.accept();并将连接的通道设置为非阻塞
 *      然后注册进选择器，这个通道感兴趣的内容就是读数据了，要在后面声明接受数据的buffer
 *      如果这个通道是数据通道，则将当前key强转为SocketChannel
 *      获取该通道下的的bugffer(这个buffer就是带来的空盒子)，还要把数据读到这个buffer中
 */
public class NIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //初始化serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //获取一个selector对象
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //设置通道事件（通过Selector监听Channel时对什么事件感兴趣）server监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //设置阻塞事件为1s，监听通道事件
            if(selector.select(1000) == 0){ //没有时间发生
                System.out.println("服务器等待了1s,无连接!");
                continue;
            }
            //如果有事件发生就获取到selectionKeys集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取到有事件的SelectionKey
                SelectionKey key = iterator.next();
                //根据key查看对应通道发生的事件
                if(key.isAcceptable()){ //如果是这个事件
                   //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功！socketChannel=" +socketChannel.hashCode() );
                    //把这个通道注册到选择器，并设置事件为 read,读取数据以后借助第三个参数buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if(key.isReadable()){
                    //通过key反向获取到channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //通道事件为read时，传进来一个buffer，通过attachment方法获取到该buffer
                    ByteBuffer attachment = (ByteBuffer)key.attachment();
                    //将数据读入到buffer
                    channel.read(attachment);
                    System.out.println("from 客户端：" + new String(attachment.array()));
                }

                //手动从集合中移除当前的selectionKeys,防止重复操作
                /*
                    可以通过迭代器进行元素删除，删除元素时要维护两个变量，一个是修改次数，一个是期望修改次数，
                俩者必须吻合，list.remove只修改了modCount 所以会报错，迭代器两个都修改所以可以成功。
                 */
                iterator.remove();
            }

        }


    }
}
