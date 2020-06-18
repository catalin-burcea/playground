package ro.cburcea.playground.designpatterns.spring.statemachine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ForkJoinStateMachineConfiguration.class)
public class ForkJoinStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenForkStateEntered_thenMultipleSubStatesEntered() {
        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));
    }

    @Test
    public void whenAllConfiguredJoinEntryStatesAreEntered_thenTransitionToJoinState() {

        boolean success = stateMachine.sendEvent("E1");

        assertTrue(success);

        assertTrue(Arrays.asList("SFork", "Sub1-1", "Sub2-1").containsAll(stateMachine.getState().getIds()));

        assertTrue(stateMachine.sendEvent("sub1"));
        assertTrue(stateMachine.sendEvent("sub2"));
        assertEquals("SJoin", stateMachine.getState().getId());
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stop();
    }
}