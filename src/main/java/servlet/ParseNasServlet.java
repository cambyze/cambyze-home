package servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jboss.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Servlet implementation class CambyzeIMDBServlet
 */
public class ParseNasServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ParseNasServlet.class);
  private int numberFiles = 0;


  /**
   * @see HttpServlet#HttpServlet()
   */
  public ParseNasServlet() {
    super();
  }

  /**
   * Send the detailed information of the imdb movie
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String json = "";
    String xml = null;
    numberFiles = 0;


    if (request != null && request.getContentLength() > 0) {

      LOGGER.info("Request size: " + request.getContentLength());

      try {
        byte[] xmlData = new byte[request.getContentLength()];

        // Start reading XML Request as a Stream of Bytes
        InputStream sis = request.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(sis);

        bis.read(xmlData, 0, xmlData.length);

        if (request.getCharacterEncoding() != null) {
          xml = new String(xmlData, request.getCharacterEncoding());
        } else {
          xml = new String(xmlData);
        }
        // xml and xmlData contains incomplete data
      } catch (IOException ioe) {
        LOGGER.error("IOException:" + ioe.getMessage());
      }

      LOGGER.debug("XML body: " + xml);

      SAXParserFactory factory = SAXParserFactory.newInstance();
      try {
        SAXParser saxParser = factory.newSAXParser();


        DefaultHandler handler = new DefaultHandler() {

          boolean file = false;
          boolean path = false;
          boolean name = false;
          boolean folder = false;
          boolean category = false;
          boolean shortPath = false;

          public void startElement(String uri, String localName, String qName, Attributes attributes)
              throws SAXException {
            if (qName.equalsIgnoreCase("file")) {
              file = true;
              numberFiles++;
            } else if (qName.equalsIgnoreCase("path")) {
              path = true;
            } else if (qName.equalsIgnoreCase("name")) {
              name = true;
            } else if (qName.equalsIgnoreCase("folder")) {
              folder = true;
            } else if (qName.equalsIgnoreCase("category")) {
              category = true;
            } else if (qName.equalsIgnoreCase("shortPath")) {
              shortPath = true;
            }
          }

          public void endElement(String uri, String localName, String qName) throws SAXException {}

          public void characters(char ch[], int start, int length) throws SAXException {

            if (file) {
              file = false;
            } else if (path) {
              path = false;
            } else if (name) {
              name = false;
            } else if (folder) {
              folder = false;
            } else if (category) {
              category = false;
            } else if (shortPath) {
              LOGGER.info("File name: " + new String(ch, start, length));
              shortPath = false;
            }
          }
        };

        saxParser.parse(new InputSource(new StringReader(xml)), handler);

      } catch (ParserConfigurationException e) {
        LOGGER.error("ParserConfigurationException:" + e.getMessage());
      } catch (SAXException e) {
        LOGGER.error("SAXException" + e.getMessage());
      }

      json = "{\"message\" : \"files treated:" + String.valueOf(numberFiles) + " \"}";

    } else {
      LOGGER.info("The request is empty");
      json = "{\"message\" : \"Empty request\"}";
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
    doGet(request, response);
  }
}
