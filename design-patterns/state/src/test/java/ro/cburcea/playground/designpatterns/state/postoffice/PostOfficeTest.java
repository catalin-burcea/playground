package ro.cburcea.playground.designpatterns.state.postoffice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostOfficeTest {

    @Test
    public void givenNewPackage_whenPackageReceived_thenStateReceived() {
        Package pkg = new Package();

        assertTrue(pkg.getState() instanceof OrderedState);
        pkg.nextState();

        assertTrue(pkg.getState() instanceof DeliveredState);
        pkg.nextState();

        assertTrue(pkg.getState() instanceof ReceivedState);
    }

    @Test
    public void givenDeliveredPackage_whenPrevState_thenStateOrdered() {
        Package pkg = new Package();
        pkg.setState(new DeliveredState());
        pkg.previousState();

        assertTrue(pkg.getState() instanceof OrderedState);
    }
}
