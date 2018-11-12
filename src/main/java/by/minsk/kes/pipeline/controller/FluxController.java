package by.minsk.kes.pipeline.controller;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.domain.TestDomain;
import by.minsk.kes.pipeline.persistence.dao.EventDao;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FluxController {

    private static final Logger LOG = LoggerFactory.getLogger(FluxController.class);

    @Autowired
    private EventDao eventDao;

    @PostMapping(path = "/api/test2")
    public Mono<Void> justTest(@RequestBody final TestDomain testDomain) {
        System.out.println(testDomain.getName());
        return Mono.empty().then();
    }

    @PostMapping(path = "/api/insert2")
    public Mono<Void> insert(@RequestBody final KesEvent testDomain) {
        System.out.println(testDomain.getName());
        return Mono.empty().then();
    }
}
