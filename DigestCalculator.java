import java.security.MessageDigest;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class DigestCalculator {
    private MessageDigest md;

    // public byte[] calculateDigest(byte[] msg, String pattern) {
    // this.md = MessageDigest.getInstance(pattern);
    // byte[] digest = md.update(msg);
    // return digest;
    // }

    public void listWriter(String path) {

    }

    // Busca se o digest do tipo procurado para o arquivo procurado já se encontra
    // na lista de arquivos. Se sim, retorna o digest que está registrado , se não,
    // retorna nulo.
    // Nome_Arq<SP>Tipo_Digest<SP>Digest_Hex[<SP>TipoDigest<SP>Digest_Hex]<EOL>
    public DigestStatus getDigestFromList(String ListName, String fileName, String pattern, String digest)
            throws IOException {
        File file = new File(ListName);
        Scanner sc = new Scanner(file);
        DigestStatus ds = DigestStatus.NOT_FOUND;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            String[] splited = line.split(" ");

            // for (int i = 0; i < splited.length; i++)
            // System.out.println("splited " + splited[i]);
            System.out.println(splited[2] + " " + digest);
            if (splited[0].equals(fileName) && splited[1].equals(pattern)) {
                if (digest.equals(splited[2])) {
                    ds = DigestStatus.OK;
                } else if (!digest.equals(splited[2])) {
                    ds = DigestStatus.NOT_OK;
                }
            } else if (splited[1].equals(pattern) && digest.equals(splited[2])) {
                ds = DigestStatus.COLISION;
            }
        }
        return ds;
    }

    public void fileWriter(String path) {

    }

    // Recebe o arquivo e retorna todo seu conteudo em uma única variável, em
    // formato de array de byte
    public byte[] getFileContent(String path) throws Exception {
        File file = new File(path);
        String text = "";
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int singleCharInt;
            char singleChar = Character.MIN_VALUE;

            while ((singleCharInt = fileInputStream.read()) != -1) {
                singleChar = (char) singleCharInt;
                text += singleChar;
            }
            System.out.println("print de dentro");
            System.out.println(text);

            return text.getBytes();
        } catch (Exception e) {
            throw e;
        }
    }

    // ***************** AUXILIAR METHOD *****************//
    private StringBuffer convertToHex(byte[] vec) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < vec.length; i++) {
            hexString.append(Integer.toHexString(0xFF & vec[i]));
        }
        return hexString;
    }
    // ***************************************************//

}