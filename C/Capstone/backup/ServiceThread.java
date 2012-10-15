/**
 *  The ServiceThread class represents a single thread in the DiningService
 *  process.
 *
 *  Each thread currently establishes a TCP connection with the client
 *  using the provided Socket.  It then reads the line sent from the client
 *  and returns the same line in all upper-case.
 *
 *  @author Tony Majestro
 *  @version Jan 12, 2012
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServiceThread implements Callable<String>
{
    private Socket socket;


    /**
     * Creates a new ServiceThread object with the specified Socket.
     * @param Socket  the Socket used to communicate with the client
     */
    public ServiceThread(Socket socket)
    {
        this.socket = socket;
    }


    /**
     * Reads the input provided by the client over the Socket and echos
     * back the same line in all upper-case letters.
     */
    public String call()
    {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            FileOutputStream fos = new FileOutputStream(new File("image.png"));
            int len = in.readInt();
            int numBytes = 0;
            int bytesRead = 0;
            byte[] buffer = new byte[len];
            while (bytesRead < len) {
            	numBytes = in.read(buffer);
             	fos.write(buffer, 0, numBytes);
            	bytesRead += numBytes;
            } 
            
            System.out.println("Done reading image");
            
            String ocrText = Capstone.ocr("image.png");
//	    String ocrText = "got dis file back doe of size: " + bytesRead;
            buffer = ocrText.getBytes();
            out.writeInt(buffer.length);
            out.write(buffer);
            
            in.close();
            out.close();
            fos.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }

        return "Completed";
    }
}
