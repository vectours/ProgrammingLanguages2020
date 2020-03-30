package main;

public class RecognizerTest {
    public static void main(String[] args){
        System.out.println("Hello World!");
        String testFilePath = "../TestInput/scratchFile.txt";
        Recognizer recognizer = new Recognizer(testFilePath);
        recognizer.recognize();
    }
}
