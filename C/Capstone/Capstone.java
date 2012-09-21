import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;


public class Capstone
{
    public static String ocr(String imageFilePath) throws Exception, IOException, FileNotFoundException
    {
        String tesseractExecutablePath = "/usr/local/bin/tesseract";
        String outputFilePath = imageFilePath.substring(0, imageFilePath.length() - 4) + "Output";
        String dictionaryPath = "words";
        
        String[] processArgs = new String[] {tesseractExecutablePath, imageFilePath, outputFilePath};
        Process process = Runtime.getRuntime().exec(processArgs);
        if (process.waitFor() != 0)
            throw new Exception("Tessaract did not finish normally");
        return validateOutput(outputFilePath, dictionaryPath);
        //FileWriter writer = new FileWriter(outputFilePath + ".txt");
        //writer.append(validatedString);
        //writer.flush();
        //writer.close();
    }
    
    private static String validateOutput(String outputFilePath, String dictionaryPath) throws IOException
    {
        Scanner dictIn = new Scanner(new File(dictionaryPath));
        ArrayDeque<String> words = new ArrayDeque<String>();
        while (dictIn.hasNextLine())
            words.add(dictIn.nextLine().toLowerCase());
        dictIn.close();
        
        BufferedReader in = new BufferedReader(new FileReader(outputFilePath + ".txt"));
//        Scanner in = new Scanner(new File(outputFilePath + ".txt"));
        StringBuilder sb = new StringBuilder();
        String str;
        while((str = in.readLine()) != null)
            sb.append(str + "\n");
        in.close();
        
        //System.out.print("Before\n----------\n" + sb.toString() + "After\n----------\n");
        char[] splitArray = new char[] {' ', '.', '?', '>', '<', '/', '\\', '(', ')', '{', '}', '[', ']', '-', ';', ':', '\'', '\"', '!', '\n'};
        StringBuilder tempWord = new StringBuilder();
        
        for (int i = 0; i < sb.length(); ++i)
        {
            if (!splitArrayContains(sb.charAt(i), splitArray))
                tempWord.append(sb.charAt(i));
            else
            {
                String word = tempWord.toString();
                if (!words.contains(word.toLowerCase()))
                {
                    int index;
                    if ((index = vCheck(words, word)) >= 0)
                        sb.replace(i - word.length() + index, i - word.length() + index + 2, "W");
                    else if ((index = hCheck(words, word)) >= 0)
                        sb.replace(i - word.length() + index, i - word.length() + index + 1, "ll");
                    else
                        sb.replace(i - word.length(), i, findClosestMatch(words, word));
                }
                tempWord.setLength(0);
            }
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }
    
    private static boolean splitArrayContains(char c, char[] splitArray)
    {
        for (Character character : splitArray)
            if (character == c)
                return true;
        return false;
    }
    
    private static String findClosestMatch(ArrayDeque<String> words, String word)
    {
        int wordLength = word.length();
        String bestWordMatch = word;
        int currConfidence = 60;
        for (String s : words)
        {
            if (s.length() == wordLength)
            {
                int count = 0;
                for (int i = 0; i < wordLength; ++i)
                    if (s.charAt(i) == word.charAt(i))
                        count++;
                int newConfidence = (count * 100) / wordLength;
                if (newConfidence > currConfidence)
                {
                    currConfidence = newConfidence;
                    bestWordMatch = s;
                }
            }
        }
        return bestWordMatch;
    }
    
    private static int vCheck(ArrayDeque<String> words, String word)
    {
        int index = word.indexOf("VV");
        while (index >= 0)
        {
            String newWord = word.substring(0, index) + "W" + word.substring(index + 2);
            if (words.contains(newWord.toLowerCase()))
                return index;
            index = word.indexOf("VV", index + 1);
        }
        return -1;
    }
    
    private static int hCheck(ArrayDeque<String> words, String word)
    {
        int index = word.indexOf("H");
        while (index >= 0)
        {
            String newWord = word.substring(0, index) + "ll" + word.substring(index + 1);
            if (words.contains(newWord.toLowerCase()))
                return index;
            index = word.indexOf("H", index + 1);
        }
        return -1;
    }
}
