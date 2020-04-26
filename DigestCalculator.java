import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class DigestCalculator {
    private MessageDigest md;

    public String calculateDigest(byte[] msg, String pattern) throws NoSuchAlgorithmException {
        this.md = MessageDigest.getInstance(pattern);
        this.md.update(msg);
        byte[] digest = this.md.digest();
        return this.convertToHex(digest).toString();
    }

    public void addOrReplaceDigestOnListFile(String path, String fileName, String pattern, String digest)
            throws IOException {
        FileWriter writer = null;
        File file = new File(path);
        Scanner sc = new Scanner(file);
        List<String> temp = new ArrayList<String>();
        Boolean achou = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.out.println(line);

            String[] splited = line.split(" ");

            if (splited[0].equals(fileName)) {
                System.out.println("splited[0]  " + splited[0] + " fileName " + fileName);
                writer = new FileWriter(path);
                achou = true;
                for (int i = 1; i < splited.length - 1; i += 2) {
                    if (splited[i].equals(pattern)) {
                        System.out.println("dentro do if splited[i] == pattern");
                        splited[i + 1] = digest;
                        break;
                    }
                    System.out.println("depois do if splited[i] == pattern");

                    String[] temp2 = new String[splited.length + 2];
                    System.arraycopy(splited, 0, temp2, 0, splited.length);
                    temp2[temp2.length - 2] = pattern;
                    temp2[temp2.length - 1] = digest;
                    splited = temp2;
                }
            }
            String aux = "";
            for (int i = 0; i < splited.length; i++) {
                aux = aux.concat(splited[i] + " ");
            }
            temp.add(aux);
        }

        if (!achou) {
            System.out.println("!achou");
            writer = new FileWriter(path, true);
            writer.write(fileName + " " + pattern + " " + digest + '\n');
        } else {
            System.out.println("else do !achou");

            for (String s : temp) {
                System.out.println("s");
                System.out.println(s);

                writer.write(s + '\n');
            }
        }

        writer.close();
        sc.close();
    }

    // Busca se o digest do tipo procurado para o arquivo procurado já se encontra
    // na lista de arquivos. Se sim, retorna o digest que está registrado , se não,
    // retorna nulo.
    public DigestStatus generateDigestStatusFromList(String ListName, String fileName, String pattern, String digest)
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
        sc.close();
        return ds;
    }

    // Recebe o arquivo e retorna todo seu conteudo em uma única variável, em
    // formato de array de byte
    public byte[] getFileContent(String path) throws Exception {
        File file = new File(path);
        System.out.println(path);
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