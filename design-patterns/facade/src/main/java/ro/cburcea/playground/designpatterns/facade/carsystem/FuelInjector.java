package ro.cburcea.playground.designpatterns.facade.carsystem;

import java.util.logging.Logger;

public class FuelInjector {

    private static final Logger LOGGER = Logger.getLogger(FuelInjector.class.getName());

    private FuelPump fuelPump = new FuelPump();

    public void on() {
        LOGGER.info("Fuel injector ready to inject fuel.");
    }

    public void inject() {
        fuelPump.pump();
        LOGGER.info("Fuel injected.");
    }

    public void off() {
        LOGGER.info("Stopping Fuel injector..");
    }
}