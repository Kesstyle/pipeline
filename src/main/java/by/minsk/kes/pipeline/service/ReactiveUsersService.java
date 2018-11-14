package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.UiUser;
import by.minsk.kes.pipeline.domain.persistence.User;
import by.minsk.kes.pipeline.persistence.dao.hibernate.UsersDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ReactiveUsersService {

  @Autowired
  private UsersDao usersDao;

  public Flux<UiUser> getAllUsers() {
    return Flux.fromIterable(usersDao.getAllUsers())
        .map(this::convertToUiModel)
        .delayElements(Duration.ofSeconds(4));
  }

  public Mono<UiUser> getUserByToken(final Mono<String> token) {
    return Mono.from(token)
        .map(usersDao::getUserByToken)
        .map(this::convertToUiModel)
        .onErrorResume(t -> Mono.empty());
  }

  public Mono<String> insertUser(final Mono<UiUser> users) {
    return Mono.from(users)
        .map(this::convertToDbModel)
        .map(usersDao::insertUser)
        .map(u -> u.getToken());
  }

  private UiUser convertToUiModel(final User user) {
    final UiUser uiUser = new UiUser();
    BeanUtils.copyProperties(user, uiUser);
    if (user.getActive() != null && user.getActive().equalsIgnoreCase("Y")) {
      uiUser.setActive(true);
    }
    return uiUser;
  }

  private User convertToDbModel(final UiUser user) {
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
