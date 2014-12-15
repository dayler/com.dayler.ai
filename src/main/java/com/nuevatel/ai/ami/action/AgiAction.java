/**
 * 
 */
package com.nuevatel.ai.ami.action;

import static com.nuevatel.ai.Constants.*;

import com.nuevatel.common.util.Parameters;

/**
 * @author asalazar
 *
 *
 */
public class AgiAction extends AmiAction {

    /**
     * Action name.
     */
    private static final String ACTION_NAME = "AGI";

    private String channel;

    private String command;

    private String commandId;

    public AgiAction(String actionId, String channel, String command, String commandId) {
        Parameters.checkBlankString(actionId, "actionId");
        Parameters.checkBlankString(channel, "channel");
        Parameters.checkBlankString(command, "command");
        Parameters.checkBlankString(commandId, "commandId");

        this.actionId = actionId;
        this.channel = channel;
        this.command = command;
        this.commandId = commandId;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the commandId
     */
    public String getCommandId() {
        return commandId;
    }

    /**
     * 
     */
    @Override
    public StringBuilder toCommand() {
        StringBuilder builder
            = new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.ActionID.name(), actionId));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Channel.name(), channel));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Command.name(), command));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.CommandID.name(), commandId));
        builder.append(CARRIAGE_RETURN);

        return builder;
    }
}
