/**
 *
 */
package com.nuevatel.ai.ami.conn;

import com.nuevatel.common.exception.OperationException;

import java.util.ArrayDeque;

/**
 * @author asalazar
 */
public interface ManagerReader extends Runnable {

    void dispatchEvent(ArrayDeque<String> eventArgs) throws OperationException;

    void setSocket(SocketConnectionFacade socket);

    boolean isConnected();

    void terminate();
}
