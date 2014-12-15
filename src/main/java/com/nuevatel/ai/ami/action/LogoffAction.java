/**
 *
 */
package com.nuevatel.ai.ami.action;

import static com.nuevatel.ai.Constants.CARRIAGE_RETURN;
import static com.nuevatel.ai.Constants.COMMAND_LINE;

/**
 * @author asalazar
 */
public class LogoffAction extends AmiAction {

    private static final String ACTION_NAME = "Logoff";

    @Override
    public StringBuilder toCommand() {
        StringBuilder builder =
                new StringBuilder(String.format(COMMAND_LINE, RequestFieldName.Action.name(), ACTION_NAME));
        builder.append(CARRIAGE_RETURN);

        return builder;
    }

}
