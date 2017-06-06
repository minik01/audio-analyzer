/**
 * Created by widelec on 06.06.17.
 */
import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA extends AbstractCryptographyService {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N / 2, random);
        BigInteger q = BigInteger.probablePrime(N / 2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }

    RSA() {
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    @Override
    public byte[] encodeByteArray(byte[] array) {
        byte[] ans = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ans[i] = encryptBigInteger(BigInteger.valueOf(unsignedToBytes(array[i]))).byteValue();
        }
        return ans;
    }

    @Override
    public byte[] decodeByteArray(byte[] array) {
        byte[] ans = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ans[i] = decryptBigInteger(BigInteger.valueOf(unsignedToBytes(array[i]))).byteValue();
        }
        return ans;
    }

    @Override
    public byte encodeByte(byte a) {
        return encryptBigInteger(BigInteger.valueOf(a)).byteValue();
    }

    @Override
    public byte decodeByte(byte a) {
        return decryptBigInteger(BigInteger.valueOf(a)).byteValue();
    }

    public BigInteger encryptBigInteger(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    public BigInteger decryptBigInteger(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public void setKey(long ip, long iq) {
        BigInteger p = BigInteger.valueOf(ip);
        BigInteger q = BigInteger.valueOf(iq);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }





    public static void main(String[] args) {    //test
        //int N = 50;
        RSA key = new RSA();
        key.setKey(13, 11);
        //RSAImpl2 key = new RSAImpl2(N);
        System.out.println(key);

        BigInteger message = BigInteger.valueOf(unsignedToBytes((byte) 123));

        BigInteger encrypt = key.encryptBigInteger(message);
        BigInteger decrypt = key.decryptBigInteger(encrypt);
        System.out.println("message   = " + message);
        System.out.println("encrypted = " + encrypt);
        System.out.println("decrypted = " + decrypt);
    }
}
