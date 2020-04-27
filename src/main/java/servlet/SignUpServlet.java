package servlet;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mailtools.MailBodies;
import mailtools.SendMail;
import org.jboss.logging.Logger;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import commons.Encryption;

/**
 * Servlet implementation class SignUpServlet Send mail to the user requesting an account and
 * another to the cambyze.com administrator
 */
public class SignUpServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(SignUpServlet.class);
  private static final String SUBJECT = "Welcome to cambyze.com";
  private static final String ADMIN_EMAIL = "support@cambyze.com";
  private static final String ADMIN_NAME = "CAMBYZE";

  /**
   * @see HttpServlet#HttpServlet()
   */

  /**
   * Send email
   * 
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String json = "";
    String username = "";
    String email = "";
    String password = "";
    boolean isMissingParameters = true;

    LOGGER.info("POST sendMailServices");

    if (request != null && request.getContentLength() > 0) {

      LOGGER.info("Body size: " + request.getContentLength());
      LOGGER.info("Body type: " + request.getContentType());

      Enumeration<String> params = request.getParameterNames();
      while (params.hasMoreElements()) {
        String key = params.nextElement();
        String value = request.getParameter(key);
        LOGGER.info("Body application/x-www-form-urlencoded: " + key);

        if ("username".compareToIgnoreCase(key) == 0) {
          username = value;
        } else if ("email".compareToIgnoreCase(key) == 0) {
          email = value;
        } else if ("password".compareToIgnoreCase(key) == 0) {
          password = Encryption.encrypt(value);
          LOGGER.info("Encrypted password:" + password);
        }
      }


      if (username != null && !username.isEmpty() && email != null && !email.isEmpty()
          && password != null && !password.isEmpty()) {
        isMissingParameters = false;
        SendMail sendMail = new SendMail();
        LOGGER.info("Before sendMail");
        try {
          sendMail.sendMail(email, username, SUBJECT, MailBodies.getSignUpBody(username));
          sendMail.sendMail(ADMIN_EMAIL, ADMIN_NAME, "Account request",
              MailBodies.getAccountRequest(username, email, password));
          json = "{\"message\" : \"Mail sent\"}";
        } catch (MailjetException e) {
          LOGGER.error(e.getMessage());
          e.printStackTrace();
          StackTraceElement[] stack = e.getStackTrace();
          if (stack != null) {
            for (StackTraceElement element : stack) {
              LOGGER.error(element.toString());
            }
          }
          json = "{\"message\" : \"Mail error " + e.getMessage() + "\"";
        } catch (MailjetSocketTimeoutException e) {
          LOGGER.error(e.getMessage());
          e.printStackTrace();
          StackTraceElement[] stack = e.getStackTrace();
          if (stack != null) {
            for (StackTraceElement element : stack) {
              LOGGER.error(element.toString());
            }
          }
          json = "{\"message\" : \"Mail error " + e.getMessage() + "\"";
        }
      } else {
        LOGGER.error("Missing parameters");
      }
    } else {
      LOGGER.error("Request is empty");
    }
    if (isMissingParameters) {
      json = "{\"message\" : \"Missing parameters\"}";
    }
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String json = "{\"message\" : \"Use POST method\"}";
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);

  }
}
