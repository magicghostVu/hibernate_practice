package socket.use.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by magic_000 on 14/02/2017.
 */
public class ServerSocket {

    public static int[] processByteBuff(ByteBuffer bf){

        String data= new String(bf.array());

        String[] arr= data.split(" ");

        ArrayList<Integer> arrayData= new ArrayList<>();

        for (String s: arr){
            arrayData.add(Integer.parseInt(s));
        }

        int res[]= new int[2];

        res[0]= Collections.min(arrayData);
        res[1]= Collections.max(arrayData);


        return res;
    }
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8088);

        serverSocketChannel.bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false);

        //int ops = serverSocketChannel.validOps();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();

            Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();

            while (selectionKeyIterator.hasNext()) {


                SelectionKey myKey = selectionKeyIterator.next();
                if (myKey.isAcceptable()) {
                    ServerSocketChannel ssChannel= (ServerSocketChannel) myKey.channel();

                    SocketChannel socketChannelClient = serverSocketChannel.accept();
                    socketChannelClient.configureBlocking(false);
                    socketChannelClient.register(selector, SelectionKey.OP_READ );
                    System.out.println("Client " + socketChannelClient.getLocalAddress() + " connected");
                }
                if (myKey.isReadable()) {

                    SocketChannel socketChannelClient = (SocketChannel) myKey.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    socketChannelClient.read(byteBuffer);


                    //String msg= new String(byteBuffer.array());
                    //System.out.println(msg);

                    /*int minMax[]= processByteBuff(byteBuffer);

                    System.out.println(minMax[0]+" "+ minMax[1]);*/

                    byteBuffer.flip();

                    String msgFromServer="Message received";

                    byte[] raw= msgFromServer.getBytes();

                    ByteBuffer bf= ByteBuffer.wrap(raw);

                    socketChannelClient.write(bf);
                    //socketChannelClient.close();
                    //break;

                }

                selectionKeyIterator.remove();
            }


        }



    }

}

