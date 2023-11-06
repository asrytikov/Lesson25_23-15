package p1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        Scanner in = new Scanner(socketChannel, "UTF-8");
        OutputStream outputStream = Channels.newOutputStream(socketChannel);
    }
}