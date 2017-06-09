import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AudioAnalyzer {
    final static String FILE_NAME_1 = "res/suprise.wav";
    final static String FILE_NAME_2 = "res/11_-_Incident_on_the_Other_Side_of_the_Wall.wav";
    final static String OUTPUT_FILE_NAME = "res/suprise2.wav";

    public static void main(String[] args) throws Exception {

        try {
            FFTWindow g = new FFTWindow(FILE_NAME_2, 1024, 1024 * 9 , 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
