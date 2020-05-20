package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class Starter {

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getName());

    public void start() {
        LOGGER.info("Starting..");
    }
}