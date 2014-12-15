/**
 *
 */
package com.dayler.ai.ami.service;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.dayler.ai.ami.action.AmiAction;
import com.dayler.ai.ami.action.LoginAction;
import com.dayler.ai.ami.event.AmiCreateSessionEvent;
import com.dayler.ai.ami.event.AmiDiscardSessionEvent;
import com.dayler.ai.ami.event.AmiEvent;
import com.dayler.ai.ami.event.CDREvent;
import com.dayler.ai.ami.event.DialEvent;
import com.dayler.ai.ami.event.HangupEvent;
import com.dayler.ai.ami.event.HangupRequestEvent;
import com.dayler.ai.ami.event.NewStateEvent;
import com.dayler.ai.ami.event.PeerStatusEvent;
import com.dayler.ai.ami.event.Response;
import com.dayler.ai.ami.event.RtcpReceivedEvent;
import com.dayler.ai.ami.event.RtcpSentEvent;
import com.dayler.ai.ami.event.VarSetEvent;
import com.dayler.ai.ami.service.cache.SimpleCacheService;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.dayler.common.exception.OperationException;

/**
 * Handler to catch events produced by the AMI.
 *
 * @author asalazar
 */
public abstract class AmiServiceHandler {

    private ScheduledExecutorService executorService = null;

    protected Properties properties;

    protected Object args;

    /**
     * Session context variable. It is related to unique id identifier.
     */
    private SessionCtx ctx;

    /**
     * Reference to current {@link AmiServiceListener} to handle the event. It is used only to
     * schedule actions.
     */
    private AmiServiceListener amiListener;

