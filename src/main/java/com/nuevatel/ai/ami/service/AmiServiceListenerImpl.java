/**
 *
 */
package com.nuevatel.ai.ami.service;

import com.nuevatel.ai.ami.action.AmiAction;
import com.nuevatel.ai.ami.event.AmiCreateSessionEvent;
import com.nuevatel.ai.ami.event.AmiDiscardSessionEvent;
import com.nuevatel.ai.ami.event.AmiEvent;
import com.nuevatel.ai.ami.event.AmiEventArgs;
import com.nuevatel.ai.ami.event.AmiResponseFactory;
import com.nuevatel.ai.ami.event.CDREvent;
import com.nuevatel.ai.ami.event.DialEvent;
import com.nuevatel.ai.ami.event.HangupEvent;
import com.nuevatel.ai.ami.event.HangupRequestEvent;
import com.nuevatel.ai.ami.event.NewStateEvent;
import com.nuevatel.ai.ami.event.PeerStatusEvent;
import com.nuevatel.ai.ami.event.Response;
import com.nuevatel.ai.ami.event.RtcpReceivedEvent;
import com.nuevatel.ai.ami.event.RtcpSentEvent;
import com.nuevatel.ai.ami.event.VarSetEvent;
import com.nuevatel.ai.ami.service.cache.SimpleCacheService;
import com.nuevatel.ai.ami.service.context.SessionCtx;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.thread.SimpleMonitor;
import com.nuevatel.common.util.Parameters;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static com.nuevatel.common.util.ClassUtil.castAs;

/**
 * @author asalazar
 */
public class AmiServiceListenerImpl implements AmiServiceListener {

    private static Logger logger = Logger.getLogger(AmiServiceListenerImpl.class);

    private SimpleMonitor sync;

    private AmiService service;

    private boolean running = false;

    private List<AmiServiceHandler> handlers = Collections.emptyList();

    public AmiServiceListenerImpl(AmiService service, SimpleMonitor sync) {
        Parameters.checkNull(service, "service");
        Parameters.checkNull(sync, "sync");
        this.service = service;
        this.sync = sync;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.AmiServiceListener#service(com.nuevatel.ai.ami.event.AmiEventArgs)
     */
    @Override
    public void service(AmiEventArgs eventArgs) throws OperationException {
        logger.debug(String.format("Event dispatch at service: eventArgs: %s", eventArgs));

        try {
            // Get context variables
            Response amiResponse = AmiResponseFactory.build(eventArgs);

            if (amiResponse == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Failed to build Response for: %s", eventArgs));
                }

                return;
            }

            for (AmiServiceHandler handler : handlers) {
                // Set context variables
                String uniqueId = amiResponse.getUniqueId();
                SessionCtx sessionCtx = service.getSession(uniqueId);
                handler.setSessionCtx(sessionCtx);

                // Dispatch events.
                handler.responseReceived(amiResponse);

                if (amiResponse instanceof AmiEvent) {
                    AmiEvent amiEvent = castAs(AmiEvent.class, amiResponse);
                    handler.eventReceived(amiEvent);
                    EventManager.valueOfEventName(amiEvent.getName()).delegate(handler, amiEvent);
                }
            }

        } catch (InstantiationException ex) {
            String msg = String.format("Could not build ami response for the eventArgs: %s.",
                    eventArgs);
            logger.error(msg, ex);
        } catch (IllegalAccessException ex) {
            String msg = String.format("Could not build ami response for the eventArgs: %s.",
                    eventArgs);
            logger.error(msg, ex);
        } catch (OperationException ex) {
            String msg = String.format("Ami response event cannot be handle. eventArgs: %s",
                    eventArgs);
            logger.error(msg, ex);
        } catch (Throwable ex) {
            String msg = String.format("Error to handle ami response event. eventArgs: %s",
                    eventArgs);
            logger.fatal(msg, ex);

            // This exception will stop the thread, only if an unknown exception is occurred the
            // thread can be stooped.
            throw new OperationException(msg, ex);
        }
    }

