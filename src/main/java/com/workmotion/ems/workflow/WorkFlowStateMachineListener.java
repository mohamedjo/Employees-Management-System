package com.workmotion.ems.workflow;

import com.workmotion.ems.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import lombok.extern.slf4j.Slf4j;

public class WorkFlowStateMachineListener extends StateMachineListenerAdapter<WorkFlowStates, WorkFlowEvents>  {
    private static Logger log = LoggerFactory.getLogger(WorkFlowStateMachineListener.class);

    @Override
    public void stateChanged(State<WorkFlowStates, WorkFlowEvents> from, State<WorkFlowStates, WorkFlowEvents> to) {
        super.stateChanged(from, to);
        log.info("State changed to: {}", to.getId().name());
    }

    @Override
    public void eventNotAccepted(Message<WorkFlowEvents> event) {
        super.eventNotAccepted(event);
        log.info("State change failed: {}", event.getPayload());
    }
}