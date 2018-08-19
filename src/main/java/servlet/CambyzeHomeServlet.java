package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ApplicationModel;
import model.MenuModel;
import com.google.gson.Gson;
import entities.Application;

/**
 * Servlet implementation class CambyzeHomeServlet
 */
@WebServlet("/menus")
public class CambyzeHomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CambyzeHomeServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Send the list of first level menus
    String json;
    ApplicationModel applicationModel = new ApplicationModel();
    List<Application> applicationList = applicationModel.findByName("cambyze-home");
    if (applicationList != null && applicationList.size() == 1) {
      Integer applicationID = applicationList.iterator().next().getId();
      MenuModel menuModel = new MenuModel();
      json = new Gson().toJson(menuModel.findMenu1stLevelByAppli(applicationID));
    } else {
      json = "";;
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
