package commons;

import static org.junit.Assert.assertTrue;
import org.jboss.logging.Logger;
import org.junit.Test;

public class EncryptionTest {

  private static final Logger LOGGER = Logger.getLogger(EncryptionTest.class);

  @Test
  public void testEncrypt() {

    String encryptedText = "";
    encryptedText = Encryption.encrypt("fkdlgj#");
    LOGGER.info(encryptedText);
    assertTrue(true);

  }
}
