package com.ido.Netty;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 使用缓冲区及通道 写文件
 */
public class WriteFile {
    public static void main(String[] args) throws IOException {
        String str = "你好袁国伟";
        //append设置为true表示追加不覆盖文件内容
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/file/file.txt",true);
        FileChannel channel = fileOutputStream.getChannel();
        //建立缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        //添加数据
        allocate.put(str.getBytes(StandardCharsets.UTF_8));
        //读写转换
        allocate.flip();
        //通道读取并写入
        channel.write(allocate);
        //关闭流
        fileOutputStream.close();
    }
}
