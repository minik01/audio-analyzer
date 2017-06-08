package Headers;

import Headers.Utils;

/**
 * Created by kinga on 23.05.17.
 */
public class FMT {

    private int offset;

    public String getSubchunkID(byte[] bytes) {
        String ans = "";
        for (int i = 0; i < 4; i++)
            ans += Character.toString((char) bytes[offset + i]);
        return ans;
    }

    public int getSubchunkSize(byte[] bytes)  {
        return Utils.readUInt(false, offset+4, 4, bytes);
    }

    public int getAudioFormat(byte[] bytes) {
        return Utils.readUInt(false, offset+8, 2, bytes);

    }

    public int getNumChannels(byte[] bytes) {
        return Utils.readUInt(false, offset+10, 2, bytes);

    }

    public long getSampleRate(byte[] bytes) {
        return Utils.readUInt(false, offset+12, 4, bytes);

    }

    public int getByteRate(byte[] bytes) {
        return Utils.readUInt(false, offset+16, 4, bytes);

    }


    public int getBlockAlign(byte[] bytes) {
        return Utils.readUInt(false, offset+20, 2, bytes);

    }

    public int getBitsPerSample(byte[] bytes) {
        return Utils.readUInt(false, offset+22, 2, bytes);

    }

    public String getExtraParam(byte[] bytes) {
        String ans = "";
        for (int i = 24; i < getSubchunkSize(bytes); i++)
            ans += Character.toString((char) bytes[offset + i]);
        return ans;
    }


    public FMT(int offset) {
        this.offset = offset;
    }
}