package by.minsk.kes.pipeline.controller;

import by.minsk.kes.pipeline.persistence.dao.EventDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FluxController {

    private static final Logger LOG = LoggerFactory.getLogger(FluxController.class);

    @Autowired
    private EventDao eventDao;
}
