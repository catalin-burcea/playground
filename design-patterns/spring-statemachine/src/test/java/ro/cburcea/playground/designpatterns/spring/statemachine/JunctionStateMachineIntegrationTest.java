package ro.cburcea.playground.designpatterns.spring.statemachine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JunctionStateMachineConfiguration.class)
public class JunctionStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenTransitioningToJunction_thenArriveAtSubJunctionNode() {

        stateMachine.sendEvent("E1");
        assertEquals("low", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        assertEquals("SF", stateMachine.getState().getId());
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stop();
    }
}