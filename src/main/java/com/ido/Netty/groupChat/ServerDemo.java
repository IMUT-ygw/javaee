package com.ido.Netty.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerDemo {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9999;


    public ServerDemo(){

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while(true){
                int listen = selector.select();
                if(listen > 0){
                    Set<SelectionKey> keys = selector.keys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector,SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() +": 已上线");
                        }
                        if(key.isReadable()){
                            readData(key);
                        }
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel)key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if(read > 0){
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);
                //向其他客户端转发消息（要让其他人知道）
                sendInfoToOtherClient(msg, channel);
            }

        } catch (IOException e) {
            System.out.println(channel.getRemoteAddress() + "离线了！");
            key.cancel();
            channel.close();
        }
    }

    public void sendInfoToOtherClient(String msg,SocketChannel socketChannel) throws IOException {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel = key.channel();
            if(channel instanceof SocketChannel && channel != socketChannel){
                SocketChannel dest  = (SocketChannel)channel;
                dest.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }



}
