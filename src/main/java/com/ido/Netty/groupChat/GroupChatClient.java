package com.ido.Netty.groupChat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            //socketChannel = socketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            socketChannel = socketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + ": 已就绪!");
        } catch (Exception e) {
                e.printStackTrace();
        } finally {

        }
    }

    //向服务端发送消息
    public void sendData(String info){
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取从服务端回复的消息
    public void readInfo(){
        try {
            int select = selector.select(2000);
            if(select > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        //如果是可读的，获取相关通道
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String string = new String(buffer.array());
                        System.out.println(string.trim());
                    }
                }
            }else{
                System.out.println("没有可用的通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();

        //启动一个线程，每隔3s读取服务端发送的数据
        new Thread(() -> {
            while(true){
                groupChatClient.readInfo();
                try {
                    TimeUnit.SECONDS.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        //发送数据到服务端
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String next = scanner.next();
            groupChatClient.sendData(next);
        }

    }

}
