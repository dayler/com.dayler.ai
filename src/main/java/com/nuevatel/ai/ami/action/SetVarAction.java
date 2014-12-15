package com.nuevatel.ai.ami.action;

import static com.nuevatel.ai.Constants.*;

import com.nuevatel.common.util.Parameters;

/**
 * Set channel variable. The feature to set global variables was disable.
 * 
 * @author asalazar
 *
 */
public class SetVarAction extends AmiAction {

    private static final String ACTION_NAME = "SetVar";

    /**
     * Always originate channel.
     */
    private String channel;

    /**
     * Identifier for the variable. Variable name.
     */
    private String variable;

    /**
     * Value of the variable.
     */
    private String value;

    public SetVarAction(String actionId, String channel, String variable, String value) {
        Parameters.checkNull(channel, "channel");
        Parameters.checkNull(variable, "variable");

        this.actionId = actionId;
        this.channel = channel;
        this.variable = variable;
        this.value = value;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.action.AmiAction#toCommand()
     */
    @Override
    public StringBuilder toCommand() {
        StringBuilder builder =
                new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.ActionID.name(), actionId));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Channel.name(), channel));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Variable.name(), variable));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Value.name(), value));
        builder.append(CARRIAGE_RETURN);

        return builder;
    }

}
