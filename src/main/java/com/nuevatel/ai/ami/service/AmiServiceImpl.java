package com.nuevatel.ai.ami.service;

import com.nuevatel.ai.ami.action.AmiAction;
import com.nuevatel.ai.ami.action.LoginAction;
import com.nuevatel.ai.ami.action.LogoffAction;
import com.nuevatel.ai.ami.conn.*;
import com.nuevatel.ai.ami.event.AmiEvent;
import com.nuevatel.ai.ami.event.AmiEventArgs;
import com.nuevatel.ai.ami.service.cache.CacheServiceFactory;
import com.nuevatel.ai.ami.service.cache.SessionCache;
import com.nuevatel.ai.ami.service.cache.SessionCacheFactory;
import com.nuevatel.ai.ami.service.cache.SimpleCacheService;
import com.nuevatel.ai.ami.service.context.SessionCtx;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.thread.SimpleMonitor;
import com.nuevatel.common.util.Parameters;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Stables communication with astersk AMI service. And consume its events.
 *
 * @author asalazar
 */
class AmiServiceImpl implements AmiService, AmiExternalCommonInterface {

    private static Logger logger = Logger.getLogger(AmiServiceImpl.class);

    /**
     * Time to awaiting by close the threads.
     */
    private static final int OFFSET_WAIT_TIME = 60000;

    private String hostName;

    private int port;

    private String username;

    private String secret;

    private int listenerCount;

    private long pingTimeout;

    private Properties properties;

    private Object args;

    /**
     * <code>true<code> to use SSL for the connection.
     */
    private boolean useSSL = false;

    /**
     * The timeout to wait for a ManagerResponse after sending a
     * ManagerAction.
     */
    private long responseTimeout;

    /**
     * The default timeout to wait for the last ResponseEvent after sending an
     * EventGeneratingAction.
     */
    private long eventTimeout;

    /**
     * Timeout to use when connecting to the Asterisk server. A timeout of <b>0</b> indicates
     * infinite timeout;
     */
    private int socketTimeout;

    /**
     * Closes the connection (and reconnects) if no input has been read for the given amount of
     * milliseconds. A timeout of <b>0</b> indicates infinite timeout;
     */
    private int socketReadTimeout;

    private boolean running;

    private ManagerReader reader;

    private ManagerWriter writer;

    private Thread readerThread;

    private Thread writerThread;

    /**
     * Socket used to communicate (TCP/IP) with Asterisk.
     */
    private SocketConnectionFacade socket;

    private ConcurrentLinkedQueue<AmiEventArgs> eventQueue = new ConcurrentLinkedQueue<AmiEventArgs>();

    private SimpleMonitor sync = new SimpleMonitor();

    private List<AmiServiceListener> listeners = new ArrayList<AmiServiceListener>();

    private List<Thread> listenerThreads = new ArrayList<Thread>();

    private List<Class<? extends AmiServiceHandler>> serviceHandlers = new ArrayList<Class<? extends AmiServiceHandler>>();

    private SessionCache sessionCache;

    private SimpleCacheService cacheService;

    public AmiServiceImpl(
            String hostName,
            int port,
            String username,
            String secret,
            int socketTimeout,
            int socketReadTimeout,
            boolean useSSL,
            int listenerCount,
            long pingTimeout) {
        Parameters.checkBlankString(hostName, "hostName");
        Parameters.checkIntNegative(port, "port");
        Parameters.checkBlankString(username, "username");
        Parameters.checkBlankString(secret, "secret");
        Parameters.checkIntNegative(socketTimeout, "socketTimeout");
        Parameters.checkIntNegative(socketReadTimeout, "socketReadTimeout");
        Parameters.checkIntNegative(listenerCount, "listenerCount");
        Parameters.checkLongNegative(pingTimeout, "pingTimeout");

        this.hostName = hostName;
        this.port = port;
        this.username = username;
        this.secret = secret;
        this.socketTimeout = socketTimeout;
        this.socketReadTimeout = socketReadTimeout;
        this.useSSL = useSSL;
        this.listenerCount = listenerCount;
        this.pingTimeout = pingTimeout;

        // Create reader and writer.
        reader = new ManagerReaderImpl(this, sync);
        writer = new ManagerWriterImpl(sync);
    }

    /**
     * Initialize listener to consume events.
     *
     * @throws OperationException
     */
    private void initListeners() throws OperationException {
        // Create listeners threads.
        for (int i = 0; i < listenerCount; i++) {
            AmiServiceListener listener = new AmiServiceListenerImpl(this, sync);
            listener.setServiceHandlers(createHandlers(listener));
            listeners.add(listener);
            Thread listenrThread = new Thread(listener, String.format("Service Listener Thread - %s", i));
            listenerThreads.add(listenrThread);
        }

        // Execute listener threads.
        for (Thread thread : listenerThreads) {
            thread.start();
        }
    }

