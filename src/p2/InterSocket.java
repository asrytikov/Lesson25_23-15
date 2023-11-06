package p2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class InterSocket {

    private Scanner scanner;
    private DigitServer digitServer;
    private Thread connectThread;

    public InterSocket() {

        connectThread = new Thread(
                () -> {
                try{
                    connInter();
                }catch (IOException exception){
                    System.out.println("InterSocket.connInter: " + exception);
                }
            }
        );

        /*connectThread = new Thread(
                () -> {
                    try{
                        connBlock();
                    }catch (IOException exception){
                        System.out.println("InterSocket.connBlock: " + exception);
                    }
                }
        );*/

        connectThread.start();
        digitServer = new DigitServer();
        new Thread(digitServer).start();

        Timer timer = new Timer(1500, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                connectThread.interrupt();
            }
        });

       timer.start();

    }


    public void connInter() throws IOException{
        System.out.println("Inter: \n");
        try (SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("localhost", 9999))){
            scanner = new Scanner(socketChannel, "UTF-8");
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("Reading ");
                if (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            }
        }finally {
            System.out.println("Channel close");
        }
    }

    public void connBlock() throws IOException {
        System.out.println("Block: \n");
        try (Socket socket = new Socket("localhost", 9999)) {
            scanner = new Scanner(socket.getInputStream(), "UTF-8");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Reading ");
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            }
        }finally {
            System.out.println("Channel close");
        }
    }
}
