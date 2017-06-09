package Headers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinga on 24.05.17.
 */
public class DataSubchunk {
    private int offset;
    List<Integer[]> channals;
    int numChannals, bitSize;

    public int getOffset() {
        return offset;
    }

    public String getSubchunkID(byte[] bytes) {
        String ans = "";
        for (int i = 2; i < 6; i++)
            ans += Character.toString((char) bytes[36 + i]);
        return ans;
    }

    public int getNumOfChannals()
    {
        return channals.size();
    }
    public int getSubchunkSize(byte[] bytes) {
        return Utils.readUInt(false, offset+4, 4, bytes);
    }

    public Integer[] getChannal(int index) {
        if (channals.size() > index)
            return channals.get(index);
        else
            return null;
    }

    public DataSubchunk(int offset, byte[] bytes, int numChannals, int bitSize) {
        this.bitSize = bitSize;
        this.numChannals = numChannals;
        this.offset = offset;
        channals = new ArrayList<>();
        int baitSize = bitSize / 8;
        int numSamples = getSubchunkSize(bytes) / (numChannals * baitSize);

        for (int i = 0; i < numChannals; i++)
            channals.add(new Integer[numSamples]);

        for (int i = 0; i < numSamples; i++) {
            for (int ch = 0; ch < numChannals; ch++) {
                channals.get(ch)[i] = Utils.readUInt(false, (offset + 8 + (numChannals * baitSize) * i - baitSize ), baitSize, bytes);
                //System.out.println( i + ": " +Utils.readUInt(false, (offset + 8 + (numChannals * baitSize) * i - baitSize ), baitSize, bytes));
            }
        }


    }
}