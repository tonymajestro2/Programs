

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadedTest
{
    public static void main(String[] args)
    {
        for (int i = 0; i < 21; i++) {
            new Thread(new Runnable() {
                public void run() {

                    Socket socket = null;
                    PrintWriter out = null;
                    BufferedReader in = null;

                    try {
                        socket = new Socket("localhost", 4444);
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    }
                    catch (UnknownHostException e) {
                        System.err.println("Error: unrecogizable host");
                        System.exit(-1);
                    }
                    catch (IOException e) {
                        System.err.println("Error: Invalid port");
                        System.exit(-1);
                    }

                    try {
                        out.println("hello");
                        System.out.println("echo: " + in.readLine());

                        Thread.sleep(1000);

                        out.println("Done");

                        socket.close();
                    }
                    catch (Exception e) {}
                }
            }).start();
        }
    }
}
