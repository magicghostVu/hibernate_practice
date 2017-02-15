package use.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by magic_000 on 14/02/2017.
 */
public class MainNio {
    public static void main(String[] args) {
        System.out.println("Today is valentine day");
        RandomAccessFile file = null;

        //Character c;

        try {
            file= new RandomAccessFile("C:\\Users\\magic_000\\Desktop\\data.txt", "rw");
            FileChannel fileChannel= file.getChannel();
            ByteBuffer byteBuffer= ByteBuffer.allocate(Character.BYTES);


            //write byte from channel to buffer
            int v = fileChannel.read(byteBuffer);


            byteBuffer.flip();

            byte t= byteBuffer.get();

            byte p= byteBuffer.get();

            //CharBuffer charBuffer = byteBuffer.asCharBuffer();



            System.out.println(t+" "+p);


            System.out.println(v);
        }catch (Exception e){

        }
    }
}
