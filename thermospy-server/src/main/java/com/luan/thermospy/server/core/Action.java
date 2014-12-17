package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ludde on 14-12-17.
 */
public class Action {
    private int actionId = 0;

    public Action() {}

    public Action(int actionId)
    {
        this.actionId = actionId;
    }
    @JsonProperty
    public int getActionId()
    {
        return actionId;
    }
}
