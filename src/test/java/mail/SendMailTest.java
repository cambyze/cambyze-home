package mail;

import junit.framework.TestCase;
import mailtools.MailBodies;
import mailtools.SendMail;
import org.jboss.logging.Logger;
import org.junit.Test;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import commons.Encryption;

public class SendMailTest extends TestCase {

  private static final Logger LOGGER = Logger.getLogger(SendMailTest.class);

  @Test
  public void testSendMail() {

    SendMail sendMail = new SendMail();
    LOGGER.info("Before test sendMail");
    try {
      sendMail.sendMail("cambyze@gmail.com", "cambyze", "jUnit test", "<b>Hello mail</b>");
      sendMail.sendMail(
          "admin@cambyze.com",
          "admin",
          "jUnit test",
          MailBodies.getAccountRequest("hercule", "hercule@cat.com",
              Encryption.encrypt("Hercule123+")));
      sendMail.sendMail("thierry.nestelhut@orange.fr", "thierry", "jUnit test",
          MailBodies.getSignUpBody("thierry"));
    } catch (MailjetException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      StackTraceElement[] stack = e.getStackTrace();
      if (stack != null) {
        for (StackTraceElement element : stack) {
          LOGGER.error(element.toString());
        }
      }
      assertTrue(false);
    } catch (MailjetSocketTimeoutException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      StackTraceElement[] stack = e.getStackTrace();
      if (stack != null) {
        for (StackTraceElement element : stack) {
          LOGGER.error(element.toString());
        }
      }
      assertTrue(false);
    }
    assertTrue(true);
  }
}
