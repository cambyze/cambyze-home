package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Movie;

public class MovieModel extends AbstractModel<Movie> {

  public MovieModel() {
    super(Movie.class);
  }

  public List<Movie> findByName(String keyword) {
    List<Movie> movieList = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      String sqlQuery = "select m from movie m where upper(m.name) like upper(:keyword)";
      @SuppressWarnings("unchecked")
      org.hibernate.query.Query<Movie> query = session.createQuery(sqlQuery);
      query.setParameter("keyword", "%" + keyword + "%");
      movieList = query.getResultList();
    } catch (Exception e) {
      movieList = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
    return movieList;
  }
}