    public AmiServiceHandler(Properties properties) {
        this.properties = properties;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    /**
     * On create session. Its is invoked after set new session in the cache.
     *
     * @param event Create session event.
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onCreateSession(AmiCreateSessionEvent event) throws OperationException {
        // No op
    }

    /**
     * Ocurrs when the session object is discard from the cache.
     *
     * @param event Discard session event
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onDiscardSession(AmiDiscardSessionEvent event) throws OperationException {
        // No op
    }

    /**
     * A response is received. Response could be {@link AmiEvent} like dial or hugup, or the
     * response of some action like {@link LoginAction}
     *
     * @param response The response to receive.
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void responseReceived(Response response) throws OperationException {
        // No op
    }

    /**
     * An {@link AmiEvent} is received.
     *
     * @param event The {@link AmiEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void eventReceived(AmiEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link PeerStatusEvent} is received. No session associated to this event.
     * 
     * @param event the {@link PeerStatusEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onPeerStatus(PeerStatusEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link DialEvent} is received.
     *
     * @param event The received {@link DialEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onDial(DialEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link RtcpReceivedEvent} is received
     *
     * @param event the received {@link RtcpReceivedEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onRtcpReceived(RtcpReceivedEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link RtcpSentEvent} is received.
     *
     * @param event the received {@link RtcpSentEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onRtcpSent(RtcpSentEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link HangupRequestEvent} is received.
     *
     * @param event the received {@link HangupRequestEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onHangupRequest(HangupRequestEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link HangupEvent} is received.
     *
     * @param event the received {@link HangupEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onHangup(HangupEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link VarSetEvent} is received.
     * 
     * @param event The received {@link VarSetEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onVarSet(VarSetEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link NewStateEvent} is received.
     * 
     * @param event The received {@link NewStateEvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onNewstate(NewStateEvent event) throws OperationException {
        // No op
    }

    /**
     * {@link CDREvent} is received.
     * 
     * @param event The received {@link CDREvent}
     * @throws OperationException An exception is thrown when is processing this method.
     */
    protected void onCdr(CDREvent event) throws OperationException {
        // No op
    }

    /**
     * Set session variables context.
     *
     * @param ctx Context variables.
     * @throws OperationException
     */
    protected synchronized final void setSessionCtx(SessionCtx ctx) {
        this.ctx = ctx;
    }

    /**
     * @return The session variable context. Return <b>null</b> if it is not available for the
     * current context.
     */
    protected synchronized final SessionCtx getSessionCtx() {
        return ctx;
    }

    public final void setAmiListener(AmiServiceListener amiListener) {
        this.amiListener = amiListener;
    }

    /**
     * Schedule an action to {@link AmiService}.
     *
     * @param action           The action to perform.
     * @param internalActionId Unique id to identify the action.
     * @throws OperationException An exception is thrown when is processing this method.
     */
    public final void scheduleAction(AmiAction action, String internalActionId) throws OperationException {
        amiListener.scheduleAction(action, internalActionId);
    }

    /**
     * Link two sessions in a call. The responsibility to get <b>callID</b> and call this method is
     * on the service handler implementation.
     * <br/>
     * <br/>
     * <b>No implemented, needs to implement on {@link AmiServiceHandler} implementation.</b>
     * <br/>
     * <b>No safe to use getSessionCtx()</b>
     * 
     * @param callID Unique identifier of the call.
     * @param destinationSessionId Session ID of the destination session call.
     * @param originChannel Channel on which is originate the call.
     * @param destChannel Channel that is the destination of the call.
     * @throws OperationException When <b>destinationSessionId</b> could not be retrieved.
     */
    protected void linkSessionsByCall(String callID, SessionCtx session,
            String destinationSessionId, String originChannel, String destChannel) throws OperationException {
        // No op.
    }

    protected final SessionCtx getSessionCtx(String uniqueSessionId) throws OperationException {
        return amiListener.getSessionCtx(uniqueSessionId);
    }

    private synchronized void doTimeout(SessionCtx sessionCtx, Date dateTime, boolean isScheduledAtFixRate) throws OperationException {
        timeout(sessionCtx, dateTime, isScheduledAtFixRate);
    }

    private synchronized void doOnTimeoutException(SessionCtx sessionCtx, Date dateTime, OperationException ex, boolean isScheduledAtFixRate) {
        onTimeoutException(sessionCtx, dateTime, ex, isScheduledAtFixRate);
    }
    /**
     * Scheduled task.
     * <br/>
     * <b>No safe to use getSessionCtx</b>
     * 
     * @param sessionCtx Context in which execute the task.
     * @param dateTime Time in which is escuting the task
     * 
     * @see AmiServiceHandler#schedule(SessionCtx, long)
     * @see AmiServiceHandler#scheduledAtFixRate(SessionCtx, long, long)
     */
    protected void timeout(SessionCtx sessionCtx, Date dateTime, boolean isScheduledAtFixRate) throws OperationException {
        // No op
    }

    /**
     * Handle the error condition on <b>timeout</b> operation.
     * 
     * @param sessionCtx The Context of the session.
     * @param dateTime Time in which was executed timeout op.
     * @param ex Exception was thrown by timeout op.
     * @param isScheduledAtFixRate If timeout was call by periodically task.
     */
    protected void onTimeoutException(SessionCtx sessionCtx, Date dateTime, OperationException ex, boolean isScheduledAtFixRate) {
        // No op
    }

    /**
     * Initialize the executor service.
     * 
     * @param threadPoolCount Number of threads with which is creating the executoir service.
     */
    protected final void createExecutorService(int threadPoolCount) {
        executorService = Executors.newScheduledThreadPool(threadPoolCount);
    }

    /**
     * Shutdown executor service.
     */
    protected final void stopExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    /**
     * Schedule timeout after <b>delay</b> time.
     * 
     * @param sessionCtx Context session
     * @param delay Time to await, expressed in milliseconds.
     */
    protected final void schedule(SessionCtx sessionCtx, long delay) {
        Runnable task = new TimerWorker(sessionCtx, this, false);
        executorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Schedule timeout periodically, it is executing after <b>initialDelay</b>, time after that
     * periodically each <b>period</b> time
     * <b/>
     * <b>No safe to use getSessionCtx</b>
     * 
     * @param sessionCtx Context of the session.
     * @param initialDelay Initial delay, expressed in milliseconds.
     * @param period Time in milliseconds to execute timeout periodically.
     * 
     * @return The {@link Future} scheduled of the scheduled task.
     */
    protected final Future<?> scheduledAtFixRate(SessionCtx sessionCtx, long initialDelay, long period) {
        Runnable task = new TimerWorker(sessionCtx, this, true);
        return executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    protected final SimpleCacheService getCacheService() {
        return amiListener.getCacheService();
    }

    protected void invalidateCacheObj(String key) {
        getCacheService().invalidateCall(key);
    }

    protected void invalidateSessionCtx(String sessionCtxId) {
        amiListener.invalidateSession(sessionCtxId);
    }

    /**
     * Timer worker to execute <b>timeout</b> operation.
     */
    private static final class TimerWorker implements Runnable {

        private SessionCtx ctx;

        private AmiServiceHandler serviceHandler;

        private boolean isScheduledAtFixRate;

        /**
         * TimerWorker Constructor.
         * 
         * @param ctx Current context. It can be null;
         * @param serviceHandler Service handler over which perform the timer op.
         * @param isScheduledAtFixRate Indicate if the timer is on fix rate, or scheduled rate.
         */
        public TimerWorker(SessionCtx ctx, AmiServiceHandler serviceHandler, boolean isScheduledAtFixRate) {
            this.ctx = ctx;
            this.serviceHandler = serviceHandler;
            this.isScheduledAtFixRate = isScheduledAtFixRate;
        }

        /**
         * Execute scheduled operations.
         */
        @Override
        public void run() {
            Date dateTime = new Date();

            try {
                serviceHandler.doTimeout(ctx, dateTime, isScheduledAtFixRate);
            } catch (OperationException ex) {
                serviceHandler.doOnTimeoutException(ctx, dateTime, ex,isScheduledAtFixRate);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }
}
