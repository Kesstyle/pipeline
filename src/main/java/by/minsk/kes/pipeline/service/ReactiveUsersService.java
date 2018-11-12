package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.User;
import by.minsk.kes.pipeline.persistence.dao.hibernate.UsersDao;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ReactiveUsersService {

  @Autowired
  private UsersDao usersDao;

  public Flux<User> getAllUsers() {
    return Flux.fromIterable(usersDao.getAllUsers()).delayElements(Duration.ofSeconds(4));
  }
}
