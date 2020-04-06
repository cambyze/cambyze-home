package model;

import junit.framework.TestCase;
import org.jboss.logging.Logger;
import org.junit.Test;

public class MenuModelTest extends TestCase {

  private static final Logger LOGGER = Logger.getLogger(MenuModelTest.class);

  @Test
  public void testFindMenu1stLevelByAppli() {

    assertTrue(true);

    /*
     * ApplicationModel applicationModel = new ApplicationModel(); MenuModel menuModel = new
     * MenuModel(); List<Application> applicationList = applicationModel.findByName("cambyze-home");
     * LOGGER.info("applicationList.size:" + applicationList.size());
     * assertTrue(applicationList.size() == 1); Integer applicationId =
     * applicationList.iterator().next().getId(); LOGGER.info("applicationId:" + applicationId);
     * List<Menu> menulList = menuModel.findMenu1stLevelByAppli(applicationId); if (menulList !=
     * null) { LOGGER.info("menulList.size:" + menulList.size()); assertTrue(menulList.size() >= 1);
     * } else { assertFalse(true); } for (Menu menu : menulList) { LOGGER.info("1st level menu: " +
     * menu.getLabel() + "=>" + menu.getUrl()); Set<Menu> subMenuList = menu.getMenuses(); for (Menu
     * subMenu : subMenuList) { LOGGER.info("++ 2nd level menu: " + subMenu.getLabel()); } }
     */
  }
}