    @Override
    public void run() {
        logger.info("Service listener started ...");

        try {
            setRunning(true);

            while (isRunning()) {
                try {
                    AmiEventArgs args = service.pollEvent();

                    if (args == null) {
                        logger.debug("No events to handle...");
                        sync.doWait();
                    } else {
                        service(args);
                    }
                } catch (Throwable ex) {
                    logger.error("While service listener is running.", ex);
                }
            }

            logger.info("Service listener was terminated ...");
        } catch (Throwable ex) {
            logger.fatal("While service listener is running.", ex);
        }
    }

    @Override
    public void terminate() {
        logger.info("Service Listener is terminating...");
        setRunning(false);
    }

    public synchronized boolean isRunning() {
        return running;
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void setServiceHandlers(List<AmiServiceHandler> handlers) {
        Parameters.checkNull(handlers, "handlers");
        this.handlers = handlers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SessionCtx getSessionCtx(String uniqueSessionId) throws OperationException {
        return service.getSession(uniqueSessionId);
    }

    @Override
    public void scheduleAction(AmiAction action, String internalActionId) throws OperationException {
        service.scheduleAction(action, internalActionId);
    }

    @Override
    public SimpleCacheService getCacheService() {
        return service.getCacheService();
    }

    @Override
    public void invalidateSession(String sessionCtxId) {
        service.invalidateSession(sessionCtxId);
    }

    private enum EventManager {
        onCreateSession(AmiCreateSessionEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                AmiCreateSessionEvent event = castAs(AmiCreateSessionEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onCreateSession(event);
            }
        },
    
        onDiscardSession(AmiDiscardSessionEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                AmiDiscardSessionEvent event = castAs(AmiDiscardSessionEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onDiscardSession(event);
            }
        },
    
        onDial(DialEvent.REPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                DialEvent dialEvent = castAs(DialEvent.class, amiEvent);
    
                if (dialEvent == null) {
                    return;
                }
    
                serviceHandler.onDial(dialEvent);
            }
        },
    
        onRtcpReceived(RtcpReceivedEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                RtcpReceivedEvent event = castAs(RtcpReceivedEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onRtcpReceived(event);
            }
        },
    
        onRtcpSent(RtcpSentEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                RtcpSentEvent event = castAs(RtcpSentEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onRtcpSent(event);
            }
        },
    
        onHangupRequest(HangupRequestEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                HangupRequestEvent event = castAs(HangupRequestEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onHangupRequest(event);
            }
        },
    
        onHangup(HangupEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                HangupEvent event = castAs(HangupEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onHangup(event);
            }
        },
    
        onVarSet(VarSetEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                VarSetEvent event = castAs(VarSetEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onVarSet(event);
            }
        },
    
        onNewstate(NewStateEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                NewStateEvent event = castAs(NewStateEvent.class, amiEvent);
    
                if (event == null) {
                    return;
                }
    
                serviceHandler.onNewstate(event);
            }
        },

        onPeerStatus(PeerStatusEvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                PeerStatusEvent event = castAs(PeerStatusEvent.class, amiEvent);

                if (event == null) {
                    return;
                }

                serviceHandler.onPeerStatus(event);
            }
        },

        onCdr(CDREvent.RESPONSE_NAME) {
            @Override
            protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
                CDREvent event = castAs(CDREvent.class, amiEvent);

                if (event == null) {
                    return;
                }

                serviceHandler.onCdr(event);
            }
        },

        noneEvent("");
    
        private String eventName;
    
        private EventManager(String eventName) {
            this.eventName = eventName;
        }
    
        protected void delegate(AmiServiceHandler serviceHandler, AmiEvent amiEvent) throws OperationException {
            // No op.
        }
    
        public static EventManager valueOfEventName(String eventName) {
            for (EventManager eMng : values()) {
                if (eMng.eventName.equals(eventName)) {
                    return eMng;
                }
            }
    
            return noneEvent;
        }
    }
}
