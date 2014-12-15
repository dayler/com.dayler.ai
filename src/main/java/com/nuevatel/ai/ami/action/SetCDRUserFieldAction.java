/**
 * 
 */
package com.nuevatel.ai.ami.action;

import static com.nuevatel.ai.Constants.*;
import static com.nuevatel.common.util.ClassUtil.ifNull;

import com.nuevatel.common.util.Parameters;

/**
 * 
 * @author asalazar
 *
 */
public class SetCDRUserFieldAction extends AmiAction {
    /**
     * Action name.
     */
    private static final String ACTION_NAME = "SetCDRUserField";

    private String channel;

    private String userField;

    private String response;

    private String message;

    public SetCDRUserFieldAction(String actionId, String channel, String userField,
                                String response, String message) {
        Parameters.checkNull(actionId, "actionId");
        Parameters.checkBlankString(channel, "channel");
        Parameters.checkBlankString(userField, "userField");

        setActionId(actionId);
        this.channel = channel;
        this.userField = userField;
        this.response = response;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getUserField() {
        return userField;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     * 
     *  * Action: SetCDRUserField
Channel: SIP/test-10225140
UserField: abcdefg 
          
Response: Success
Message: CDR Userfield Set
     * 
     */
    @Override
    public StringBuilder toCommand() {
        StringBuilder builder
            = new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.ActionID.name(), actionId));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Channel.name(), channel));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.UserField.name(), userField));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Response.name(), ifNull(response, "")));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Message.name(), ifNull(message, "")));
        builder.append(CARRIAGE_RETURN);
        return builder;
    }
}
