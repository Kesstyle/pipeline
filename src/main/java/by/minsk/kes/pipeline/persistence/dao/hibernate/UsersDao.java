package by.minsk.kes.pipeline.persistence.dao.hibernate;

import by.minsk.kes.pipeline.domain.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UsersDao extends CommonHibernateDao {

  public List<User> getAllUsers() {
    return getHibernateTemplate().loadAll(User.class);
  }
}
