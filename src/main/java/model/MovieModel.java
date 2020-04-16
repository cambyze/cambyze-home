package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import entities.Movie;

public class MovieModel extends AbstractModel<Movie> {

  private static final Logger LOGGER = Logger.getLogger(MovieModel.class);

  public MovieModel() {
    super(Movie.class);
  }


  public List<Movie> searchMoviesWithName(String keyword) {
    List<Movie> movieList = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      String sqlQuery = "from Movie m where m.name = :keyword";
      @SuppressWarnings("unchecked")
      org.hibernate.query.Query<Movie> query = session.createQuery(sqlQuery);
      query.setParameter("keyword", keyword);
      movieList = query.getResultList();
      if (movieList != null) {
        LOGGER.info("Number of movies with the name " + keyword + " = " + movieList.size());
      } else {
        LOGGER.info("Number of movies with the name " + keyword + " = 0");
      }
    } catch (Exception e) {
      movieList = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return movieList;
  }


  public boolean createOrReplace(Movie movie) {
    boolean isOK = false;
    if (movie != null) {
      if (movie.getName().length() > 0) {
        if (this.searchMoviesWithName(movie.getName()).isEmpty()) {
          this.create(movie);
        } else {
          this.update(movie);
        }
      }
      isOK = true;
    }
    return isOK;
  }

  public boolean markMovies() {
    boolean isOK = false;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();

      String hqlUpdate = "update Movie m set m.path = 'X' where m.idmovie >0";
      session.createQuery(hqlUpdate).executeUpdate();
      transaction.commit();
      isOK = true;
    } catch (Exception e) {
      LOGGER.error("Error when marking movies: " + e.getMessage());
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return isOK;
  }

  public boolean deleteMarkedMovies() {
    boolean isOK = false;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      String hqlUpdate = "delete Movie m where m.path = 'X'";
      session.createQuery(hqlUpdate).executeUpdate();
      transaction.commit();
      isOK = true;
    } catch (Exception e) {
      LOGGER.error("Error when deleting marking movies: " + e.getMessage());
      isOK = false;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return isOK;
  }

}
