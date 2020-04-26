public class DigestCalculatorTest {
    public static void main(String args[]) throws Exception {
        // if (args == null || args.length < 3) {
        // System.out.println(
        // "Por favor envie o tipo digest e os caminhos e arquivos necessários pela
        // linha de comando");
        // System.exit(1);
        // }

        // String Tipo_Digest = args[0];
        // String Caminho_ArqListaDiges = args[1];
        // String Caminho_da_Pasta_dos_Arquivos = args[2];

        // switch (Tipo_Digest) {
        // case "MD5withRSA":
        // Tipo_Digest = "MD5";
        // break;
        // case "SHA1withRSA":
        // Tipo_Digest = "SHA-1";
        // break;
        // case "SHA256withRSA":
        // Tipo_Digest = "SHA-256";
        // break;
        // case "SHA512withRSA":
        // Tipo_Digest = "SHA-512";
        // break;
        // default:
        // System.out.println("Tipo de digest inválido.");
        // System.exit(1);
        // }

        DigestCalculator dc = new DigestCalculator();
        // byte[] b =
        // dc.getFileContent("/Users/pilarfernandez/Documents/INF1416/INF1416-G1 Trab
        // 3/teste.txt");

        // System.out.println("print de fora");
        // for (int i = 0; i < b.length; i++) {
        // System.out.println(b[i]);
        // }

        // DigestStatus ds =
        // dc.getDigestFromList("/Users/pilarfernandez/Documents/INF1416/INF1416-G1 Trab
        // 3/lista.txt",
        // "arq1", "sha1", "asjhkfhkfd");

        dc.listWriter("/Users/pilarfernandez/Documents/INF1416/INF1416-G1 Trab 3/lista.txt", "arq4", "sha1",
                "asjhkfhkfd".getBytes());
        // System.out.println(ds);

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