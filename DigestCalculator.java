import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class DigestCalculator {
    private MessageDigest md;

    // Calcula o digest da mensagem passada para o padrão especificado
    public String calculateDigest(byte[] msg, String pattern) throws NoSuchAlgorithmException {
        this.md = MessageDigest.getInstance(pattern);
        this.md.update(msg);
        byte[] digest = this.md.digest();
        return this.convertToHex(digest).toString();
    }

    // Procura na lista de arquivos em qual linha o arquivo está cadastrado.
    // Escreve no final da linha o padrão e o valor do digest correpondente
    public void addDigestOnListFile(String path, String fileName, String pattern, String digest) throws IOException {
        FileWriter writer = null;
        Scanner sc = null;
        try {
            File file = new File(path);
            sc = new Scanner(file);
            List<String> newLines = new ArrayList<String>();
            Boolean achou = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> splited = new ArrayList<String>();

                splited = Arrays.asList(line.split(" "));

                if (splited.get(0).equals(fileName)) {
                    writer = new FileWriter(path);
                    achou = true;
                    line = line.concat(" " + pattern + " " + digest);
                }
                newLines.add(line);
            }
            if (!achou) {
                writer = new FileWriter(path, true);
                writer.write(fileName + " " + pattern + " " + digest + '\n');
            } else {
                for (String s : newLines) {
                    writer.write(s + '\n');
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            writer.close();
            sc.close();
        }
    }

    // Busca se o digest do tipo procurado para o arquivo procurado já se encontra
    // na lista de arquivos. Se sim, retorna o digest que está registrado , se não,
    // retorna nulo.
    public DigestStatus generateDigestStatusFromList(String ListName, String fileName, String pattern, String digest)
            throws IOException {
        File file = null;
        Scanner sc = null;
        try {
            DigestStatus ds = DigestStatus.NOT_FOUND;
            file = new File(ListName);
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splited = line.split(" ");

                for (int i = 1; i < splited.length - 1; i += 2) {
                    if (splited[0].equals(fileName)) {
                        if (splited[i].equals(pattern)) {
                            if (digest.equals(splited[i + 1])) {
                                ds = DigestStatus.OK;
                            } else if (!digest.equals(splited[i + 1])) {
                                ds = DigestStatus.NOT_OK;
                            }
                        }
                    } else if (splited[i].equals(pattern) && digest.equals(splited[i + 1])) {
                        ds = DigestStatus.COLISION;
                    }
                }
            }
            return ds;
        } catch (Exception e) {
            throw e;
        } finally {
            sc.close();
        }
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