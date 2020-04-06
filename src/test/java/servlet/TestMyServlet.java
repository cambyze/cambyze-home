package servlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.mockito.Mockito;

public class TestMyServlet extends Mockito {

  private static final Logger LOGGER = Logger.getLogger(TestMyServlet.class);

  @Test
  public void testServlet() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new CambyzeIMDBServlet().doGet(request, response);

    LOGGER.info("Response content: " + response.getWriter().toString());

  }
}
