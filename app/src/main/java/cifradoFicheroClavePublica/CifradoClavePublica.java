package cifradoFicheroClavePublica;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class CifradoClavePublica {
    /**
     * This method encrypts a String.
     * @param password The String to encrypt.
     * @return The String received encrypted.
     */
    public static String cifrarTexto(String password,String ficheroClavePublica) {
        byte[] encodedMessage = null;
        try {
            byte fileKey[] = fileReader(ficheroClavePublica);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(password.getBytes());
        } catch (Exception e) {
        }
        return byteToHex(encodedMessage);
    }

    /**
     * This method converts the byte array text received to hexadecimal String.
     * @param byteText byte array text to convert.
     * @return converted text in hexadecimal.
     */
    private static String byteToHex(byte[] byteText) {
        String hexText = "";
        for (int i = 0; i < byteText.length; i++) {
            String h = Integer.toHexString(byteText[i] & 0xFF);
            if (h.length() == 1) {
                hexText += "0";
            }
            hexText += h;
        }
        return hexText.toUpperCase();
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String path) throws IOException {
        ByteArrayOutputStream os = null;
        InputStream keyfis = CifradoClavePublica.class.getClassLoader()
                .getResourceAsStream(path);

        os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
            keyfis.close();
        }
        return os.toByteArray();
    }

}

