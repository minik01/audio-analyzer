import java.math.BigInteger;

/**
 * Created by kinga on 06.06.17.
 */
public abstract class AbstractCryptographyService {
    //kodowanie tablic typu byte
    public abstract byte[] encodeByteArray(byte[] array);
    public abstract byte[] decodeByteArray(byte[] array);
    //kodowanie pojedynczej zmienney typu byte
    public abstract byte encodeByte(byte a);
    public abstract byte decodeByte(byte a);
    //kodowanie zmiennej typu BigInteger
    public abstract BigInteger encryptBigInteger(BigInteger message);
    public abstract BigInteger decryptBigInteger(BigInteger encrypted);
    //
    public abstract void setKey(long p, long q);
}