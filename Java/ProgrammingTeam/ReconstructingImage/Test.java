
public class Test 
{
	public static void main(String[] args)
	{
		TesseractInfo[] infoArray = new TesseractInfo[4];
		infoArray[0] = new TesseractInfo(150, 50, 610, 110, "Lorem ipsum sdkfnslkdgns\nsklgnslknskln klsdgnlksbn lknsg");
		infoArray[1] = new TesseractInfo(140, 400, 320, 110, "klsgkl klesgjklrh\nklgnklr");
		infoArray[2] = new TesseractInfo(900, 50, 250, 180, "kflgjfkg\ndfkbjfdbk\nfjfk;jgk;fj");
		infoArray[3] = new TesseractInfo(130, 230, 500, 110, "sldgjnslkgn skgnskrlhnk\ngsklgnsklgnklhn");
		
		ImageConstructor ic = new ImageConstructor(infoArray, 560);
		String s = ic.getFinalString();
		System.out.println(s);
	}
}
