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
	private String url;
	private String icon;

	public MenuDTO() {
	}

	public MenuDTO(Integer id, Integer applicationId, Integer fatherId,
			String name, String label, String icon, String url) {
		this.id = id;
		this.applicationId = applicationId;
		this.fatherId = fatherId;
		this.name = name;
		this.label = label;
		this.icon = icon;
		this.setUrl(url);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
