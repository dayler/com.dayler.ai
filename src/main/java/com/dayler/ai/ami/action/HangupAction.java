/**
 *
 */
package com.dayler.ai.ami.action;

import static com.dayler.ai.Constants.*;

import com.dayler.common.util.Parameters;

/**
 * @author asalazar
 */
public class HangupAction extends AmiAction {

    /**
     * Action name.
     */
    private static final String ACTION_NAME = "Hangup";

    /**
     * Chanel to close.
     */
    private String channel;

    public HangupAction(String channel) {
        Parameters.checkNull(channel, "chanel");

        this.channel = channel;
        setActionId(channel);
    }

    public String getChannel() {
        return channel;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.action.AmiAction#toCommand()
     */
    @Override
    public StringBuilder toCommand() {
        StringBuilder builder =
                new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Channel.name(), channel));
        builder.append(CARRIAGE_RETURN);

        return builder;
    }

}
