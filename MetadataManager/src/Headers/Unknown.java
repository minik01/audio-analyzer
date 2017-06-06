package Headers;

public class Unknown {
    private int offset;


    public String getSubchunkID(byte[] bytes) {
        String ans = "";
        for (int i = 0; i < 4; i++)
            ans += Character.toString((char) bytes[offset + i]);
        return ans;
    }

    public int getSubchunkSize(byte[] bytes)   {
        return Utils.readInt(false, offset+4, 4, bytes);
    }

    public Unknown(int offset) {
        this.offset = offset;
    }
}
