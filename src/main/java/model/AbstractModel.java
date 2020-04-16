package model;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

public abstract class AbstractModel<T> {

  private static final Logger LOGGER = Logger.getLogger(AbstractModel.class);

  private Class<T> entity;
  protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  public AbstractModel(Class<T> entity) {
    this.entity = entity;
  }

  public List<T> findAll() {
    List<T> result = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      result = session.createQuery("from " + entity.getName()).list();
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Error in findAll for entity " + entity.getName() + ": " + e.getMessage());
      result = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

  public T find(Object id) {
    T result = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      result = (T) session.get(entity, (Serializable) id);
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Error in find for entity " + entity.getName() + ": " + e.getMessage());
      result = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

  public boolean create(T entity) {
    boolean result = true;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Error in create: " + e.getMessage());
      result = false;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

  public boolean update(T entity) {
    boolean result = true;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.update(entity);
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Error in update: " + e.getMessage());
      result = false;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

  public boolean delete(T entity) {
    boolean result = true;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.delete(entity);
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Error in delete: " + e.getMessage());
      result = false;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

}
