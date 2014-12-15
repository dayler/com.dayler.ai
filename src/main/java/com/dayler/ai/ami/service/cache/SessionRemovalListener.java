/**
 *
 */
package com.dayler.ai.ami.service.cache;

import com.dayler.ai.ami.event.AmiDiscardSessionEvent;
import com.dayler.ai.ami.event.AmiEventArgs;
import com.dayler.ai.ami.event.AmiResponseField;
import com.dayler.ai.ami.service.AmiService;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.dayler.common.exception.OperationException;
import com.dayler.common.util.Parameters;

import org.apache.log4j.Logger;

import java.util.ArrayDeque;

/**
 * @author asalazar
 */
public class SessionRemovalListener implements RemovalListener<String, SessionCtx> {

    private static Logger logger = Logger.getLogger(SessionRemovalListener.class);

    private AmiService amiService;

    public SessionRemovalListener(AmiService amiService) {
        Parameters.checkNull(amiService, "amiService");
        this.amiService = amiService;
    }

    @Override
    public void onRemoval(RemovalNotification<String, SessionCtx> notification) {
        try {
            if (notification.wasEvicted()) {
                ArrayDeque<String> args = new ArrayDeque<String>();
                args.add(String.format("%s:%s", AmiResponseField.Event.name(),
                        AmiDiscardSessionEvent.RESPONSE_NAME));
                args.add(String.format("%s:%s", AmiResponseField.UniqueId.name(),
                        notification.getValue().getSessionID()));
                args.add(String.format("%s:%s", AmiResponseField.Channel.name(),
                        notification.getValue().getChannel()));
                AmiEventArgs eventArgs = new AmiEventArgs(args);
                eventArgs.setCtx(notification.getValue());
                amiService.offerEvent(eventArgs);
            }
        } catch (OperationException ex) {
            logger.warn(String.format("Could not offer removal session event for sessionId:%s",
                    notification.getKey()), ex);
        }
    }

}
