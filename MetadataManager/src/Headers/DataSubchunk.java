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

    public int getSubchunkSize(byte[] bytes) {
        return Utils.readInt(false, offset+4, 4, bytes);
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
        int numSamples = getSubchunkSize(bytes) / (numChannals * bitSize / 8);

        for (int i = 0; i < numChannals; i++)
            channals.add(new Integer[numSamples]);


        for (int i = 0; i < numSamples; i++) {
            for (int ch = 0; ch < numChannals; ch++) {
                int probe = 0;
                for (int bit = 0; bit < (bitSize / 8); bit++) {
                    //if(i<30)System.out.println("addr:"+ (offset + 8 + (numChannals*bitSize/8) *    i    + (ch*bitSize/8)  - bit) +"\tval:" + unsignedToBytes( bytes[offset + 8 + (numChannals*bitSize/8) *    i    + (ch*bitSize/8)  - bit]) );
                    probe = probe * 256;
                    //                    |   start   | size 1 probe in all ch  | current | current channal | current bit |
                    probe = probe + (bytes[offset + 8 + (numChannals * bitSize / 8) * i + (ch * bitSize / 8) - bit]);
                }
                //if(i<30)System.out.println(probe);
                channals.get(ch)[i] = probe;
            }
        }
    }
}