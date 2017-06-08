import Headers.RIFF;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kinga on 30.05.17.
 */

public class WavReader {
    byte[] bytes;
    RIFF riff;

    public WavReader(byte[] raw)  {
        bytes = raw;
        riff = new RIFF(bytes);

    }

    public boolean isPowOf2(int n) {
        if (n == 2)
            return true;
        if (n < 2)
            return false;

        return isPowOf2(n / 2);
    }

    public double[] getNormalizedData(int channal, int start, int end) {
        if (!isPowOf2(end - start)) {
            System.out.println("Warning! (end-start) is not a power of 2");
        }
        double ans[] = new double[end - start];
        for (int i = 0; i < end - start; i++)
            ans[i] = riff.dataSubchunk.get(0).getChannal(channal)[i + start] / Math.pow(2.0, (double) riff.fmt.getBitsPerSample(bytes)) - 1;

        return ans;
    }

    public int getBitsPerSample() {
        return riff.fmt.getBitsPerSample(bytes);
    }

    public int getNumChannels() {
        return riff.fmt.getNumChannels(bytes);
    }

    public long getSampleRate() {
        return riff.fmt.getSampleRate(bytes);
    }

    public int getByteRate() {
        return riff.fmt.getByteRate(bytes);
    }

    public void showFileProperties() {
        System.out.println("===================================");
        System.out.println("AudioFormat: \t" + riff.fmt.getAudioFormat(bytes));
        System.out.println("BitsPerSample: \t" + riff.fmt.getBitsPerSample(bytes));
        System.out.println("NumChannels:\t" + getNumChannels());
        System.out.println("Subchunk1Size: \t" + riff.fmt.getSubchunkSize(bytes));
        System.out.println("sampleRate: \t" + riff.fmt.getSampleRate(bytes));
        System.out.println("ByteRate: \t\t" + riff.fmt.getByteRate(bytes));
        System.out.println("BlockAlign: \t" + riff.fmt.getBlockAlign(bytes));
        System.out.println("ExtraParam:\t" + riff.fmt.getExtraParam(bytes));
        System.out.println("===================================");
        System.out.println("DataSize: \t\t" + riff.dataSubchunk.get(0).getSubchunkSize(bytes));
    }
}