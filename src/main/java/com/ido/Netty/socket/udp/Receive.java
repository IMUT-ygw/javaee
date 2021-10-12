package com.ido.Netty.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 服务端 DatagramSocket
 */
public class Receive {

    public static void main(String[] args) throws Exception {
//		1、建立接收端的DatagramSocket，需要指定本端的监听端口号
        DatagramSocket ds = new DatagramSocket(9999);

        //一直监听数据
        while(true){
            //		2、建立数据包DatagramPacket
            byte[] buffer = new byte[1024*64]; //接受数据的包


            DatagramPacket dp = new DatagramPacket(buffer , buffer.length);

            //		3、调用Socket的接收方法
            ds.receive(dp);

            //4、拆封数据
            String str = new String(buffer,0,dp.getLength());
            System.out.println(str);
        }
    }
}
