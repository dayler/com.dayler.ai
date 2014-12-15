/**
 *
 */
package com.dayler.ai.ami.event;

import com.dayler.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asalazar
 */
public final class AmiResponseFactory {

    private AmiResponseFactory() {
        // No op.
    }

    public static Response build(AmiEventArgs eventArgs) throws InstantiationException, IllegalAccessException {
        Map<String, String> metadata = new HashMap<String, String>();

        for (String line : eventArgs.getArgs()) {
            String[] pair = line.trim().split(":");

            if (pair.length > 0) {
                String key = pair[0].trim();
                String value = null;

                if (pair.length > 1) {
                    value = pair[1].trim();
                }

                metadata.put(key, value);
            }
        }

        String respKey = getRespKey(metadata);

        Class<? extends Response> clazz = AmiResponseClassCatalog.classFromEventName(respKey);
        Response response = null;

        if (clazz != null) {
            response = clazz.newInstance();
            response.build(metadata, eventArgs.getCtx());
        }

        return response;
    }

    private static String getRespKey(Map<String, String> args) {
        String respKey = args.get(AmiResponseField.Event.name());

        return StringUtils.isBlank(respKey) ? args.get(AmiResponseField.Response.name()) : respKey;
    }
}
