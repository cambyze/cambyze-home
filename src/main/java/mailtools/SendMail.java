/**
 * Send Mail
 */
package mailtools;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;


/**
 * @author Cambyze
 *
 */
public class SendMail {

  private static final Logger LOGGER = Logger.getLogger(SendMail.class);

  /*
   * Mailjet parameters
   */
  private static final String FROM = "support@cambyze.com";
  private static final String NAME = "CAMBYZE";
  private static final String USER = "55d54413da4d94386e40dce6e67a6085";
  private static final String KEY = "a1d28589a5f1ef8c3e78d4881646ad48";
  private static final String OPTIONS = "v3.1";
  private static final String TEXTPART = "Cambyze inscription";
  private static final String CUSTOMID = "AppGettingStartedTest";

  public SendMail() {
    super();
  }

  public void sendMail(String recipient, String userName, String subject, String body)
      throws MailjetException, MailjetSocketTimeoutException {

    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;

    client = new MailjetClient(USER, KEY, new ClientOptions(OPTIONS));

    request =
        new MailjetRequest(Emailv31.resource).property(
            Emailv31.MESSAGES,
            new JSONArray().put(new JSONObject()
                .put(Emailv31.Message.FROM, new JSONObject().put("Email", FROM).put("Name", NAME))
                .put(
                    Emailv31.Message.TO,
                    new JSONArray().put(new JSONObject().put("Email", recipient).put("Name",
                        userName))).put(Emailv31.Message.SUBJECT, subject)
                .put(Emailv31.Message.TEXTPART, TEXTPART).put(Emailv31.Message.HTMLPART, body)
                .put(Emailv31.Message.CUSTOMID, CUSTOMID)));
    response = client.post(request);
    LOGGER.info("SMTP response status: " + response.getStatus());
    JSONArray json = response.getData();
    if (json != null) {
      for (Object element : json) {
        LOGGER.info(element.toString());
      }
    }
  }
}
