

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadedTest
{
    public static void main(String[] args)
    {
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                public void run() {

                	Socket socket = null;
                    DataOutputStream out = null;
                    DataInputStream in = null;

                    try {
                        socket = new Socket("ec2-23-22-156-166.compute-1.amazonaws.com", 4444);
                        out = new DataOutputStream(socket.getOutputStream());
                        in = new DataInputStream(socket.getInputStream());
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
                    	File image = new File("text2.png");
                        int len = (int)image.length();
                        //System.out.println(image.length());
                        FileInputStream fis = new FileInputStream(image);
                        byte[] bytes = new byte[len];
                        fis.read(bytes);
                        out.writeInt(len);
                        out.write(bytes, 0, len);
                        
                        
                        int numBytes = 0;
                        int bytesRead = 0;
                        byte[] buffer = new byte[len];
                        len = in.readInt();
                        while (bytesRead < len) {
                        	numBytes = in.read(buffer);
                        	bytesRead += numBytes;
                        }
                        
                        System.out.println(new String(buffer));
                        
                        out.flush();
                        out.close();
                        fis.close();
                        socket.close();
                    }
                    catch (Exception e) {}
                }
            }).start();
        }
    }
}
