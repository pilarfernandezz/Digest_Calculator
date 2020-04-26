import java.io.File;

public class DigestCalculatorTest {
    public static void main(String args[]) throws Exception {

        if (args == null || args.length < 3) {
            System.out.println(
                    "Por favor envie o tipo digest e os caminhos e arquivos necessários pela linha de comando");
            System.exit(1);
        }

        String Tipo_Digest = args[0];
        String Caminho_ArqListaDigest = args[1];
        String Caminho_da_Pasta_dos_Arquivos = args[2];

        File folder = new File(Caminho_da_Pasta_dos_Arquivos);
        File fileEntry = null;
        for (File f : folder.listFiles()) {
            System.out.println(f.getName().charAt(0));
            if (!f.isDirectory() && f.getName().charAt(0) != '.') {
                fileEntry = f;
                DigestCalculator dc = new DigestCalculator();
                byte[] fileContent = dc.getFileContent(fileEntry.getAbsolutePath());

                String digest = dc.calculateDigest(fileContent, Tipo_Digest);

                DigestStatus ds = dc.generateDigestStatusFromList(Caminho_ArqListaDigest, f.getName(), Tipo_Digest,
                        digest);

                if (ds != DigestStatus.OK)
                    dc.addOrReplaceDigestOnListFile(Caminho_ArqListaDigest, f.getName(), Tipo_Digest, digest);
                System.out.println(fileEntry.getName() + " " + Tipo_Digest + " " + digest + " " + ds.toString());
            }
        }
    }
}

/*
 * 1- digest foi calculado e bate com o q ja existia e só tem uma opçao - nao
 * faz nd (OK)
 * 
 * 2- digest foi calculado , nao tem no arquivo de lista - colocamos la (not
 * found)
 * 
 * 3- digest foi calculado e NAO bate com o digest que ja estava registrado para
 * o MESMO arquivo - substitui? ( not OK)
 * 
 * 4- digest foi calculado, porém já existia OUTRO arquivo com o mesmo digest -
 * (colision)
 */