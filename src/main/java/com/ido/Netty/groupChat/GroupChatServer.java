package com.ido.Netty.groupChat;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open(); //初始化选择器
            listenChannel = ServerSocketChannel.open(); //初始化ServerSocketChannel
            listenChannel.socket().bind(new InetSocketAddress(PORT)); //绑定端口
            listenChannel.configureBlocking(false); //设置非阻塞
            listenChannel.register(selector, SelectionKey.OP_ACCEPT); //注册进选择器
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                //监听
                int count = selector.select();
                //处理通道（大于0代表有事件处理）
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + ": 已上线!");
                        } else if (key.isConnectable()) {

                        } else if (key.isReadable()) {
                            readData(key);
                        } else if (key.isWritable()) {

                        }
                        //防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待连接.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 读数据
     * @param key 读的SelectionKey
     */
    public void readData(SelectionKey key) {
        //定义一个SocketChannel
        SocketChannel socketChannel = null;
        try {
            //取到关联的channel
            socketChannel = (SocketChannel) key.channel();
            //创建buffer，把数据读到缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            //根据count的值做处理
            if(count > 0){
                //读取到数据以后把缓冲区数据转成字符串输出
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);
                //向其他客户端转发消息（要让其他人知道）
                sendInfoToOtherClient(msg, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + ": 离线了!");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }

        }
    }

    /**
     * 转发消息
     * @param self  排除自己通道
     * @param msg  需要转发的消息
     */
    public void sendInfoToOtherClient(String msg,SocketChannel self) throws IOException {
        //遍历服务器中的所有channel,去除自己的channel
        for (SelectionKey key : selector.keys()) {
            //通过key取出channel
            SelectableChannel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != self){
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg存到buffer
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                //将数据写入通道,每个通道都会写
                dest.write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
