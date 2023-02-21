package com.workmotion.ems.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class WorkflowStateMachineConfiguration extends StateMachineConfigurerAdapter<WorkFlowStates, WorkFlowEvents> {

    @Autowired
    private  JpaPersistingStateMachineInterceptor<WorkFlowStates, WorkFlowEvents, String> persister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<WorkFlowStates, WorkFlowEvents> config)
            throws Exception {
        config
            .withPersistence()
                .runtimePersister(persister);
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(new WorkFlowStateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<WorkFlowStates, WorkFlowEvents> states) throws Exception {
        states
            .withStates()
            .initial(WorkFlowStates.ADDED)
            .fork(WorkFlowStates.IN_CHECK)
            .join(WorkFlowStates.JOIN)
            .state(WorkFlowStates.APPROVED)
            .state(WorkFlowStates.ACTIVE)
            .and()
            .withStates()
                .parent(WorkFlowStates.IN_CHECK)
                .initial(WorkFlowStates.SECURITY_CHECK_STARTED)
                .end(WorkFlowStates.SECURITY_CHECK_FINISHED)
            .and()
            .withStates()
                .parent(WorkFlowStates.IN_CHECK)
                .initial(WorkFlowStates.WORK_PERMIT_CHECK_STARTED)
                .state(WorkFlowStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
                .end(WorkFlowStates.WORK_PERMIT_CHECK_FINISHED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<WorkFlowStates, WorkFlowEvents> transitions) throws Exception {
        transitions.withExternal()
                    .source(WorkFlowStates.ADDED).target(WorkFlowStates.IN_CHECK).event(WorkFlowEvents.BEGIN_CHECK)
                 .and()
                .withExternal()
                    .source(WorkFlowStates.SECURITY_CHECK_STARTED).target(WorkFlowStates.SECURITY_CHECK_FINISHED).event(WorkFlowEvents.FINISH_SECURITY_CHECK)
                .and()
                .withExternal()
                    .source(WorkFlowStates.WORK_PERMIT_CHECK_STARTED).target(WorkFlowStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION).event(WorkFlowEvents.COMPLETE_INITIAL_WORK_PERMIT_CHECK)
                .and()
                .withExternal()
                .source(WorkFlowStates.WORK_PERMIT_CHECK_PENDING_VERIFICATION).target(WorkFlowStates.WORK_PERMIT_CHECK_FINISHED).event(WorkFlowEvents.FINISH_WORK_PERMIT_CHECK)

                .and()
                .withExternal()
                    .source(WorkFlowStates.JOIN).target(WorkFlowStates.APPROVED)
                .and()
                .withExternal()
                    .source(WorkFlowStates.APPROVED).target(WorkFlowStates.ACTIVE).event(WorkFlowEvents.ACTIVATE)
                .and()
                .withFork()
                    .source(WorkFlowStates.IN_CHECK)
                    .target(WorkFlowStates.SECURITY_CHECK_STARTED)
                    .target(WorkFlowStates.WORK_PERMIT_CHECK_STARTED)
                .and()
                .withJoin()
                    .source((WorkFlowStates.SECURITY_CHECK_FINISHED))
                    .source(WorkFlowStates.WORK_PERMIT_CHECK_FINISHED)
                    .target(WorkFlowStates.JOIN);
    }
}
