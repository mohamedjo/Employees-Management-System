package com.workmotion.ems.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;

import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.util.EMSException;
import com.workmotion.ems.util.ExceptionEnums;
import com.workmotion.ems.workflow.WorkFlowEvents;
import com.workmotion.ems.workflow.WorkFlowStateMachineInterceptor;
import com.workmotion.ems.workflow.WorkFlowStates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class WorkFlowServiceImpl implements IWorkFlowService {

    private static Logger log = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Autowired
    private StateMachineService<WorkFlowStates, WorkFlowEvents> stateMachineService;
    @Autowired

    private WorkFlowStateMachineInterceptor stateMachineInterceptor;

    @Override
    public boolean executeTransition(Employee employee, String event) throws EMSException {
        final String methodName = "executeTransition()";
        StateMachine<WorkFlowStates, WorkFlowEvents> sm = stateMachineService.acquireStateMachine(employee.getId().toString());

        WorkFlowEvents workflowEvent = WorkFlowEvents.get(event);

        if (workflowEvent == null) {
            log.error("{} Unkown event: {}.", methodName, event);
            throw ExceptionEnums.STATE_TRANSITION_EXCEPTION.get().errorMessage("Unkown event name.");
        }

        Message<WorkFlowEvents> message = MessageBuilder.withPayload(workflowEvent)
                .setHeader("employeeId", employee.getId())
                .build();

        sm.getStateMachineAccessor().doWithAllRegions(sma -> sma.addStateMachineInterceptor(stateMachineInterceptor));
        return sm.sendEvent(message);
    }
}
