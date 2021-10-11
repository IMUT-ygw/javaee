package com.ido.Netty;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  修改文件内容
 * 可以长文件直接在内存（堆外）修改，不需要对文件进行拷贝
 */
public class MapperdByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("src/main/resources/file/randomAccessFile.txt", "rw");
        //获取对应文件通道
        FileChannel channel = rw.getChannel();

        /*
               参数：
                   1：读写模式
                   2：可以直接修改的起始位置
                   3：映射到内存的大小，可修改的范围就是 0-5（包左不包右）
                   4：最终实现是：DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        map.put(0,(byte)'H');
        map.put(3,(byte)'Y');

        rw.close();

    }

}
