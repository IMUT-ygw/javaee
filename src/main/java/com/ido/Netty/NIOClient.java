package com.ido.Netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 *  梳理：
 *      客户端使用SocketChannel 还要把这个通道设置为非阻塞
 *      设置连接信息InetSocketAddress
 *      通道连接
 *      创建一个缓冲区，添加数据
 *      通道直接读取缓冲区数据
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);

        //连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while(!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        //如果连接成功就发送数据
        String str = "你好袁国伟";
        //之前使用buffer.allocate需要指定大小，而wrap方法直接扩容到参数大小，及不良费也不会小
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer数据写入到channel
        socketChannel.write(wrap);
    }
}
