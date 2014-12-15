/**
 *
 */
package com.nuevatel.ai.ami.conn;

import com.nuevatel.ai.ami.action.AmiAction;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.thread.SimpleMonitor;
import com.nuevatel.common.util.Parameters;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author asalazar
 */
public class ManagerWriterImpl implements ManagerWriter {

    private static Logger logger = Logger.getLogger(ManagerWriterImpl.class);

    private ConcurrentLinkedQueue<AmiAction> queue = new ConcurrentLinkedQueue<AmiAction>();

    private SimpleMonitor sync;

    private SocketConnectionFacade socket;

    private boolean running = false;

    public ManagerWriterImpl(SimpleMonitor sync) {
        Parameters.checkNull(sync, "sync");
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

        try {
            while (isRunning() && socket.isConnected()) {
                AmiAction action = queue.poll();

                if (action == null) {
                    sync.doWait();
                } else {
                    // Dispatch command through the socket.
                    String command = action.toCommand().toString();
                    logger.info(String.format("Write: %s", command));
                    socket.write(action.toCommand().toString());
                    socket.flush();
                }
            }
        } catch (InterruptedException ex) {
            logger.error("The thread was interrupted on doWait.", ex);
        } catch (IOException ex) {
            logger.error("Input output exception was succeed on write command in the socket.", ex);
        } catch (Throwable ex) {
            logger.error("When action is writing in the socket.", ex);
        }
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.conn.ManagerWriter#scheduleAction(com.nuevatel.ai.ami.action.AmiAction, java.lang.String)
     */
    @Override
    public void scheduleAction(AmiAction action, String internalActionId) throws OperationException {
        action.setActionId(internalActionId);
        queue.offer(action);
        sync.doNotifyAll();
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.conn.ManagerWriter#setSocket(com.nuevatel.ai.ami.conn.SocketConnectionFacade)
     */
    @Override
    public void setSocket(SocketConnectionFacade socket) {
        this.socket = socket;
    }

    @Override
    public void terminate() {
        logger.info("Manager Writer is terminating...");
        setRunning(false);
        // No close socket. Socket belongs to an other object instance.
    }

    private synchronized boolean isRunning() {
        return running;
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isConnected() {
        return socket == null ? false : socket.isConnected();
    }
}
