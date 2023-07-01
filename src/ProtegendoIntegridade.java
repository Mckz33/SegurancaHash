import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProtegendoIntegridade {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String doHash(String pasta) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        StringBuilder builder = new StringBuilder();

        // Obtém o caminho da pasta
        Path diretorio = Paths.get(pasta);

        // Lista todos os arquivos na pasta
        Files.walk(diretorio).filter(Files::isRegularFile).forEach(arquivo -> {
            try {
                // Verifica se o arquivo é um arquivo TXT
                if (arquivo.toString().endsWith(".txt")) {
                    // Lê o conteúdo do arquivo
                    String conteudoArquivo = new String(Files.readAllBytes(arquivo));
                    // Calcula o hash do conteúdo do arquivo
                    md.update(conteudoArquivo.getBytes());
                    byte[] digest = md.digest();
                    String hash = bytesToHex(digest).toLowerCase();
                    builder.append(hash).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return builder.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String pasta = "C:\\Users\\Mackenzie M. Machado\\eclipse-workspace\\SegurancaHash\\diretorio"; // Atualize o caminho para a pasta desejada
        String hashes = (new ProtegendoIntegridade()).doHash(pasta);
        System.out.println(hashes);
    }
}
