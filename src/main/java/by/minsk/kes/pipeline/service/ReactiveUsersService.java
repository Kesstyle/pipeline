package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.UiUser;
import by.minsk.kes.pipeline.domain.UiUserWatch;
import by.minsk.kes.pipeline.domain.persistence.User;
import by.minsk.kes.pipeline.listener.KesListener;
import by.minsk.kes.pipeline.persistence.converter.UserConverter;
import by.minsk.kes.pipeline.persistence.dao.hibernate.UsersDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ReactiveUsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UserConverter userConverter;

    public Flux<UiUser> getAllUsers() {
        final Flux<UiUser> existingUsers = Flux.fromIterable(usersDao.getAllUsers())
                .map(userConverter::convertToUiModel)
                .delayElements(Duration.ofSeconds(1));
        return existingUsers.mergeWith(Flux.create(emitter -> {
            final KesListener<User> listener = u -> emitter.next(userConverter.convertToUiModel(u));
            usersDao.registerListener(listener);
        }, FluxSink.OverflowStrategy.BUFFER));
    }

    public Mono<UiUser> getUserByToken(final Mono<String> token) {
        return Mono.from(token)
                .map(usersDao::getUserByToken)
                .map(userConverter::convertToUiModel)
                .onErrorResume(t -> Mono.empty());
    }

    public Mono<String> insertUser(final Mono<UiUser> users) {
        return Mono.from(users)
                .map(userConverter::convertToDbModel)
                .map(usersDao::insertUser)
                .map(u -> u.getToken());
    }

    public Mono<UiUser> updateLastReadId(final Mono<UiUser> user) {
        return Mono.from(user)
                .map(userConverter::convertToDbModel)
                .map(u -> usersDao.updateLastRead(u.getId(), u.getLastReadId()))
                .map(userConverter::convertToUiModel);
    }
}
