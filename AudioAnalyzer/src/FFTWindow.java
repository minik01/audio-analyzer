import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kinga on 30.05.17.
 */
public class FFTWindow {
    final static String OUTPUT_FILE_NAME = "res/suprise2.wav";

    byte[] readSmallBinaryFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllBytes(path);
    }

    public FFTWindow(String FILE_NAME, int start, int end) throws IOException {

        byte[] bytes = readSmallBinaryFile(FILE_NAME);

        WavReader wavReader = new WavReader(bytes);
        wavReader.showFileProperties();

        double dataPart[] = wavReader.getNormalizedData(0, start, end);
        Complex normalizedDataArray[] = new Complex[end - start];

        if (dataPart.length >= 1024) {
            double timeLine[] = new double[(end - start) / 2];
            double toPrint[] = new double[(end - start) / 2];
            for (int i = start; i < end; i++) {
                normalizedDataArray[i - start] = new Complex(dataPart[i - start], 0);
                //System.out.println(i + ":\t" );

            }
            Complex[] fftData = FFT.fft(normalizedDataArray);

            double max = 0;
            //skip first value
            for (int i = 1; i < (end - start) / 2; i++) {
                toPrint[i] = Math.abs(fftData[i].re());
                if(max < toPrint[i])
                    max = toPrint[i];
                timeLine[i] = 0.5 * i * wavReader.getSampleRate() / ((end - start) / 2);
            }


            Plot2DPanel plot = new Plot2DPanel();

            plot.addLinePlot("my plot", timeLine, toPrint);
            plot.getAxis(0).setLabelText("[Hz]");
            plot.getAxis(1).setLabelText("Poziom jednostkowego widma");
            plot.setFixedBounds(1, 0, max);
            plot.setFixedBounds(0, 0, wavReader.getSampleRate() / 2 + 10);

            JFrame frame = new JFrame("Wykres widma" + FILE_NAME);
            frame.setSize(800, 600);
            frame.setContentPane(plot);
            frame.setVisible(true);
        } else
            System.out.println("ERROR - za krotki plik dzwiekowy");

        //binary.writeSmallBinaryFile(bytes, OUTPUT_FILE_NAME);
    }

    public static void main(String[] args) {
        try {
            FFTWindow g = new FFTWindow("res/11_-_Incident_on_the_Other_Side_of_the_Wall.wav", 2048, 2048 * 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}