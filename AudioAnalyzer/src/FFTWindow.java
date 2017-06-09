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

    public FFTWindow(String FILE_NAME, int start, int end, int wx, int wy ) throws IOException {
        //odczyt pliku
        byte[] bytes = readSmallBinaryFile(FILE_NAME);
        //odczyt wlasciwosci pliku
        WavReader wavReader = new WavReader(bytes);
        wavReader.showFileProperties();
        //normalizujemy dane
        double dataPart[] = wavReader.getNormalizedData(0, start, end);
        Complex normalizedDataArray[] = new Complex[end - start];

        if (dataPart.length >= 512) {
            double timeLine[] = new double[(end - start) / 2]; //x
            double toPrint[] = new double[(end - start) / 2]; //y
            for (int i = start; i < end; i++) {
                normalizedDataArray[i - start] = new Complex(dataPart[i - start], 0);
            }
            Complex[] fftData = FFT.fft(normalizedDataArray);
            // przypisanie czesci rzecyzwistych kolejnych wartosci FFT do tablicy wypisanie y
            double max = 0;
            //skip first value
            for (int i = 1; i < (end - start) / 2; i++) {
                toPrint[i] = Math.abs(fftData[i].re());
                if(max < toPrint[i])
                    max = toPrint[i];
                timeLine[i] = 0.5 * i * wavReader.getSampleRate() / ((end - start) / 2); //czestotliwosci
            }


            Plot2DPanel plot = new Plot2DPanel();

            plot.addLinePlot("my plot", timeLine, toPrint);
            plot.getAxis(0).setLabelText("[Hz]");
            plot.getAxis(1).setLabelText("Poziom jednostkowego widma");
            plot.setFixedBounds(1, 0, max);
            plot.setFixedBounds(0, 0, wavReader.getSampleRate() / 2 + 10);

            JFrame frame = new JFrame("Wykres widma" + FILE_NAME);
            frame.setSize(wx, wy);
            frame.setContentPane(plot);
            frame.setVisible(true);
        } else
            System.out.println("ERROR - za krotki plik dzwiekowy");

        //binary.writeSmallBinaryFile(bytes, OUTPUT_FILE_NAME);
    }

}