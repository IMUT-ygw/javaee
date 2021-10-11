package com.ido.Netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *  Scattering:将数据写入到buffer时，可以采用buffer数组，依次写入 【分散】
 *  Gathering: 从buffer读取数据时，可以采用buffer数组，依次读取 【聚集】
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        //创建serverSocketChannel 和 SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定serverSocketChannel
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建缓冲区数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel accept = serverSocketChannel.accept();
        //设置读取数据最大值
        int temp = 8;
        //循环读取
        while (true){
            int byteRead = 0;
            while (byteRead < temp){
                long read = accept.read(byteBuffers);
                byteRead += read;//累计读取的字节数
                System.out.println("byteRead="+byteRead);
                //查看当前position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> {
                   return  "position" + buffer.position() + ", limit=" + buffer.limit();
                }).forEach(System.out::println);
            }

            //读写转换
            Arrays.asList(byteBuffers).stream().map(buffer -> buffer.flip());

            //写
            long byteWrite = 0;
            while(byteWrite < temp){
                long write = accept.write(byteBuffers);
                byteWrite += write;
            }

            //清空
            Arrays.asList(byteBuffers).stream().map(buffer -> buffer.clear());


        }
    }

}
