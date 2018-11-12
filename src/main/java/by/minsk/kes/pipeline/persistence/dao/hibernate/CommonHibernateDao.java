package by.minsk.kes.pipeline.persistence.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class CommonHibernateDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private HibernateTemplate hibernateTemplate;

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  protected HibernateTemplate getHibernateTemplate() {
    return hibernateTemplate;
  }
}
