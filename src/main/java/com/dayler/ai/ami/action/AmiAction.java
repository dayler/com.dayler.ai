package com.dayler.ai.ami.action;

public abstract class AmiAction implements Request {

    protected String actionId;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public abstract StringBuilder toCommand();
}
