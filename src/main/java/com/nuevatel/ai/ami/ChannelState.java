package com.nuevatel.ai.ami;

/**
 * The lifecycle status of an {@link org.asteriskjava.live.AsteriskChannel}.
 * <br>
 * Defined in <code>channel.c</code> function <code>ast_state2str</code>.
 *
 * @author srt, asalazar
 */
public enum ChannelState {

    /**
     * Channel is down and available.
     * This is the initial state of the channel when it is not yet in use.
     */
    DOWN(0),

    /**
     * Channel is down, but reserved.
     */
    RSRVD(1),

    /**
     * Channel is off hook.
     */
    OFFHOOK(2),

    /**
     * Digits (or equivalent) have been dialed.
     */
    DIALING(3),

    /**
     * Line is ringing.
     */
    RING(4),

    /**
     * Remote end is ringing.
     */
    RINGING(5),

    /**
     * Line is up.
     */
    UP(6),

    /**
     * Line is busy.
     */
    BUSY(7),

    /**
     * Digits (or equivalent) have been dialed while offhook.
     */
    DIALING_OFFHOOK(8),

    /**
     * Channel has detected an incoming call and is waiting for ring.
     */
    PRERING(9),

    /**
     * The channel has been hung up and is not longer available on the Asterisk server.
     */
    HUNGUP(-1);

    private int status;

    /**
     * Creates a new instance.
     *
     * @param status the numerical status code.
     */
    ChannelState(int status) {
        this.status = status;
    }

    /**
     * Returns the numerical status code.
     *
     * @return the numerical status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns value specified by int. Use this to transform
     * {@link org.asteriskjava.manager.event.AbstractChannelStateEvent#getChannelState()}.
     *
     * @param status integer representation of the status.
     * @return corresponding ChannelState object or <code>null</code> if none matches.
     */
    public static ChannelState valueOf(Integer status) {
        if (status == null) {
            return null;
        }

        for (ChannelState tmp : ChannelState.values()) {
            if (tmp.getStatus() == status) {
                return tmp;
            }
        }

        return null;
    }

}
