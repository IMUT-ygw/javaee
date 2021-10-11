package com.ido.Netty;

import java.nio.ByteBuffer;

/**
 * 读取完数据以后获取到一个只读buffer，只读buffer不能put数据会报错
 */
public class BufferReadAndWrite {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);

        for (byte i = 0; i < 64; i++) {
            allocate.put(i);
        }
        //写转读
        allocate.flip();

        //得到一个只读buffer
        ByteBuffer byteBuffer = allocate.asReadOnlyBuffer();
        System.out.println(byteBuffer);//java.nio.HeapByteBufferR  结尾是R标记只读

        //只读buffer，通过hashRemaining判断是否还有数据
        while(byteBuffer.hasRemaining()){
            //get方法会将position后移
            System.out.print(byteBuffer.get() + " ");
        }

        //byteBuffer.put((byte)1); 写这段代码会报错

    }
}
