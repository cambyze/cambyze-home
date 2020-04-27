package commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jboss.logging.Logger;

public class Encryption {

  private static final Logger LOGGER = Logger.getLogger(Encryption.class);

  public final static String encrypt(String text) {

    String encryptedText = "";
    try {
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");
      // Add password bytes to digest
      md.update(text.getBytes());
      // Get the hash's bytes
      byte[] bytes = md.digest();
      // This bytes[] has bytes in decimal format;
      // Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      // Get complete hashed password in hex format
      encryptedText = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      StackTraceElement[] stack = e.getStackTrace();
      if (stack != null) {
        for (StackTraceElement element : stack) {
          LOGGER.error(element.toString());
        }
      }
    }

    LOGGER.info(encryptedText);

    return encryptedText;

  }
}
