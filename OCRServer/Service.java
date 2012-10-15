

/**
 *  Service to return information about dining stats.
 *  Current version will only echo back the client input in all upper-case.
 *
 *  @author Tony Majestro
 *  @version Jan 12, 2012
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service
{
    private ServerSocket socket;
    private ExecutorService threadPool;
    private static final int NUM_THREADS = 8;
    private static int imageNum = 0;


    /**
     * Creates a new Server object.  Listens for clients on the
     * given port.
     *
     * @param port  The port used to listen for incoming clients.
     */
    public Service(int port)
    {
        try {
            socket = new ServerSocket(port);
        }
        catch (IOException e) {
            System.err.printf("Could not listen on port: %d\n", port);
            System.exit(-1);
        }

        threadPool = Executors.newFixedThreadPool(NUM_THREADS);
    }


    /**
     * Run the server, waiting for connections from clients.  If
     * the current connection lasts longer than 10 seconds, it will
     * timeout and send an error message to the client.
     */
    public void run()
    {
        try {
            while (true) {
                // Wait for incoming client
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection");
                // Submit the task to the thread pool
                Callable<String> serviceThread = new ServiceThread(clientSocket, imageNum);
                imageNum++;
                threadPool.submit(serviceThread);
            }
        }
        catch (IOException e) {
            System.err.println("Could connect with client.");
            System.exit(-1);
        }
    }

    
    /**
     * Starts the DiningServer.
     */
    public static void main(String[] args)
    {
        new Service(4444).run();
    }

}

