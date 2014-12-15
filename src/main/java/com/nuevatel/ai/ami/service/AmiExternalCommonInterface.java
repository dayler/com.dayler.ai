package com.nuevatel.ai.ami.service;

import java.util.List;

import com.nuevatel.ai.ami.event.AmiEvent;

/**
 * Provide an interface to use external apps to communicate with AMI.
 * 
 * @author asalazar
 *
 */
public interface AmiExternalCommonInterface {

    /**
     * 
     * @return Size of the queued events. 
     */
    int getEventQueueSize();

    /**
     * 
     * @return List of the registered handlers.
     */
    List<String> getRegisteredHandlers();

    /**
     * 
     * @return Size of the cache session.
     */
    long getCacheSessionSize();

    /**
     * 
     * @return Size of the service cache.
     */
    long getCacheServiceSize();

    /**
     * 
     * @return <b>true</b> if writer socket is connected.
     */
    boolean isConnectedWriterSocket();

    /**
     * 
     * @return <b>true</b> if reader socket is connected.
     */
    boolean isConnectedReaderSocket();

    /**
     * 
     * @return List of the active treads for the service.
     */
    List<String> getListenerThreadNames();

    /**
     * 
     * @param threadName Name of the thread that is reviewed
     * @return Event that is processing for the thread.
     */
    AmiEvent getEventOnExecution(String threadName);
}
