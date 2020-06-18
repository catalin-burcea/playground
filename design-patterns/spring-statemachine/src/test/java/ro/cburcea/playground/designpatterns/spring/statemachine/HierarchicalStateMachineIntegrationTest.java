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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HierarchicalStateMachineConfiguration.class)
public class HierarchicalStateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenTransitionToSubMachine_thenSubStateIsEntered() {

        assertEquals(Arrays.asList("SI", "SUB1"), stateMachine.getState().getIds());

        stateMachine.sendEvent("se1");

        assertEquals(Arrays.asList("SI", "SUB2"), stateMachine.getState().getIds());

        stateMachine.sendEvent("s-end");

        assertEquals(Arrays.asList("SI", "SUBEND"), stateMachine.getState().getIds());

        stateMachine.sendEvent("end");

        assertEquals(1, stateMachine.getState().getIds().size());
        assertEquals("SF", stateMachine.getState().getId());
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stop();
    }
}