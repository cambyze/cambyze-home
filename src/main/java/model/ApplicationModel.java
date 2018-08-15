package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Application;

public class ApplicationModel extends AbstractModel<Application> {

  public ApplicationModel() {
    super(Application.class);
  }

  public List<Application> findByName(String keyword) {
    List<Application> applicationList = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query =
          session.createQuery("select a from Application a where a.name like :keyword");
      query.setParameter("keyword", "%" + keyword + "%");
      applicationList = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      applicationList = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
    return applicationList;
  }
}
