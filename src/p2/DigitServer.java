package p2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DigitServer implements Runnable{

    @Override
    public void run() {
        try (ServerSocket socket = new ServerSocket(9999)){
            while (true){
                Socket socketIn = socket.accept();
                Runnable r = new DigitServerHandler(socketIn);
                Thread thread = new Thread(r);
                thread.start();
            }
        }catch (IOException exception){
            System.out.println("\n DigitServer.run: " + exception);
        }
    }
}
