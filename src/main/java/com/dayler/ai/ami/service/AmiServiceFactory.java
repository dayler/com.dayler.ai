/**
 *
 */
package com.dayler.ai.ami.service;

/**
 * Builds {@link AmiService} instance.
 *
 * @author asalazar
 */
public final class AmiServiceFactory {

    private static final int DEFAULT_SOCKET_READ_TIMEOUT = 0;

    private static final int DEFAULT_SOCKET_TIMEOUT = 0;

    private static final int DEFAULT_EVENT_TIMEOUT = 5000;

    private static final int DEFAULT_RESPONSE_TIMEOUT = 2000;

    private final static int DEFAULT_PORT = 5038;

    private String hostName;

    private int port = DEFAULT_PORT;

    private String userName;

    private String secret;

    private int listenerCount = 4;

    private long pingTimeout;

    /**
     * <code>true<code> to use SSL for the connection.
     */
    private boolean useSSL = false;

    /**
     * The timeout to wait for a ManagerResponse after sending a
     * ManagerAction.
     */
    private long responseTimeout = DEFAULT_RESPONSE_TIMEOUT;

    /**
     * The default timeout to wait for the last ResponseEvent after sending an
     * EventGeneratingAction.
     */
    private long eventTimeout = DEFAULT_EVENT_TIMEOUT;

    /**
     * Timeout to use when connecting to the Asterisk server. A timeout of <b>0</b> indicates
     * infinite timeout;
     */
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    /**
     * Closes the connection (and reconnects) if no input has been read for the given amount of
     * milliseconds. A timeout of <b>0</b> indicates infinite timeout;
     */
    private int socketReadTimeout = DEFAULT_SOCKET_READ_TIMEOUT;

    /**
     * @return An instance of {@link AmiService}.
     */
    public AmiService getService() {
        AmiServiceImpl service = new AmiServiceImpl(hostName, port, userName, secret, socketTimeout,
                socketReadTimeout, useSSL, listenerCount, pingTimeout);
        service.setEventTimeout(eventTimeout);
        service.setResponseTimeout(responseTimeout);

        return service;
    }

    /**
     * Sets asterisk interface service host.
     *
     * @param hostName Asterisk interface host.
     * @return factory.
     */
    public AmiServiceFactory setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    /**
     * Sets port on which is listening the AMI service.
     *
     * @param port Port on which is listening the AMI service.
     * @return factory.
     */
    public AmiServiceFactory setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the <b>user name</b> to login in the AMI service.
     *
     * @param userName the user name to login in the AMI service.
     * @return factory.
     */
    public AmiServiceFactory setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Sets <b>secret key</b> to logging in the AMI service.
     *
     * @param secret The <b>secret key</b> to logging in the AMI service.
     * @return factory.
     */
    public AmiServiceFactory setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    /**
     * Amount of threads to handle the AMI responses.
     *
     * @param listenerCount Amount of threads to handle the AMI responses
     * @return factory
     */
    public AmiServiceFactory setListenerCount(int listenerCount) {
        this.listenerCount = listenerCount;
        return this;
    }

    /**
     * Time out to execute ping to AMI service.
     *
     * @param pingTimeout Time out to execute ping to AMI service.
     * @return factory
     */
    public AmiServiceFactory setPingTimeout(long pingTimeout) {
        this.pingTimeout = pingTimeout;
        return this;
    }

    /**
     * @param useSSL True to use SSL connection with AMI.
     * @return factory
     */
    public AmiServiceFactory setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
        return this;
    }

    public AmiServiceFactory setResponseTimeout(long responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public AmiServiceFactory setEventTimeout(long eventTimeout) {
        this.eventTimeout = eventTimeout;
        return this;
    }

    /**
     * @param socketTimeout Socket time out.
     * @return factory
     */
    public AmiServiceFactory setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    /**
     * @param socketReadTimeout Time out to read the socket.
     * @return factory.
     */
    public AmiServiceFactory setSocketReadTimeout(int socketReadTimeout) {
        this.socketReadTimeout = socketReadTimeout;
        return this;
    }
}
