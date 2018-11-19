package by.minsk.kes.pipeline.persistence.dao.hibernate;

import by.minsk.kes.pipeline.domain.persistence.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UsersDao extends CommonHibernateDao<User> {

    public List<User> getAllUsers() {
        return getHibernateTemplate().loadAll(User.class);
    }

    public User getUserByToken(final String token) {
        return getSession().bySimpleNaturalId(User.class).load(token);
    }

    public User insertUser(final User user) {
        getHibernateTemplate().save(user);
        listeners.forEach(listener -> listener.insertListener(user));
        return user;
    }

    public User updateLastRead(final Long userId, final Long lastReadId) {
        final User user = getHibernateTemplate().load(User.class, userId);
        if (user != null) {
            user.setLastReadId(lastReadId);
        }
        return user;
    }
}
