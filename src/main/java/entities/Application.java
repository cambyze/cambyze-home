package entities;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "application", catalog = "db", uniqueConstraints = @UniqueConstraint(
    columnNames = "name"))
public class Application implements java.io.Serializable {

  /**
   * Applications managed by cambyze.com
   */
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private String label;
  private Set<Menu> menuses = new HashSet<Menu>(0);

  public Application() {}

  public Application(String name, String label, Set<Menu> menuses) {
    this.name = name;
    this.label = label;
    this.menuses = menuses;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "name", unique = true, length = 180)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "label", length = 1024)
  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
  public Set<Menu> getMenuses() {
    return this.menuses;
  }

  public void setMenuses(Set<Menu> menuses) {
    this.menuses = menuses;
  }

}
