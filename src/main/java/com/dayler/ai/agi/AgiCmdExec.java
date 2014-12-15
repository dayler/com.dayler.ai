/*
 * AgiCmdExec.java
 */
package com.dayler.ai.agi;

/**
 * <p>The AgiCmdExec interface.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public interface AgiCmdExec {

    /* constants */
    static String ANSWER = "ANSWER";
    static String GET_DATA = "GET DATA";
    static String GET_OPTION = "GET OPTION";
    static String HANGUP = "HANGUP";
    static String NOOP = "NOOP";
    static String STREAM_FILE = "STREAM FILE";
    static String WAIT_FOR_DIGIT = "WAIT FOR DIGIT";
    static String GET_VARIABLE = "GET VARIABLE";
    static String VOICEMAIL = "VOICEMAIL";
    static String SET_VARIABLE = "SET VARIABLE";
    static String EXEC = "EXEC";

    static String NL = "\n";

    /**
     * Answers channel if not already in answer state.
     *
     * @throws Exception
     */
    public void answer() throws Exception;

    /**
     * Streams the given file, and receive DTMF data.
     *
     * @param file      String
     * @param timeout   int
     * @param maxDigits int
     * @return String
     * @throws Exception
     */
    public String getData(String file, int timeout, int maxDigits) throws Exception;

    /**
     * Streams the given file, and receive DTMF data.
     *
     * @param file    String
     * @param timeout int
     * @return String
     * @throws Exception
     */
    public String getData(String file, int timeout) throws Exception;

    /**
     * Streams the given file, and receive DTMF data.
     *
     * @param file String
     * @return String
     * @throws Exception
     */
    public String getData(String file) throws Exception;

    /**
     * Hangs up the channel.
     *
     * @throws Exception
     */
    public void hangup() throws Exception;

    /**
     * Does nothing.
     *
     * @throws Exception
     */
    public void noop() throws Exception;

    /**
     * Streams the given file, allowing playback to be interrupted by the given digits, if any.
     *
     * @param file         String
     * @param escapeDigits String
     * @return char
     * @throws Exception
     */
    public char streamFile(String file, String escapeDigits) throws Exception;

    /**
     * Streams the given file.
     *
     * @param file String
     * @throws Exception
     */
    public void streamFile(String file) throws Exception;

    /**
     * Waits up to <timeout> milliseconds for channel to receive a DTMF digit.
     *
     * @param timeout int
     * @return char
     * @throws Exception
     */
    public char waitForDigit(int timeout) throws Exception;

    /**
     * Dials to <sipExtension> with <options>.
     *
     * @param sipExtension String
     * @param options      String
     * @param callerId     String
     * @return void
     * @throws Exception
     */
    abstract void dial(String sipExtension, String options, String callerId)
            throws Exception;

    /**
         * Exec VoiceMail app of <mailbox>.
         *
         * @param sipExtension String
         * @throws Exception
         */
        void voiceMail(String mailbox) throws Exception;

    /**
     * Exec VoiceMailMain of <mailbox>.
     *
     * @param mailbox String
     * @return void
     * @throws Exception
     */
    public void voiceMailMain(String mailbox) throws Exception;

    /**
     * Waits <seconds> seconds.
     *
     * @param seconds String
     * @return void
     * @throws Exception
     */
    public void waitSeconds(String seconds) throws Exception;

    /**
     * Sets a <variable> to the current channel.
     *
     * @param variable String
     * @param value    String
     * @throws Exception
     */
    int setVariable(String variable, String value) throws Exception;

    String getVariable(String dialStatus) throws Exception;
}