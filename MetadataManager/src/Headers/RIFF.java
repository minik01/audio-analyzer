package Headers;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kinga on 23.05.17.
 */
public class RIFF {

    private String chunkID;
    private long chunkSize;
    private String format;
    public FMT fmt;
    public List<DataSubchunk> dataSubchunk;

    public String getChunkID(byte[] bytes) {
        String ans = "";
        for (int i = 0; i < 4; i++)
            ans += Character.toString((char) bytes[i]);
        return ans;
    }

    public String getFormat(byte[] bytes) {
        String ans = "";
        for (int i = 8; i < 12; i++)
            ans += Character.toString((char) bytes[i]);
        return ans;
    }

    public int getChunkSize(byte[] bytes)
    {
        return Utils.readUInt(false,4,4,bytes);
    }

    public void setChunkSize(int newChunkSize ,byte[] bytes)
    {
        Utils.writeInt(newChunkSize,false,4, 4, bytes);
    }

    public RIFF(byte[] bytes) {
        //chunkID = "";
        dataSubchunk = new ArrayList<DataSubchunk>();
        chunkID = getChunkID(bytes);
        chunkSize = getChunkSize(bytes);
        format = getFormat(bytes);

        for (int i = 12; i < chunkSize; ) {
            Unknown unknown_header = new Unknown(i);
            String subchunkID = unknown_header.getSubchunkID(bytes).toLowerCase();
            System.out.println(i + ": found " + subchunkID);

            if (subchunkID.contains("fmt")) {
                fmt = new FMT(i);
                //System.out.println("Found FMT:" + i);
            }

            if (subchunkID.contains("data")) {
                dataSubchunk.add(new DataSubchunk(i, bytes, fmt.getNumChannels(bytes), fmt.getBitsPerSample(bytes)));
                //System.out.println("Found DATA:" + i);
            }
            try {
                i = i + unknown_header.getSubchunkSize(bytes) + 8;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // +8, bo 4 bajty na nazwe i 4 na dlugoscc bloku
        }
    }
}