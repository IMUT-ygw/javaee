package com.ido.Netty;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * 使用缓冲区及通道拷贝文件
 */
public class BufferAndChannel {
    public static void main(String[] args) throws IOException {
        readAndWrite();
    }

    public static void readAndWrite() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/file/file.txt");
        FileChannel channel1 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("src/main/resources/file/bufferCopy.txt");
        FileChannel channel2 = fos.getChannel();

        //buffer初始化大小512字节
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true) {
            //假如第一次没读写完，第二次要从初始化位开始，position = 0 ，limit = 最大值（清空缓冲区）
            buffer.clear();
            //将通道的数据读取到buffer
            int read = channel1.read(buffer);//
            if (read == -1) { //读完直接break;
                break;
            }
            buffer.flip();//读写转换
            //将缓冲区信息写入到通道
            channel2.write(buffer);
        }

        fis.close();
        fos.close();
    }
}
