import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AudioAnalyzer {
    final static String FILE_NAME = "res/suprise.wav";
    final static String OUTPUT_FILE_NAME = "res/suprise2.wav";


    static byte[] readSmallBinaryFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllBytes(path);
    }




    public static void main(String[] args) throws Exception {

        byte[] bytes = readSmallBinaryFile(FILE_NAME);

        WavReader wavReader = new WavReader(bytes);
        wavReader.showFileProperties();
    }
}
