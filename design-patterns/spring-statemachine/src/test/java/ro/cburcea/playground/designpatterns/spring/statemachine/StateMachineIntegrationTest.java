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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SimpleStateMachineConfiguration.class)
public class StateMachineIntegrationTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenSimpleStringStateMachineEvents_thenEndState() {
        assertEquals("SI", stateMachine.getState().getId());

        stateMachine.sendEvent("E1");
        assertEquals("S1", stateMachine.getState().getId());

        stateMachine.sendEvent("E2");
        assertEquals("S2", stateMachine.getState().getId());
    }

    @Test
    public void whenSimpleStringMachineActionState_thenActionExecuted() {

        stateMachine.sendEvent("E3");
        assertEquals("S3", stateMachine.getState().getId());

        boolean acceptedE4 = stateMachine.sendEvent("E4");

        assertTrue(acceptedE4);
        assertEquals("S4", stateMachine.getState().getId());

        stateMachine.sendEvent("E5");
        assertEquals("S5", stateMachine.getState().getId());

        stateMachine.sendEvent("end");
        assertEquals("SF", stateMachine.getState().getId());

        assertEquals(2, stateMachine.getExtendedState().getVariables().get("approvalCount"));
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stop();
    }
}