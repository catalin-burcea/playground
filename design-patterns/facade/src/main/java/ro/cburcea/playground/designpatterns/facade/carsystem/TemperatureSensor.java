package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class TemperatureSensor {

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getName());

    public void getTemperature() {
        LOGGER.info("Getting temperature from the sensor..");
    }

}