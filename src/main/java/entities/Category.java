package entities;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category", catalog = "cambyzedb")
public class Category implements java.io.Serializable {

  /**
   * Categories of a NAS movie
   */

  // table category
  // +------------+--------------+------+-----+---------+----------------+
  // | Field | Type | Null | Key | Default | Extra |
  // +------------+--------------+------+-----+---------+----------------+
  // | idcategory | int | NO | PRI | NULL | auto_increment |
  // | idmovie | int | NO | MUL | NULL | |
  // | name | varchar(255) | NO | MUL | NULL | |
  // +------------+--------------+------+-----+---------+----------------+

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "idcategory", updatable = false, unique = true, nullable = false)
  private Long idcategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idmovie", nullable = false)
  private Movie movie;

  @Column(name = "name", nullable = false, length = 255)
  private String name;


  public Category() {
    super();
  }

  public Category(Movie movie, String name) {
    super();
    this.movie = movie;
    this.name = name;
  }

  public Long getIdcategory() {
    return idcategory;
  }

  public void setIdcategory(Long idcategory) {
    this.idcategory = idcategory;
  }

  public Movie getMovie() {
    return this.movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
