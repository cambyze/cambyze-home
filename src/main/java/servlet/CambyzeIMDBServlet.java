package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Servlet implementation class CambyzeIMDBServlet
 */
@WebServlet("/menus2")
public class CambyzeIMDBServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(CambyzeIMDBServlet.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CambyzeIMDBServlet() {
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
    // imdb api call
    // other key: c0599c12dfmsh2c8b2086c6a73e6p1a3e4ajsn39ecc2980977
    HttpResponse<String> httpResponse = null;
    try {
      httpResponse =
          Unirest.get("https://imdb8.p.rapidapi.com/title/get-details?tconst=tt0830515")
              .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
              .header("x-rapidapi-key", "262be893ffmshd727a81662b0cc8p186d0bjsnde41f8853bec")
              .asString();
    } catch (UnirestException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      StackTraceElement[] stack = e.getStackTrace();
      if (stack != null) {
        for (StackTraceElement element : stack) {
          LOGGER.error(element.toString());
        }
      }
    }

    if (httpResponse != null) {
      LOGGER.info("Response status: " + httpResponse.getStatus());
      json = httpResponse.getBody();
      LOGGER.info("Response body: " + json);
      LOGGER.info("Response header: " + httpResponse.getHeaders().toString());

    } else {
      LOGGER.error("Response is null");
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
