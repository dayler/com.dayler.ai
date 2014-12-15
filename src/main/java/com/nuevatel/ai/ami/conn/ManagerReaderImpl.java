/**
 *
 */
package com.nuevatel.ai.ami.conn;

import com.nuevatel.ai.ami.event.AmiEventArgs;
import com.nuevatel.ai.ami.service.AmiService;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.thread.SimpleMonitor;
import com.nuevatel.common.util.Parameters;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayDeque;

/**
 * @author asalazar
 */
public class ManagerReaderImpl implements ManagerReader {

    private static Logger logger = Logger.getLogger(ManagerReaderImpl.class);

    private SimpleMonitor sync;

    private SocketConnectionFacade socket;

    private boolean running;

    private AmiService service;

    public ManagerReaderImpl(AmiService service, SimpleMonitor sync) {
        Parameters.checkNull(service, "service");
        Parameters.checkNull(sync, "sync");
        this.service = service;
        this.sync = sync;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (socket == null) {
            logger.error("No socket instance...");
            throw new IllegalStateException("socket is null, at run command.");
        }

        setRunning(true);

        String line;

        // Main loop.
        try {
            // fast performance array.
            ArrayDeque<String> lines = null;

            while (isRunning()
                    && (socket.isConnected())
                    && ((line = socket.readLine()) != null)) {
                logger.info(String.format("Line read: %s", line));

                if (line.length() == 0) {
                    dispatchEvent(lines);
                    lines = null;
                } else {
                    if (lines == null) {
                        lines = new ArrayDeque<String>();
                    }

                    lines.add(line);
                }
            }
        } catch (IOException ex) {
            logger.error("While Event is reading. Could not read the line from socket.", ex);
        } catch (OperationException ex) {
            logger.error("While Event is reading. On dispatch event", ex);
        } catch (Throwable ex) {
            logger.error("While Event is reading.", ex);
        }
    }

    @Override
    public void dispatchEvent(ArrayDeque<String> eventArgs) throws OperationException {
        AmiEventArgs args = new AmiEventArgs(eventArgs);
        service.offerEvent(args);
        sync.doNotifyAll();
    }

    @Override
    public void terminate() {
        logger.info("Manager Reader is terminating...");
        setRunning(false);
        // No close socket connection. Socket belong to other object.
    }

    @Override
    public boolean isConnected() {
        return socket == null ? false : socket.isConnected();
    }

    public synchronized boolean isRunning() {
        return running;
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    public void setSocket(SocketConnectionFacade socket) {
        this.socket = socket;
    }
}
