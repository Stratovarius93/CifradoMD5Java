/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacifradomd5;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Aristos
 */
public class PruebaCifradoMD5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Scanner lector=new Scanner(System.in);
        System.out.println("Ingrese un texto:");
        String frase=lector.nextLine();
        System.out.println("Ingrese una contraseña:");
        String pass=cifradoMD5(lector.nextLine());
        System.out.println("Encriptado: "+ Encriptar(frase,pass));
        String encriptado=Encriptar(frase,pass);
        System.out.println("Desencriptado: "+Desencriptar(encriptado,pass));
    }
    
    private static String cifradoMD5(String texto) throws Exception{
    MessageDigest msdg= MessageDigest.getInstance("MD5");
    byte [] b=msdg.digest(texto.getBytes());
    
    int tamaño = b.length;
    StringBuffer h =new StringBuffer(tamaño);
        for (int i = 0; i < b.length; i++) {
            int u=b[i] & 255;
            if (u<16) {
                h.append("0"+Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }
    public static String Encriptar(String texto, String secretKey) {

            //String secretKey = "password123"; //llave para encriptar datos
            String base64EncryptedString = "";

            try {

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                Cipher cipher = Cipher.getInstance("DESede");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] plainTextBytes = texto.getBytes("utf-8");
                byte[] buf = cipher.doFinal(plainTextBytes);
                byte[] base64Bytes = Base64.encodeBase64(buf);
                base64EncryptedString = new String(base64Bytes);

            } catch (Exception ex) {
            }
            return base64EncryptedString;
        }
    
    public static String Desencriptar(String textoEncriptado, String secretKey) throws Exception {
 
        //String secretKey = "password123"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
}
    }



