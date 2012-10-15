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
import java.io.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
public class ServiceThread implements Callable<String> {
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
	        System.out.println("inside call");   
            DataInputStream in = new DataInputStream(socket.getInputStream());
                        FileOutputStream fos = new FileOutputStream(new File("image.jpg"));
	        BufferedReader buff = new BufferedReader(new InputStreamReader(in));
	        
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            PrintWriter buffOut = new PrintWriter(socket.getOutputStream());
            StringBuilder builder = new StringBuilder();
	        String str;
	        while (!(str = buff.readLine()).equals("")) {
		        System.out.println(str);
		        builder.append(str);
	        }
	    
	        String header = builder.toString();
	        int indexOf = header.indexOf("Content-Length:");
  	        String length = header.substring(indexOf+16);
	        //System.out.println("Length string: " + length);
	        int len = Integer.parseInt(length);
	        System.out.println("about to readint");
	        int numBytes = 0;
            int bytesRead = 0;
            byte[] buffer = new byte[len];
	
            while (bytesRead < len) {
		        numBytes = in.read(buffer, 0, len);
                
                System.out.println(numBytes);
                bytesRead += numBytes;
		        if (numBytes < 0) {
                    System.out.println("read -1");
			        break;
		        }
                bytesRead += numBytes;
                fos.write(buffer, 0, numBytes);
                System.out.println(bytesRead);
            }
            System.out.println("Done reading image");
           
            System.out.println("Calculating ocr");  
            String ocrText = Capstone.ocr("image.png");
            System.out.println("Done with tesseract");
            

            //System.out.println(ocrText);
            //String ocrText = "got dis file back doe of size: " + bytesRead;
            byte[] result = ocrText.getBytes();
            System.out.println(ocrText);
            buffOut.print(result.length);
            //out.writeInt(buffer.length);
            System.out.println("wrote length:" + result.length);
           // out.write(result);
            //buffOut.write(result);
            //buffOut.print(new String(result));
            System.out.println("wrote data");       
            in.close();
          //  out.close();
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
