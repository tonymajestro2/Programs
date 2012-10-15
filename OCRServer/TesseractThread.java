import java.util.concurrent.Callable;



public class TesseractThread implements Callable<String>
{
	private String imagePath;
	
	public TesseractThread(String imagePath)
	{
		this.imagePath = imagePath;
	}
	
	
	@Override
	public String call() throws Exception {
		return Capstone.ocr(imagePath);
	}

}
