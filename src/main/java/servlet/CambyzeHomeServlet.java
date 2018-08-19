package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import entities.Menu;
import entities.MenuDTO;

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
      List<Menu> menuList = menuModel.findMenu1stLevelByAppli(applicationID);
      // Transform hibernate class into serializable object for JSON
      ArrayList<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
      for (Menu menu : menuList) {
        MenuDTO menuDTO =
            new MenuDTO(menu.getId(), menu.getApplication().getId(), null, menu.getName(),
                menu.getLabel());
        menuDTOList.add(menuDTO);
      }
      json = new Gson().toJson(menuDTOList);
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
