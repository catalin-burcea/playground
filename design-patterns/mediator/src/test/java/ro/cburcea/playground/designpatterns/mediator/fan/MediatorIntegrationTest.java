package ro.cburcea.playground.designpatterns.mediator.fan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MediatorIntegrationTest {

    private Button button;
    private Fan fan;

    @BeforeEach
    public void setUp() {
        this.button = new Button();
        this.fan = new Fan();
        PowerSupplier powerSupplier = new PowerSupplier();
        Mediator mediator = new Mediator();

        mediator.setButton(this.button);
        mediator.setFan(fan);
        mediator.setPowerSupplier(powerSupplier);
    }

    @Test
    public void givenTurnedOffFan_whenPressingButtonTwice_fanShouldTurnOnAndOff() {
        assertFalse(fan.isOn());

        button.press();
        assertTrue(fan.isOn());

        button.press();
        assertFalse(fan.isOn());
    }
}