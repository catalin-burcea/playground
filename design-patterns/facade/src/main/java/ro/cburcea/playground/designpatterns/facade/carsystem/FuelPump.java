package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class FuelPump {

    private static final Logger LOGGER = Logger.getLogger(FuelPump.class.getName());

    public void pump() {
        LOGGER.info("Fuel Pump is pumping fuel..");
    }
}