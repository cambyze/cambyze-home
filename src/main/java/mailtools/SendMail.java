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
  private static final String SUBJECT = "Welcome to cambyze.com";
  private static final String TEXTPART = "Cambyze inscription";
  private static final String CUSTOMID = "AppGettingStartedTest";

  public SendMail() {
    super();
  }

  public void sendMail(String recipient, String userName) throws MailjetException,
      MailjetSocketTimeoutException {

    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    client = new MailjetClient(USER, KEY, new ClientOptions(OPTIONS));

    // Construction of the HTML message
    String html =
        "<html><body><div stype=\"height: 100%; margin: 0\"><section style=\"margin-top: 20px; margin-left: 50px; margin-right: 50px; "
            + "background-color: #8eb4e3; margin-bottom: 10px;\">" + '\n';
    html +=
        "<div style=\"display: flex; flex-direction: row;\"><a  href=\"https://cambyze.com/\">" + '\n';
    html += "<img style=\"width: 400px; height: 80px;\" title=\"Cambyze\" alt=\"Cambyze\"" + '\n';
    html +=
        "src=\"https://cambyze.com/resources/images/cambyze_icon.png\" /></a></div></section>" + '\n';
    html +=
        "<div style=\"flex: 2; display: flex; margin-bottom: 10px; flex-direction: row;\">" + '\n';
    html +=
        "<div style=\"margin-left: 50px; margin-right: 50px; border: 2px solid #8eb4e3; flex: 2; display: flex; flex-direction: column; "
            + "align-items: left; justify-content: left;\">" + '\n';
    html +=
        "<div style=\"list-style-type: none; font-size: 18px; text-align: justify; margin-left: 55px; margin-right: 20px; justify-content: left; "
            + "align-items: left; text-decoration: none; color: #00008c;\">" + '\n';
    html +=
        "<p><b>Hi "
            + userName.toUpperCase()
            + ",</b></p><p><b>Welcome to the CAMBYZE community</b></p><p>The validation of your account is in progress.</p>"
            + "<p style=\"color: #fff;\">.</p>" + '\n';
    html +=
        "<p>Please reply to this mail to validate it. It will be very useful if you enter in your mail a quick list "
            + "of your web site and IT experience and a short description of the projects you'd like to implement with us</p>"
            + "<p style=\"color: #fff;\">.</p>" + '\n';
    html +=
        "<p>Our support team will contact you as soon as possible.</p></div></div></div>"
            + "<p style=\"color: #fff;\">.</p>" + '\n';
    html +=
        "<div style=\"display: flex; background-color: #8eb4e3; margin-left: 50px; margin-right: 50px; margin-bottom: 10px; border: 1px solid #8eb4e3;\">" + '\n';
    html += "<div style=\"padding: 0; margin: 0; display: flex;\">" + '\n';
    html +=
        "<div style=\"font-size: 18px; color: #fff; margin-left: 55px; margin-right: 10px;\">\u00A9 2020 CAMBYZE</div>" + '\n';
    html +=
        "<div style=\"font-size: 18px; color: #fff; margin-left: 10px; margin-right: 10px;\">" + '\n';
    html +=
        "<a style=\"color: #fff; text-decoration: none;\" href=\"https://cambyze.com/termsofservice/\" target=\"_blank\">Terms</a></div>" + '\n';
    html +=
        "<div style=\"font-size: 18px; color: #fff; margin-left: 10px; margin-right: 10px;\">" + '\n';
    html +=
        "<a style=\"color: #fff; text-decoration: none;\" href=\"mailto:support@cambyze.com\">Contact us</a></div></div></div></div></body></html>";


    request =
        new MailjetRequest(Emailv31.resource).property(
            Emailv31.MESSAGES,
            new JSONArray().put(new JSONObject()
                .put(Emailv31.Message.FROM, new JSONObject().put("Email", FROM).put("Name", NAME))
                .put(
                    Emailv31.Message.TO,
                    new JSONArray().put(new JSONObject().put("Email", recipient).put("Name",
                        userName))).put(Emailv31.Message.SUBJECT, SUBJECT)
                .put(Emailv31.Message.TEXTPART, TEXTPART).put(Emailv31.Message.HTMLPART, html)
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
