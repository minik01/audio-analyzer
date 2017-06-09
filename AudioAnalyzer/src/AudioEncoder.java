import Headers.Utils;

/**
 * Created by kinga on 08.06.17.
 */
public class AudioEncoder {
    public byte[] expand(byte[] file)
    {
        WavReader wavReader = new WavReader(file);
        int len1 = wavReader.riff.dataSubchunk.get(0).getOffset();
        int len2 = wavReader.riff.getChunkSize(file) - len1;
        byte [] ans = new byte[36 + 8 +len1+len2*2];

        // copy old header
        for (int i = 0; i < 36; i++) {
            ans[i] = file[i];
        }
        wavReader.riff.setChunkSize(ans.length, ans);

        ans[36] = 'J';
        ans[37] = 'U';
        ans[38] = 'N';
        ans[39] = 'K';
        Utils.writeInt( len1,false, 36+4, 4, ans);

        for (int i = 0; i < len1; i++) {
            ans[44 + i] = file[i];
        }

        for (int i = 0; i < wavReader.riff.dataSubchunk.size(); i++)                            //dla kazdego subchunka danych
        {
            for (int j = 0; j < wavReader.riff.dataSubchunk.get(0).getNumOfChannals(); j++)     //dla kazdego kanalu
            {

            }
        }
        return ans;
    }
}
