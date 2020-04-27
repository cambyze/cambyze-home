package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mailtools.SendMail;
import org.jboss.logging.Logger;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

/**
 * Servlet implementation class SendMailServlet
 */
public class SendMailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(SendMailServlet.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SendMailServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * Send the detailed information of the imdb movie
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String json;

    SendMail sendMail = new SendMail();
    LOGGER.info("Before test sendMail");
    json = "{\"message\" : \"Mail sent\"}";
    try {
      sendMail.sendMail("cambyze@gmail.com", "cambyze");
    } catch (MailjetException e) {
      LOGGER.error(e.getMessage() + "\n" + e.getStackTrace().toString());
      json = "";
    } catch (MailjetSocketTimeoutException e) {
      LOGGER.error(e.getMessage() + "\n" + e.getStackTrace().toString());
      json = "";
    }
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
