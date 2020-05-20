package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class Radiator {

    private static final Logger LOGGER = Logger.getLogger(Radiator.class.getName());

    public void on() {
        LOGGER.info("Radiator switched on!");
    }

    public void off() {
        LOGGER.info("Radiator switched off!");
    }

    public void setSpeed(Integer speed) {
        LOGGER.info("Setting radiator speed to " + speed);
    }
}