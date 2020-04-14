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
@Table(name = "movie", catalog = "cambyzedb", uniqueConstraints = @UniqueConstraint(
    columnNames = "name"))
public class Movie implements java.io.Serializable {

  /**
   * Movies stored in the Carthage NAS
   * 
   */

  // +-----------+---------------+------+-----+---------+-------+
  // | Field | Type | Null | Key | Default | Extra |
  // +-----------+---------------+------+-----+---------+-------+
  // | idmovie | int | NO | PRI | NULL | auto_increment |
  // | name | varchar(255) | NO | UNI | NULL | |
  // | file | varchar(255) | YES | | NULL | |
  // | path | varchar(1024) | YES | | NULL | |
  // | shortPath | varchar(255) | YES | | NULL | |
  // +-----------+---------------+------+-----+---------+-------+

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "idmovie", updatable = false, unique = true, nullable = false)
  private Long idmovie;

  @Column(name = "name", unique = true, nullable = false, length = 255)
  private String name;

  @Column(name = "file", length = 255)
  private String file;

  @Column(name = "shortPath", length = 1024)
  private String shortPath;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
  private Set<Category> categories = new HashSet<Category>(0);

  public Movie() {
    super();
  }

  public Movie(String name, String file, String shortPath, Set<Category> categories) {
    super();
    this.name = name;
    this.file = file;
    this.shortPath = shortPath;
    this.categories = categories;
  }

  public Long getIdmovie() {
    return this.idmovie;
  }

  public void setIdmovie(Long idmovie) {
    this.idmovie = idmovie;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFile() {
    return this.file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getShortPath() {
    return this.shortPath;
  }

  public void setShortPath(String shortPath) {
    this.shortPath = shortPath;
  }

  public Set<Category> getCategories() {
    return this.categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

}
