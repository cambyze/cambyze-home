package servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;



/**
 * Servlet implementation class CambyzeIMDBServlet
 */
public class XMLtoPDFServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(XMLtoPDFServlet.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public XMLtoPDFServlet() {
    super();
  }

  /**
   * Send the detailed information of the imdb movie
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String xml = null;

    if (request != null && request.getContentLength() > 0) {
      LOGGER.info("Request size: " + request.getContentLength());
    }

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

    if (xml == null || xml.trim().length() == 0) {
      xml = "<message>An empty XML was sent</message>";
    }
    LOGGER.debug("XML body: " + xml);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
    Document doc = new Document(pdfDoc);
    doc.add(new Paragraph(xml));
    doc.close();

    // setting some response headers
    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    // setting the content type
    response.setContentType("application/pdf");
    // the content length
    response.setContentLength(baos.size());
    // write ByteArrayOutputStream to the ServletOutputStream
    ServletOutputStream os;

    os = response.getOutputStream();
    baos.writeTo(os);
    os.flush();
    os.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
