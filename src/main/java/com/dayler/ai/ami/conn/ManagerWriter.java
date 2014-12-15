/**
 *
 */
package com.dayler.ai.ami.conn;

import com.dayler.ai.ami.action.AmiAction;
import com.dayler.common.exception.OperationException;

/**
 * @author asalazar
 */
public interface ManagerWriter extends Runnable {

    void scheduleAction(AmiAction action, String internalActionId) throws OperationException;

    void setSocket(SocketConnectionFacade socket);

    boolean isConnected();

    void terminate();
}
