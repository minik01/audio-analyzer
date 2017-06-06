import java.math.BigInteger;

/**
 * Created by widelec on 06.06.17.
 */
public abstract class AbstractCryptographyService {
    public abstract byte[] encodeByteArray(byte[] array);
    public abstract byte[] decodeByteArray(byte[] array);
    public abstract byte encodeByte(byte a);
    public abstract byte decodeByte(byte a);
    public abstract BigInteger encryptBigInteger(BigInteger message);
    public abstract BigInteger decryptBigInteger(BigInteger encrypted);
    public abstract void setKey(long p, long q);
}