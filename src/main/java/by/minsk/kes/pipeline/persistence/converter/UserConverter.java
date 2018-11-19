package by.minsk.kes.pipeline.persistence.converter;

import by.minsk.kes.pipeline.domain.UiUser;
import by.minsk.kes.pipeline.domain.persistence.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractHibernateConverter<User, UiUser> {

    public UiUser convertToUiModel(final User user) {
        final UiUser uiUser = new UiUser();
        BeanUtils.copyProperties(user, uiUser);
        if (user.getActive() != null && user.getActive().equalsIgnoreCase("Y")) {
            uiUser.setActive(true);
        }
        return uiUser;
    }

    public User convertToDbModel(final UiUser user) {
        final User dbUser = new User();
        BeanUtils.copyProperties(user, dbUser);
        if (user.isActive()) {
            dbUser.setActive("Y");
        } else {
            dbUser.setActive("N");
        }
        return dbUser;
    }
}
