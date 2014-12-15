package com.dayler.ai;

import java.math.BigDecimal;

/**
 * Created by nuevatel.com on 6/24/14.
 */
public interface Constants {

    /**
     * Unix end line.
     */
    public static final String CARRIAGE_RETURN = "\r\n";

    /**
     * Template to build command property.
     * <p/>
     * Template: <b>"%s:%s\r\n"</b>
     */
    public static final String COMMAND_LINE = "%s:%s\r\n";

    public static int AGI_RESPONSE_LENGTH = 2;
    public static String AGI_RESPONSE_CODE = "200";
    public static String AGI_RESPONSE_SUCCESSFUL = "0";
    public static String AGI_RESPONSE_OTHERWISE = "1";
    public static String AGI_RESPONSE_FAILURE = "-1";
    public static int DEFAULT_TIMEOUT = 120;
    public static final byte OFFLINE = 0;
    public static final byte ONLINE = 1;

    /**
     * Fix factor to convert milliseconds to tenths of seconds.
     */
    public static final BigDecimal FIX_MILLISECONDS_FACTOR = new BigDecimal(100);
}
