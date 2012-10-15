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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServiceThread implements Callable<String>
{
    private Socket socket;
    private int imageNum;
    private final int NUM_THREADS = 8;


    /**
     * Creates a new ServiceThread object with the specified Socket.
     * @param Socket  the Socket used to communicate with the client
     */
    public ServiceThread(Socket socket, int imageNum)
    {
        this.socket = socket;
        this.imageNum = imageNum;
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
            
            File image = new File(String.format("image%d.png", imageNum));
            FileOutputStream fos = new FileOutputStream(image);
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
            
            String imgName = image.getName();
            String imgName2 = imgName.substring(0, imgName.length() - 3);
            String[] splitImage = Split.split(imgName2 + "_output.txt", imgName);
            String ocrText = callTesseract(splitImage);
            buffer = ocrText.getBytes();
            out.writeInt(buffer.length);
            out.write(buffer);
            
            
            image.delete();
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
    
    
    
    private static String[] splitImage(String img)
    {
    	return new String[] {"t1.jpg", "t2.jpg", "t3.jpg", "t4.jpg" };
    }
    
    
    private String callTesseract(String[] imagePaths) throws InterruptedException, ExecutionException
    {
    	ExecutorService threadpool = Executors.newFixedThreadPool(NUM_THREADS);
    	List<Future<String>> ocrStrings = new ArrayList<Future<String>>();
    	
    	// Spawn a tesseract thread for each chunk of the original image
    	for (String imagePath : imagePaths) {
    		Callable<String> tesseractThread = new TesseractThread(imagePath);
    		ocrStrings.add(threadpool.submit(tesseractThread));
    	}
    	
    	// Rebuild ocr text
    	StringBuilder ocrText = new StringBuilder();
    	for (Future<String> future : ocrStrings) {
    		try {
    			ocrText.append(future.get());
    		}
    		catch (InterruptedException e) {
    			e.printStackTrace();
    			return String.format("Error parsing image: %s", e.getMessage());
    		}
    		catch (ExecutionException e) {
    			e.printStackTrace();
    			return String.format("Error parsing image: %s", e.getMessage());
    		}
    	}
    	
    	for (String filepath : imagePaths) {
    		new File(filepath).delete();
    	}
    	
    	return ocrText.toString();
    }
}
