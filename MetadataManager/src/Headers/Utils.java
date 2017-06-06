package Headers;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Utils {
    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
    public static int readInt(boolean bigEndian, int offset, int size, byte[] bytes)  {
        int ans = 0;
        if(!bigEndian)
            for (int i = 1; i <= size; i++) {
                ans = ans * 256;
                ans = ans + Utils.unsignedToBytes(bytes[offset + size - i]);
            }
        else {
            System.out.println("NOT IMPLEMENTED");
            ans = -1;
        }
        return ans;
    }

    public static void writeInt(int data, boolean bigEndian, int offset, int size, byte[] bytes)  {
        if(!bigEndian)
            for (int i = 1; i <= size; i++) {
               bytes[offset +  i - 1] =  (byte) (data%256);
               data = data / 256;
            }
        else {
            System.out.println("NOT IMPLEMENTED");
            //ans = -1;
        }
    }
}