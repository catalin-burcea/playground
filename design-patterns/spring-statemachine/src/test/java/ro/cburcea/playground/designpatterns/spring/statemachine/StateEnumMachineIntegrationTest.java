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
@ContextConfiguration(classes = SimpleEnumStateMachineConfiguration.class)
public class StateEnumMachineIntegrationTest {

    @Autowired
    private StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine;

    @BeforeEach
    public void setUp() {
        stateMachine.start();
    }

    @Test
    public void whenStateMachineConfiguredWithEnums_thenStateMachineAcceptsEnumEvents() {
        assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.APPROVE));
        assertEquals(ApplicationReviewStates.PRINCIPAL_REVIEW, stateMachine.getState().getId());
        assertTrue(stateMachine.sendEvent(ApplicationReviewEvents.REJECT));
        assertEquals(ApplicationReviewStates.REJECTED, stateMachine.getState().getId());
    }

    @AfterEach
    public void tearDown() {
        stateMachine.stop();
    }
}