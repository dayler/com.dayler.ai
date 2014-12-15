/**
 *
 */
package com.nuevatel.ai.ami.conn;

import com.nuevatel.ai.ami.action.AmiAction;
import com.nuevatel.common.exception.OperationException;

/**
 * @author asalazar
 */
public interface ManagerWriter extends Runnable {

    void scheduleAction(AmiAction action, String internalActionId) throws OperationException;

    void setSocket(SocketConnectionFacade socket);

    boolean isConnected();

    void terminate();
}
