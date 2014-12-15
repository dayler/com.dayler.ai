/**
 *
 */
package com.dayler.ai.ami.event;

import com.dayler.common.util.Parameters;
import com.dayler.common.util.StringUtils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

/**
 * @author asalazar
 */
public class AmiEventArgs {

    private ArrayDeque<String> args = null;

    private Object ctx = null;

    public AmiEventArgs(ArrayDeque<String> args) {
        Parameters.checkNull(args, "args");

        this.args = args;
    }

    public Collection<String> getArgs() {
        return Collections.unmodifiableCollection(args);
    }

    public Object getCtx() {
        return ctx;
    }

    public void setCtx(Object ctx) {
        this.ctx = ctx;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<--- Body Ami Event Args --->");
        builder.append(StringUtils.END_LINE);
        builder.append("Size: ").append(args.size()).append(" ");

        for (String line : args) {
            builder.append(line);
            builder.append(StringUtils.END_LINE);
        }

        builder.append("<--- End body Ami Event Args --->");
        builder.append(StringUtils.END_LINE);

        if (ctx != null) {
            builder.append(String.format("ctx: %s", ctx.getClass().getName()));
        }

        return builder.toString();
    }
}
