/**
 *
 */
package com.dayler.ai.ami.action;

import static com.dayler.ai.Constants.CARRIAGE_RETURN;
import static com.dayler.ai.Constants.COMMAND_LINE;

/**
 * @author asalazar
 */
public class LoginAction extends AmiAction {

    private static final String ACTION_NAME = "Login";

    private String userName;

    private String secret;

    public LoginAction(String userName, String secret) {
        super();

        this.userName = userName;
        this.secret = secret;
    }

    public String getUserName() {
        return userName;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public StringBuilder toCommand() {
        StringBuilder builder =
                new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.ActionID.name(), actionId));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Username.name(), userName));
        builder.append(String.format(COMMAND_LINE, RequestFieldName.Secret.name(), secret));
        builder.append(CARRIAGE_RETURN);

        return builder;
    }
}
