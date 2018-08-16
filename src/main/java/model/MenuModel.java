package model;

import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import entities.Menu;

public class MenuModel extends AbstractModel<Menu> {

  private static final Logger LOGGER = Logger.getLogger(MenuModel.class);

  public MenuModel() {
    super(Menu.class);
  }

  public List<Menu> findMenu1stLevelByAppli(Integer applicationId) {
    ArrayList<Menu> menuList = new ArrayList<Menu>();
    try {
      List<Menu> allMenuList = this.findAll();
      for (Menu menu : allMenuList) {
        // find menu at 1st level = parent menu is null
        if (menu != null && menu.getMenu() == null) {
          menuList.add(menu);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error in findMenu1stLevelByAppli: " + e.getMessage());
      menuList = null;
    }
    return menuList;
  }
}
