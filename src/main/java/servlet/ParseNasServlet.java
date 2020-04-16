package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.MovieModel;
import org.jboss.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import entities.Movie;

/**
 * Servlet implementation class ParseNasServlet
 */
public class ParseNasServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ParseNasServlet.class);
  private int numberFiles = 0;
  private String json = "";
  private String fileList = "";
  private boolean first = true;
  private Movie movie = null;
  private MovieModel movieModel = null;


  /**
   * @see HttpServlet#HttpServlet()
   */
  public ParseNasServlet() {
    super();
  }

  /**
   * Save the movies of the NAS in the database
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


    numberFiles = 0;
    json = "";
    fileList = "";
    first = true;
    movie = null;
    movieModel = new MovieModel();


    if (request != null && request.getContentLength() > 0) {

      LOGGER.info("Request size: " + request.getContentLength());

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
              movie = new Movie();
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

          public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("file")) {
              LOGGER.info("Save movie in DB: " + movie.getName());
              if (numberFiles == 1) {
                movieModel.markMovies();
              }
              movieModel.create(movie);
            }
          }

          public void characters(char ch[], int start, int length) throws SAXException {

            if (file) {
              file = false;

            } else if (path) {
              // full path of the movie
              movie.setPath(new String(ch, start, length));
              path = false;

            } else if (name) {
              // file name of the movie
              movie.setFile(new String(ch, start, length));
              name = false;

            } else if (folder) {
              String fileName = new String(ch, start, length);
              movie.setName(fileName);
              LOGGER.info("File name: " + fileName);
              if (first) {
                fileList += "\n{\"fileName\" : \"" + fileName + "\"";
                first = false;
              } else {
                fileList += "},\n{\"fileName\" : \"" + fileName + "\"";
              }
              folder = false;

            } else if (category) {
              category = false;

            } else if (shortPath) {
              // short path of the movie
              movie.setShortPath(new String(ch, start, length));
              shortPath = false;
            }
          }
        };

        saxParser.parse(request.getInputStream(), handler);

      } catch (ParserConfigurationException e) {
        LOGGER.error("ParserConfigurationException:" + e.getMessage());
      } catch (SAXException e) {
        LOGGER.error("SAXException" + e.getMessage());
      }

      json =
          "{{\"message\" : \"files treated:" + String.valueOf(numberFiles) + " \"},[" + fileList
              + "}]}";

      if (numberFiles > 0) {
        LOGGER.error("Pb when deleting no more existing movies in the NAS");
      }

    } else {
      LOGGER.info("The request is empty");
      json = "{\"message\" : \"Empty request\"}";
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
