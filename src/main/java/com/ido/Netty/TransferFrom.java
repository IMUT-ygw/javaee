package com.ido.Netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 使用通道传输方法transferFrom，将目标通道的数据赋值到当前通道
 */
public class TransferFrom {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/file/file.txt");
        FileChannel channel1 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("src/main/resources/file/copy.txt");
        FileChannel channel2 = fos.getChannel();

        //文件拷贝，指定目标通道
        channel2.transferFrom(channel1,0,channel1.size());

        channel2.close();
        channel1.close();
        fis.close();
        fos.close();

    }
}
