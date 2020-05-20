package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class CatalyticConverter {

    private static final Logger LOGGER = Logger.getLogger(CatalyticConverter.class.getName());

    public void on() {
        LOGGER.info("Catalytic Converter switched on!");
    }

    public void off() {
        LOGGER.info("Catalytic Converter switched off!");
    }
}