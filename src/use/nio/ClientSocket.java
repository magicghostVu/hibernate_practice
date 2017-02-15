package use.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by magic_000 on 14/02/2017.
 */
public class ClientSocket {
    public static void main(String[] args) throws IOException, InterruptedException {


        Selector selector = Selector.open();


        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8088);
        SocketChannel socketChannelClient = SocketChannel.open();
        socketChannelClient.configureBlocking(false);
        socketChannelClient.connect(address);


        int operation = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;


        socketChannelClient.register(selector, operation);

        boolean done = false;

        boolean doneSend = false;

        while (true) {

            if (done == true) {
                break;
            }

            selector.select();

            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();

            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                selectionKeyIterator.remove();


                if (key.isConnectable()) {

                    System.out.println("finish connect");
                    SocketChannel channel = (SocketChannel) key.channel();

                    //key of the problem
                    channel.finishConnect();
                }
                // có tin từ server
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();

                    ByteBuffer bf = ByteBuffer.allocate(1024);

                    sc.read(bf);
                    bf.flip();
                    String msgFromServer = new String(bf.array());

                    System.out.println(msgFromServer);


                }


                // kênh có thể gửi
                if (key.isWritable()) {


                    if (doneSend == false) {


                        System.out.println("Gửi số lên server");
                        SocketChannel sc = (SocketChannel) key.channel();

                        int[] data = {3, 6, 78, 3, 21, 9, 0, 4, 100};


                        ByteBuffer bf= ByteBuffer.allocate(1024) ;


                        StringBuilder builderData= new StringBuilder();

                        for(int i=0; i<data.length;++i){
                            if(i==data.length-1){
                                builderData.append(Integer.toString(data[i]));
                            }else{
                                builderData.append(Integer.toString(data[i]));
                                builderData.append(" ");
                            }
                        }


                        String msg=builderData.toString();

                        //data.

                        //String msg=data.toString();
                        //bf = ByteBuffer.wrap(msg.getBytes());



                        bf.putInt(15);

                        sc.write(bf);

                        doneSend=true;

                    }
                    //Thread.sleep(1000);

                    //System.out.println("Đã gửi đi");

                }

            }

        }


        socketChannelClient.close();
        /*System.out.println("Connected to 127.0.0.1:8088");

        String message = "Dữ liệu ở đây";

        byte[] rawbyte = message.getBytes();

        ByteBuffer byteBuffer = ByteBuffer.wrap(rawbyte);


        socketChannelClient.write(byteBuffer);

        byteBuffer.clear();

        //socketChannel.close();

        System.out.println("Message sent");*/


    }
}
