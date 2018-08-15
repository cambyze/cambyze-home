package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Applications;

public class ApplicationsModel extends AbstractModel<Applications> {

  public ApplicationsModel() {
    super(Applications.class);
  }

  public List<Applications> findByName(String keyword) {
    List<Applications> applications = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query =
          session.createQuery("select a from Applications a where a.name like :keyword");
      query.setParameter("keyword", "%" + keyword + "%");
      applications = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      applications = null;
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
    return applications;
  }
}
