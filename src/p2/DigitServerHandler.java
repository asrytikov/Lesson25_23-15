package p2;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

public class DigitServerHandler implements Runnable{

    private Socket socker;
    private int counter;

    public DigitServerHandler(Socket socker) {
        this.socker = socker;
    }

    @Override
    public void run() {
        try{
            try {
                OutputStream outputStream = socker.getOutputStream();
                PrintWriter printWriter = new PrintWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"),
                        true
                );
                while (counter < 100){
                    counter++;
                    if (counter <=10) printWriter.println(counter);
                    Thread.sleep(100);
                }
            }finally {
                socker.close();
                out.println("Closing server");
            }


        }catch (Exception exception){
            out.println("\n DigitServerHandler.run: " + exception);
        }
    }
}
