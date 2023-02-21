package com.workmotion.ems.workflow;

public enum WorkFlowEvents {
    BEGIN_CHECK,
    FINISH_SECURITY_CHECK,
    COMPLETE_INITIAL_WORK_PERMIT_CHECK,
    FINISH_WORK_PERMIT_CHECK,
    ACTIVATE;

    public static WorkFlowEvents get(final String event) {
        for (WorkFlowEvents ev : WorkFlowEvents.values()) {
            if (ev.name().equals(event))
                return ev;
        }
        return null;
    }
}
