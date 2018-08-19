package entities;


public class MenuDTO implements java.io.Serializable {

  /**
   * Menus of a specific application
   */
  private static final long serialVersionUID = 1L;
  private Integer id;
  private Integer applicationId;
  private Integer fatherId;
  private String name;
  private String label;

  public MenuDTO() {}

  public MenuDTO(Integer id, Integer applicationId, Integer fatherId, String name, String label) {
    this.id = id;
    this.applicationId = applicationId;
    this.fatherId = fatherId;
    this.name = name;
    this.label = label;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getApplicationId() {
    return this.applicationId;
  }

  public void setApplicationId(Integer applicationId) {
    this.applicationId = applicationId;
  }

  public Integer getFatherId() {
    return fatherId;
  }

  public void setFatherId(Integer fatherId) {
    this.fatherId = fatherId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
