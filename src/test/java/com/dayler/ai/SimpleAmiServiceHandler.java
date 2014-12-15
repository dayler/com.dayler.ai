/**
 *
 */
package com.dayler.ai;

import com.dayler.ai.ami.event.*;
import com.dayler.ai.ami.service.AmiServiceHandler;
import com.dayler.common.exception.OperationException;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author asalazar
 */
public class SimpleAmiServiceHandler extends AmiServiceHandler {

    private static Logger logger = Logger.getLogger(SimpleAmiServiceHandler.class);

    protected SimpleAmiServiceHandler(Properties properties) {
        super(properties);
    }

    @Override
    protected void onCreateSession(AmiCreateSessionEvent event) throws OperationException {
        super.onCreateSession(event);

        logger.info("onCreateSession x-> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onDiscardSession(AmiDiscardSessionEvent event) throws OperationException {
        super.onDiscardSession(event);

        logger.info("endSession x-> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void responseReceived(Response response) throws OperationException {
        super.responseReceived(response);

        logger.info("responseReceived x-> class: " + ((response == null) ? null : response.getClass().getName()));
    }

    @Override
    protected void eventReceived(AmiEvent event) throws OperationException {
        super.eventReceived(event);

        logger.info("eventReceived x-> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onDial(DialEvent event) throws OperationException {
        super.onDial(event);

        logger.info("onDial -> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onRtcpReceived(RtcpReceivedEvent event) throws OperationException {
        super.onRtcpReceived(event);

        logger.info("onRtcpReceived -> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onRtcpSent(RtcpSentEvent event) throws OperationException {
        super.onRtcpSent(event);

        logger.info("onRtcpSent -> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onHangupRequest(HangupRequestEvent event) throws OperationException {
        super.onHangupRequest(event);

        logger.info("onHangupRequest -> class: " + ((event == null) ? null : event.getName()));
    }

    @Override
    protected void onHangup(HangupEvent event) throws OperationException {
        super.onHangup(event);

        logger.info("onHangup -> class: " + ((event == null) ? null : event.getName()));
    }
}
