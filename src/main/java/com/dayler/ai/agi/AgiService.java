/*
 * AgiServer.java
 */
package com.dayler.ai.agi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The AgiServer class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public class AgiService implements Runnable {

    /* constants for properties */
    public static final String BIND_ADDRESS = "bindAddress";
    public static final String PORT = "port";
    public static final String BACKLOG = "backlog";

    public static final int DEFAULT_PORT = 4573;
    public static final String ALL = "all";

    /* private variables */
    private InetAddress bindAddress;
    private int port;
    private int backlog;

    /**
     * The agiAppMap.
     */
    private Map<String, AgiApp> agiAppMap;

    /**
     * The serverSocket.
     */
    private ServerSocket serverSocket;

    /**
     * Creates a new instance of AgiServer.
     *
     * @param properties Properties
     */
    public AgiService(Properties properties) {
        setProperties(properties);
        agiAppMap = new HashMap<String, AgiApp>();
    }

    /**
     * Starts this.
     *
     * @throws Exception
     */
    public void start() throws Exception {
        serverSocket = new ServerSocket(port, backlog, bindAddress);
        if (serverSocket.isBound()) {
            Thread t = new Thread(this, getClass().getName());
            t.setPriority(Thread.NORM_PRIORITY + 2);
            t.start();
        }
    }

    /**
     * Interrupts this.
     */
    public void interrupt() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ioe) {
        }
    }

    @Override
    public void run() {
        while (serverSocket != null && serverSocket.isBound() && !serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new AgiConn(agiAppMap, socket)).start();
            } catch (IOException ioe) {
                Logger.getLogger("com.nuevatel.asterisk.fastagi").logp(Level.WARNING, getClass().getName(), "run", ioe.getMessage(), ioe);
            }
        }
    }

    /**
     * Adds the agiApp.
     *
     * @param agiApp AgiApp
     */
    public void add(AgiApp agiApp) {
        agiAppMap.put(agiApp.getName(), agiApp);
    }

    /**
     * Sets the properties.
     *
     * @param properties Properties
     */
    private void setProperties(Properties properties) {
        if (properties == null) throw new IllegalArgumentException("null properties");
        // bindAddress
        try {
            String str = properties.getProperty(BIND_ADDRESS, ALL);
            if (str.equals(ALL)) bindAddress = null;
            else bindAddress = InetAddress.getByName(str);
        } catch (UnknownHostException uhe) {
            throw new RuntimeException("illegal " + BIND_ADDRESS + " " + properties.getProperty(BIND_ADDRESS), uhe);
        }

        // port
        try {
            port = Integer.parseInt(properties.getProperty(PORT, String.valueOf(DEFAULT_PORT)));
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("illegal " + PORT + " " + properties.getProperty(PORT), nfe);
        }

        // backlog
        try {
            backlog = Integer.parseInt(properties.getProperty(BACKLOG, "65535"));
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("illegal " + BACKLOG + " " + properties.getProperty(BACKLOG), nfe);
        }
    }
}