    private List<AmiServiceHandler> createHandlers(AmiServiceListener amiListener) throws OperationException {
        List<AmiServiceHandler> handlers = new ArrayList<AmiServiceHandler>();

        try {
            for (Class<? extends AmiServiceHandler> srvHandlerClass : serviceHandlers) {
                AmiServiceHandler handler =
                        srvHandlerClass.getDeclaredConstructor(Properties.class).newInstance(properties);
                handler.setArgs(args);
                handler.setAmiListener(amiListener);
                handlers.add(handler);
            }

            return handlers;
        } catch (Throwable ex) {
            logger.error("AmiServiceHandler cannot be instantiated.", ex);
            throw new OperationException("AmiServiceHandler cannot be instantiated.", ex);
        }
    }

    @Override
    public void start(SessionCacheFactory cacheFactory, CacheServiceFactory cacheServiceFactory) throws IOException, OperationException {
        Parameters.checkNull(cacheFactory, "cacheFactory");
        // Strat socket connection, and launch services threads.
        socket = new SocketConnectionFacadeImpl(hostName, port, useSSL, socketTimeout, socketReadTimeout);

        // Start reader impl
        reader.setSocket(socket);
        readerThread = new Thread(reader, "ManagerReaderImpl Thread");
        readerThread.start();
        // Start writer impl.
        writer.setSocket(socket);
        writerThread = new Thread(writer, "ManagerWriterImpl Thread");
        writerThread.start();

        initListeners();

        // Initialize session cache.
        sessionCache = cacheFactory.getCache();

        if (cacheServiceFactory != null) {
            cacheService = cacheServiceFactory.getCache();
        }

        setRunning(true);
    }

    @Override
    public void stop() throws InterruptedException, IOException {
        // Terminate runnable.
        if (reader != null) {
            reader.terminate();
        }

        if (writer != null) {
            writer.terminate();
        }

        if (readerThread != null) {
            readerThread.join(OFFSET_WAIT_TIME);
        }

        if (readerThread != null) {
            readerThread.join(OFFSET_WAIT_TIME);
        }

        // Stop listeners
        for (AmiServiceListener listener : listeners) {
            listener.terminate();
        }

        for (Thread thread : listenerThreads) {
            thread.join(OFFSET_WAIT_TIME);
        }

        // Close socket
        if (socket != null && socket.isConnected()) {
            socket.close();
        }
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setSocketReadTimeout(int socketReadTimeout) {
        this.socketReadTimeout = socketReadTimeout;
    }

    public void setResponseTimeout(long responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public void setEventTimeout(long eventTimeout) {
        this.eventTimeout = eventTimeout;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public void setListenerCount(int listenerCount) {
        this.listenerCount = listenerCount;
    }

    public void setPingTimeout(long pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    @Override
    public void doLogin() throws OperationException {
        checkRunning("doLogin");

        LoginAction action = new LoginAction(username, secret);
        scheduleAction(action, getRandomUUID());
    }

    @Override
    public void doLogoff() throws OperationException {
        checkRunning("doLogoff");

        LogoffAction action = new LogoffAction();
        scheduleAction(action, getRandomUUID());
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public synchronized boolean isRunning() {
        return readerThread.isAlive() && writerThread.isAlive() && running;
    }

    @Override
    public void scheduleAction(AmiAction action, String internalActionId) throws OperationException {
        checkRunning("scheduleAction");
        writer.scheduleAction(action, internalActionId);
    }

    @Override
    public void offerEvent(AmiEventArgs eventArgs) throws OperationException {
        if (eventArgs == null) {
            // Nothing action.
            return;
        }

        checkRunning("dispatchEvent");
        eventQueue.offer(eventArgs);
    }

    @Override
    public AmiEventArgs pollEvent() {
        return eventQueue.poll();
    }

    private void checkRunning(String predicate) {
        if (!isRunning()) {
            throw new IllegalStateException(
                    String.format("%s when the service is not running not allowed", predicate));
        }
    }

    @Override
    public void registerHandler(Class<? extends AmiServiceHandler> srvHandler) {
        Parameters.checkNull(srvHandler, "srvHandler");
        serviceHandlers.add(srvHandler);
    }

    @Override
    public SessionCtx getSession(String sessionId) throws OperationException {
        return sessionCache.getSession(sessionId);
    }

    @Override
    public SimpleCacheService getCacheService() {
        return cacheService;
    }

    @Override
    public void invalidateSession(String sessionId) {
        sessionCache.invalidateSession(sessionId);
    }

    @Override
    public int getEventQueueSize() {
        return eventQueue.size();
    }

    @Override
    public List<String> getRegisteredHandlers() {
        List<String>names = new ArrayList<String>();

        for (Class<?> srvHandler : serviceHandlers) {
            names.add(srvHandler.getSimpleName());
        }

        return names;
    }

    @Override
    public long getCacheSessionSize() {
        return sessionCache.size();
    }

    @Override
    public long getCacheServiceSize() {
        return cacheService.size();
    }

    @Override
    public boolean isConnectedWriterSocket() {
        return writer.isConnected();
    }

    @Override
    public boolean isConnectedReaderSocket() {
        return reader.isConnected();
    }

    @Override
    public List<String> getListenerThreadNames() {
        List<String>names = new ArrayList<String>();

        for (Thread th : listenerThreads) {
            names.add(th.getName());
        }

        return names;
    }

    @Override
    public AmiEvent getEventOnExecution(String threadName) {
        // TODO Auto-generated method stub
        return null;
    }
}
